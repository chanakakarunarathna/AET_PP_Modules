package com.placepass.encryption.kms;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.MasterKey;
import com.amazonaws.encryptionsdk.ParsedCiphertext;
import com.amazonaws.encryptionsdk.exception.AwsCryptoException;
import com.placepass.encryption.exception.EncryptionException;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to perform encryption/decryption operations using AWS KMS.
 *
 * @see <a href="http://docs.aws.amazon.com/kms/latest/developerguide/overview.html">AWS Key Management Service</a>
 * @see <a href="http://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/introduction.html">AWS Encryption
 * SDK</a>
 */
public class AwsKmsCryptoUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String ENCRYPTION_CONTEXT_KEY_PREFIX = "PLACEPASS:AWS_KMS_CRYPTO:";

    private static AwsKmsCryptoUtil thisInstance;

    private CryptoAlgorithm cryptoAlgorithm;
    private Base64.Encoder base64Encoder;
    private Base64.Decoder base64Decoder;
    private AwsCrypto awsCrypto;

    private AwsKmsCryptoUtil() {
        cryptoAlgorithm = AwsCrypto.getDefaultCryptoAlgorithm();
        base64Encoder = Base64.getEncoder();
        base64Decoder = Base64.getDecoder();
        awsCrypto = new AwsCrypto();
    }

    /**
     * Gets an instance of this class.
     * @return Singleton instance of this class.
     */
    public static AwsKmsCryptoUtil getInstance() {
        if (thisInstance == null)
            thisInstance = new AwsKmsCryptoUtil();
        return thisInstance;
    }

    /**
     * Gets the Cryptographic algorithm used for encryption/decryption.
     * @return Cryptographic algorithm used for encryption/decryption.
     */
    public CryptoAlgorithm getCryptoAlgorithm() {
        return cryptoAlgorithm;
    }

    /**
     * Sets the Cryptographic algorithm to be used for encryption/decryption.
     * @param cryptoAlgorithm Cryptographic algorithm to be used for encryption/decryption.
     * @see <a href="http://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/supported-algorithms.html">AWS
     * Encryption SDK - Supported Algorithm Suites</a>
     */
    public void setCryptoAlgorithm(CryptoAlgorithm cryptoAlgorithm) {
        this.cryptoAlgorithm = cryptoAlgorithm;
        awsCrypto.setEncryptionAlgorithm(cryptoAlgorithm);
    }

    /**
     * Encrypts the {@code plainText} data using the provided key and encryption context. The encryption context map
     * provides additional authenticated data(AAD) or associated data(AD) to support implementing
     * <a href="https://en.wikipedia.org/wiki/Authenticated_encryption">AEAD</a>. The {@code keyAliases} value would be
     * the aliases of the master keys (For production mode this would be the AWS KMS master keys, and for developer mode
     * this would be the alias of the keystore secret keys).
     * @param keyAliases Aliases of the master keys.
     * @param encryptionContext Additional authenticated/associated data.
     * @param plainText data to be encrypted.
     * @return encrypted data.
     * @see PlacepassMasterKeyProvider
     */
    @SuppressWarnings("unchecked")
    public byte[] encrypt(String[] keyAliases, Map<String, String> encryptionContext, byte[] plainText) {
        if (encryptionContext == null || encryptionContext.isEmpty())
            throw new EncryptionException("encryptionContext cannot be null or empty.");

        try {
            // change the encryptionContext keys to include the key prefix, so that we can exclude AWS entries and validate
            // it during decryption.
            Map<String, String> prefixedEncryptionContext = new HashMap<>(encryptionContext.size());
            for (Map.Entry<String,String> contextEntry : encryptionContext.entrySet()) {
                prefixedEncryptionContext.put(ENCRYPTION_CONTEXT_KEY_PREFIX + contextEntry.getKey(), contextEntry.getValue());
            }
            PlacepassMasterKeyProvider placepassMasterKeyProvider = PlacepassMasterKeyProvider.getInstance(keyAliases);
            CryptoResult<byte[], ? extends MasterKey> cryptoResult = awsCrypto.encryptData(placepassMasterKeyProvider, plainText,
                    prefixedEncryptionContext);
            return cryptoResult.getResult();
        } catch (AwsCryptoException e) {
            throw new EncryptionException("Encryption failed.", e);
        }
    }

    /**
     * Convenience method which a single key alias can be used for encryption.
     *
     * @param keyAlias Alias of the master key.
     * @param encryptionContext Additional authenticated/associated data.
     * @param plainText data to be encrypted.
     * @return encrypted data.
     * @see #encrypt(String, Map, byte[])
     */
    public byte[] encrypt(String keyAlias, Map<String, String> encryptionContext, byte[] plainText) {
        return encrypt(new String[]{keyAlias}, encryptionContext, plainText);
    }

    /**
     * Encrypts the {@code plainText} data using the provided key and encryption context. The encryption context map
     * provides additional authenticated data(AAD) or associated data(AD) to support implementing
     * <a href="https://en.wikipedia.org/wiki/Authenticated_encryption">AEAD</a>. The {@code keyAlias} value would be
     * the alias of themaster key (For production mode this would be the AWS KMS master key, and for developer mode
     * this would be the alias of the keystore secret key). Returned data string is encoded using base64.
     * @param keyAliases Aliases of the master keys.
     * @param encryptionContext Additional authenticated/associated data.
     * @param plainText data to be encrypted.
     * @return encrypted data as base64 string.
     * @see PlacepassMasterKeyProvider
     * @see #encrypt(String[], Map, byte[])
     */
    public String encrypt(String[] keyAliases, Map<String, String> encryptionContext, String plainText) {
        try {
            byte[] encData = encrypt(keyAliases, encryptionContext, plainText.getBytes(DEFAULT_CHARSET));
            return new String(base64Encoder.encode(encData), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EncryptionException(String.format("error encoding to %s.", DEFAULT_CHARSET), e);
        }
    }

    /**
     * Convenience method which a single key alias can be used for encryption.
     *
     * @param keyAlias Alias of the master key.
     * @param encryptionContext Additional authenticated/associated data.
     * @param plainText data to be encrypted.
     * @return encrypted data.
     * @see #encrypt(String, Map, String)
     */
    public String encrypt(String keyAlias, Map<String, String> encryptionContext, String plainText) {
        return encrypt(new String[]{keyAlias}, encryptionContext, plainText);
    }

    /**
     * Decrypts the {@code cipherText} data using the provided {@code keyAlias} and decryption context. The decryption
     * context needed to contain the same values used during the encryption operation.
     * @param keyAliases Aliases of the master keys.
     * @param decryptionContext Same additional authenticated/associated data that used for encryption.
     * @param cipherText encrypted contents
     * @return Decrypted message
     * @see #encrypt(String[], Map, byte[])
     */
    @SuppressWarnings("unchecked")
    public byte[] decrypt(String[] keyAliases, Map<String, String> decryptionContext, byte[] cipherText) {
        if (decryptionContext == null || decryptionContext.isEmpty())
            throw new EncryptionException("encryptionContext cannot be null or empty.");

        // Check and validate all prefixed keys to make sure we have the correct decryptionContext is available
        ParsedCiphertext parsedCiphertext = new ParsedCiphertext(cipherText);
        Map<String, String> cipherContextMap = parsedCiphertext.getEncryptionContextMap();
        int keyCount = 0;
        for (Map.Entry<String,String> contextEntry : cipherContextMap.entrySet()) {
            String key = contextEntry.getKey();
            //ignore keys that does not start with the key prefix, i.e. AWS added keys
            if (!key.startsWith(ENCRYPTION_CONTEXT_KEY_PREFIX))
                continue;
            String keyWithoutPrefix = key.replace(ENCRYPTION_CONTEXT_KEY_PREFIX, "");
            String decryptCtxKeyValue = decryptionContext.get(keyWithoutPrefix);
            if (decryptCtxKeyValue == null || !decryptCtxKeyValue.equals(contextEntry.getValue()))
                throw new EncryptionException("Invalid decryption context.");
            keyCount++;
        }

        //check the matched key sizes are equal to decryptionContext size
        if (keyCount != decryptionContext.size())
            throw new EncryptionException("Invalid decryption context.");

        try {
            PlacepassMasterKeyProvider placepassMasterKeyProvider = PlacepassMasterKeyProvider.getInstance(keyAliases);
            CryptoResult<byte[], ? extends MasterKey> cryptoResult = awsCrypto.decryptData(placepassMasterKeyProvider,
                    parsedCiphertext);
            return cryptoResult.getResult();
        } catch (AwsCryptoException e) {
            throw new EncryptionException("Decryption failed.", e);
        }
    }

    /**
     * Convenience method which a single key alias can be used for decryption.
     *
     * @param keyAlias Alias of the master key.
     * @param decryptionContext Same additional authenticated/associated data that used for encryption.
     * @param cipherText encrypted contents
     * @return Decrypted message
     * @see #decrypt(String[], Map, byte[])
     */
    public byte[] decrypt(String keyAlias, Map<String, String> decryptionContext, byte[] cipherText) {
        return decrypt(new String[]{keyAlias}, decryptionContext, cipherText);
    }

    /**
     * Decrypts the {@code cipherText} data using the provided {@code keyAlias} and decryption context. The decryption
     * context needed to contain the same values used during the encryption operation.
     * @param keyAliases Aliases of the master keys.
     * @param decryptionContext Same additional authenticated/associated data that used for encryption.
     * @param cipherText base 64 encoded encrypted contents
     * @return Decrypted message
     * @see #encrypt(String[], Map, String)
     */
    public String decrypt(String[] keyAliases, Map<String, String> decryptionContext, String cipherText) {
        try {
            byte[] decData = decrypt(keyAliases, decryptionContext, base64Decoder.decode(cipherText.getBytes
                    (DEFAULT_CHARSET)));
            return new String(decData, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EncryptionException(String.format("error encoding to %s.", DEFAULT_CHARSET), e);
        }
    }

    /**
     * Convenience method which a single key alias can be used for decryption.
     *
     * @param keyAlias Alias of the master key.
     * @param decryptionContext Same additional authenticated/associated data that used for encryption.
     * @param cipherText encrypted contents
     * @return Decrypted message
     * @see #decrypt(String[], Map, String)
     */
    public String decrypt(String keyAlias, Map<String, String> decryptionContext, String cipherText) {
        return decrypt(new String[]{keyAlias}, decryptionContext, cipherText);
    }
}
