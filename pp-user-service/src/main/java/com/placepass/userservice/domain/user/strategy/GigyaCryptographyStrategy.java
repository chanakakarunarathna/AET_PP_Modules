package com.placepass.userservice.domain.user.strategy;

/**
 * The Interface GigyaCryptographyStrategy.
 */
public interface GigyaCryptographyStrategy {

    /**
     * Decrypt.
     *
     * @param textToDecrypt the text to decrypt
     * @return the string
     */
    public String decrypt(String textToDecrypt);
    
    /**
     * Encrypt.
     *
     * @param textToEncrypt the text to encrypt
     * @return the string
     */
    public String encrypt(String textToEncrypt);
    
}
