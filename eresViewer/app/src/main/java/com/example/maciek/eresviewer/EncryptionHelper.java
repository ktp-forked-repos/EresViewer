package com.example.maciek.eresviewer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

/**
 * Created by Maciek on 11.08.2017.
 */

public class EncryptionHelper {
    private static final String AndroidKeyStore="AndroidKeyStore";
    private static final String AndroidRSAKey ="AndroidRSAKey";
    private static final String AES_MODE_APIs_BELOW_23="AES/ECB/PKCS7Padding";
    private static String AES_MODE_APIs_ABOVE_22="AES/GCM/NoPadding";
    private static final String RSA_MODE="RSA/ECB/PKCS1Padding";
    private static final String NAME_OF_AES_IN_SHARED_PREFERENCES="encrypted_AES";
    private static Boolean firstEncryption=true;
    private static Boolean firstRSAEncryption=true;

    private static byte[] encryptedText;

    private static byte[] encryptText(final String textToEncrypt, String ivNameInSharedPreferences, Context context)
            throws Exception{
        String AES_MODE;
        if(firstEncryption){
            createSecretKey(AndroidKeyStore, context);
            firstEncryption=false;
        }
        AES_MODE=getAES_MODE();
        final Cipher cipher= Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(AndroidKeyStore, context));
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            byte[] iv = cipher.getIV();
            saveIvInSharedPreferences(ivNameInSharedPreferences, iv, context);
        }
        encryptedText=cipher.doFinal(textToEncrypt.getBytes("UTF-8"));
        return encryptedText;

    }
    private static String getAES_MODE(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            return AES_MODE_APIs_ABOVE_22;
        else
            return AES_MODE_APIs_BELOW_23;
    }
    private static void saveIvInSharedPreferences(String nameInSharedPreferences,byte[] iv, Context context){
        try {
            Preferences.saveString(nameInSharedPreferences,Base64.encodeToString(iv, 0, iv.length, Base64.NO_WRAP), context);
        }
        catch(Exception e){
        }
    }
    private static byte[] getIvFromSharedPreferences(String nameInSharedPreferences, Context context){
        try {
            return (Base64.decode(Preferences.getString(nameInSharedPreferences,context), Base64.DEFAULT));
        }
        catch(Exception e){
            return null;
        }
    }
    private static String decryptData(final String alias, final byte[] encryptedData, final byte[] encryptionIv, Context context)
            throws Exception{
        final Cipher cipher = Cipher.getInstance(getAES_MODE());
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            final GCMParameterSpec spec = new GCMParameterSpec(128, encryptionIv);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias, context),spec);
        }
        else{
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias, context));
        }
        byte[] decrypted=cipher.doFinal(encryptedData);
        return new String(decrypted, "UTF-8");
    }
    private static void createSecretKey(final String alias, Context context)throws Exception {
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            final KeyGenerator keyGenerator=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore);
            keyGenerator.init(new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build());
            keyGenerator.generateKey();
        }
        else{
            String encrypted_AES=Preferences.getString(NAME_OF_AES_IN_SHARED_PREFERENCES, context);
            if(encrypted_AES == null) {
                byte[] AES_key = new byte[16];
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(AES_key);

                byte[] encryptedKey = rsaEncrypt(AES_key, context);
                String encryptedKeyB64= Base64.encodeToString(encryptedKey, Base64.DEFAULT);
                Preferences.saveString(NAME_OF_AES_IN_SHARED_PREFERENCES, encryptedKeyB64, context);
            }
        }
    }
    public static String encryptData(String data, String ivNameInSharedPreferences, Context context) {
        try {
            byte[] encryptedData = encryptText(data, ivNameInSharedPreferences, context);
            return Base64.encodeToString(encryptedData, 0, encryptedData.length, Base64.NO_WRAP);
        }
        catch(Exception e){
            return null;
        }

    }
    public static String decryptData(String str, String ivNameInSharedPreferences, Context context)
    {
        try {
            byte[] bytes=Base64.decode(str, Base64.DEFAULT);
            byte[] iv= getIvFromSharedPreferences(ivNameInSharedPreferences, context);
            String decryptedData=decryptData(AndroidKeyStore,bytes,iv, context);
            return decryptedData;
        }
        catch(Exception e){
            return null;
    }
    }

    private static SecretKey getSecretKey(final String alias, Context context)
            throws Exception    {
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            KeyStore keyStore = KeyStore.getInstance(alias);
            keyStore.load(null);
            return ((KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null)).getSecretKey();
        }
        else {
            String encryptedAES = Preferences.getString(NAME_OF_AES_IN_SHARED_PREFERENCES, context);
            byte[] AES=Base64.decode(encryptedAES, Base64.DEFAULT);
            byte[] decryptedAES=rsaDecrypt(AES);
            return new SecretKeySpec(decryptedAES,"AES");
        }

    }
//This mehtod provides generation of RSA key pairs. It should be called only for
// API's version higher than 18
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static void generateRSAKeyPair(final String alias, Context context)
    throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException,
            IOException, CertificateException,
            NoSuchProviderException,
            InvalidAlgorithmParameterException{
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStore);
            keyStore.load(null);

            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 30);

                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=" + alias))
                        .setSerialNumber(BigInteger.TEN)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA", AndroidKeyStore);
                kpg.initialize(spec);
                kpg.generateKeyPair();
            }


    }
    private static byte[] rsaEncrypt(byte[] secret, Context context) throws Exception{
        if(firstRSAEncryption){
            generateRSAKeyPair(AndroidRSAKey, context);
            firstRSAEncryption=false;
        }
        KeyStore keyStore=KeyStore.getInstance(AndroidKeyStore);
        keyStore.load(null);
        KeyStore.PrivateKeyEntry privateKeyEntry=(KeyStore.PrivateKeyEntry) keyStore.getEntry(AndroidRSAKey, null);

        Cipher inputCipher= Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
        inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inputCipher);
        cipherOutputStream.write(secret);
        cipherOutputStream.close();

        byte[] vals=outputStream.toByteArray();
        return vals;
    }

    private static byte[] rsaDecrypt(byte[] encrypted)throws Exception {
        KeyStore keyStore=KeyStore.getInstance(AndroidKeyStore);
        keyStore.load(null);
        KeyStore.PrivateKeyEntry privateKeyEntry=(KeyStore.PrivateKeyEntry)keyStore.getEntry(AndroidRSAKey, null);

        Cipher output = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
        output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

        CipherInputStream cipherInputStream= new CipherInputStream(new ByteArrayInputStream(encrypted), output);
        ArrayList<Byte> values= new ArrayList<>();
        int nextByte;
        while((nextByte= cipherInputStream.read()) != -1){
            values.add((byte)nextByte);
        }
        byte[] bytes = new byte[values.size()];
        for(int i=0; i<bytes.length; i++)
            bytes[i]=values.get(i).byteValue();
        return bytes;
    }
}
