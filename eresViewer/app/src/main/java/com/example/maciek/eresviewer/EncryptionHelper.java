package com.example.maciek.eresviewer;

import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * Created by Maciek on 11.08.2017.
 */

public class EncryptionHelper {
    private static final String AndroidKeyStore="AndroidKeyStore";
    private static final String AES_MODE="AES/GCM/NoPadding";
    private static Boolean firstEncryption=true;

    private static byte[] encryptedText;

    private static byte[] encryptTextInAPIHigherIn23(final String textToEncrypt, String ivNameInSharedPreferences, Context context)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException,
            InvalidAlgorithmParameterException, SignatureException, BadPaddingException,
            IllegalBlockSizeException,CertificateException{
        if(firstEncryption){
            createSecretKey(AndroidKeyStore);
            firstEncryption=false;
        }
        final Cipher cipher= Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(AndroidKeyStore));
        byte[] iv=cipher.getIV();
        saveIvInSharedPreferences(ivNameInSharedPreferences, iv, context);
        encryptedText=cipher.doFinal(textToEncrypt.getBytes("UTF-8"));
        return encryptedText;

    }
    private static void saveIvInSharedPreferences(String nameInSharedPreferences,byte[] iv, Context context){
        try {
            Preferences.saveString(nameInSharedPreferences,Base64.encodeToString(iv, 0, iv.length, Base64.NO_WRAP), context);
        }
        catch(Exception e){
            //TODO:coś tu zrobic
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
    private static String decryptData(final String alias, final byte[] encryptedData, final byte[] encryptionIv)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
            CertificateException{
        final Cipher cipher = Cipher.getInstance(AES_MODE);
        //TODO:Zapewnić obsługe dla API <23
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            final GCMParameterSpec spec = new GCMParameterSpec(128, encryptionIv);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias),spec);
        }
        byte[] decrypted=cipher.doFinal(encryptedData);
        return new String(decrypted, "UTF-8");
    }
    private static SecretKey createSecretKey(final String alias)throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidAlgorithmParameterException {

        final KeyGenerator keyGenerator= KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore);
        //TODO: Zapewnić obsługe gdy API <23
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)

        keyGenerator.init(new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .build());

        return keyGenerator.generateKey();
    }
    //TODO: Ta metoda ma decydować o wyborze metody dla róznych wersji androida
    public static String encryptData(String data, String ivNameInSharedPreferences, Context context) {
        try {
            byte[] encryptedData = encryptTextInAPIHigherIn23(data, ivNameInSharedPreferences, context);
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
            String decryptedData=decryptData(AndroidKeyStore,bytes,iv);
            return decryptedData;
        }
        catch(Exception e){
            return null;
    }
    }

    private static SecretKey getSecretKey(final String alias)
            throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException,
            IOException, CertificateException {
        KeyStore keyStore=KeyStore.getInstance(alias);
        keyStore.load(null);
        return ((KeyStore.SecretKeyEntry)keyStore.getEntry(alias, null)).getSecretKey();
    }
}
