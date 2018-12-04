package com.placepass.booking.infrastructure;

import java.util.Map;

/**
 * Interface defining the cryptography services available. 
 *
 */
public interface CryptoService {

	/**
	 * Encrypts the {@code plainText} data using the AWS KMS. The {@code encryptionContext} is used to provide support
	 * <a href="https://en.wikipedia.org/wiki/Authenticated_encryption">AEAD</a> functionality. It can be any set of key/value
	 * pairs that can be used to uniquely identify the plainText value. However these are stored as plain text, so any sensitive
	 * data <b>SHOULD NOT</b> be used in the {@code encryptionContext} values. 
	 * 
	 * @param plainText String that needs to be encrypted.
	 * @param encryptionContext Any associated data which might be able to uniquely identify plain text. The values provided 
	 * here are not encrypted, so <b>DO NOT</b> use any sensitive information in {@code encryptionCondext}. 
	 * @return encrypted data 
	 */
	public String awsKmsEncrypt(String plainText, Map<String, String> encryptionContext);
	
	/**
	 * Decrypt the {@code cipherText} using AWS KMS. The {@code decryptionContext} should be the exact same values provided
	 * during the encryption process. 
	 * 
	 * @param cipherText Encrypted data
	 * @param decryptionContext Associated data values given during the encryption process
	 * @return decrypted string.
	 */
	public String awsKmsDecrypt(String cipherText, Map<String, String> decryptionContext);
	
	/**
	 * Masks a string up to the given {@code length}. {@code length} can be given a negative value which would consider the
	 * length counted from end of the string.
	 *  
	 * @param str String to be masked.
	 * @param length length the given string is masked. Can use negative values which makes the length count from string end.
	 * @return masked string
	 */
	public String maskStringToLength(String str, int length);
}
