package com.placepass.userservice.domain.user.strategy;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class GigyaCryptographyStrategyImpl.
 */
@Service
public class GigyaCryptographyStrategyImpl implements GigyaCryptographyStrategy {

    /** The Constant ENCODE_UTF_8. */
    private static final String ENCODE_UTF_8 = "UTF-8";

    /** The Constant AES. */
    private static final String AES = "AES";
    
    /** The Constant AES_ECB_PKCS5_PADDING. */
    private static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";
    
    /** The cryptography key. */
    @Value("${placepass.auth.provider.gigya.cryptography.key}")
    private String cryptographyKey;

    /**
     * Decrypt.
     *
     * @param textToDecrypt the text to decrypt
     * @param key the key
     * @return the string
     */
    public String decrypt(String textToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
            final SecretKeySpec secretKey = new SecretKeySpec(cryptographyKey.getBytes(ENCODE_UTF_8), AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final byte[] dencryptedString = cipher.doFinal(Base64.decodeBase64(textToDecrypt));
            return new String(dencryptedString);
        } catch (Exception e) {
            throw new RuntimeException("Decrypt failed ", e);
        }
    }

    /**
     * Encrypt.
     *
     * @param textToEncrypt the text to encrypt
     * @param key the key
     * @return the string
     */
    public String encrypt(String textToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5_PADDING);
            final SecretKeySpec secretKey = new SecretKeySpec(cryptographyKey.getBytes(ENCODE_UTF_8), AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = Base64.encodeBase64String(cipher.doFinal(textToEncrypt.getBytes()));
            return encryptedString;
        } catch (Exception e) {
            throw new RuntimeException("Encrypt failed ", e);
        }
    }

}
