package com.example.maciek.eresviewer;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateEncodingException;
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

    private byte[] encryption;
    private byte[] iv;

    public byte[] encryptText(final String textToEncrypt)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException,
            InvalidAlgorithmParameterException, SignatureException, BadPaddingException,
            IllegalBlockSizeException {
        final Cipher cipher= Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, createSecretKey(AndroidKeyStore));

        iv=cipher.getIV();

        return (cipher.doFinal(textToEncrypt.getBytes("UTF-8")));

    }
    public String decryptData(final String alias, final byte[] encryptedData, final byte[] encryptionIv)
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
        return new String(cipher.doFinal(encryptedData), "UTF-8");
    }
    private SecretKey createSecretKey(final String alias)throws NoSuchAlgorithmException,
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
    private SecretKey getSecretKey(final String alias)
            throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException,
            IOException, CertificateException {
        KeyStore keyStore=KeyStore.getInstance(alias);
        keyStore.load(null);
        return ((KeyStore.SecretKeyEntry)keyStore.getEntry(alias, null)).getSecretKey();
    }
}
