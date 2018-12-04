package com.placepass.encryption.kms;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.DataKey;
import com.amazonaws.encryptionsdk.MasterKey;
import com.amazonaws.encryptionsdk.MasterKeyProvider;
import com.amazonaws.encryptionsdk.MasterKeyRequest;
import com.amazonaws.encryptionsdk.exception.AwsCryptoException;
import com.amazonaws.encryptionsdk.exception.NoSuchMasterKeyException;
import com.amazonaws.encryptionsdk.exception.UnsupportedProviderException;
import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import com.amazonaws.encryptionsdk.multi.MultipleProviderFactory;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.AliasListEntry;
import com.amazonaws.services.kms.model.KeyListEntry;
import com.placepass.encryption.exception.EncryptionException;
import com.placepass.encryption.exception.InitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@link MasterKeyProvider} implementation that provides support for Production Mode and Developer Mode. Production
 * Mode uses the AWS profile information to retrieve and initialize the kms keys and KmsMasterKey instances, and the
 * Developer Mode simply uses a java key store to load the JceMasterKey instances.
 *
 * <b>Note: Production Mode requires the right roles/policies configured for the EC2 instances in order to initialize
 * the AWS profile.</b>
 */
public class PlacepassMasterKeyProvider extends MasterKeyProvider {
    /**
     * environment variable that can be used to define path to a kms keystore file
     */
    private static final String DEV_KMS_KEYSTORE_ENV_FILENAME = "PP_KMS_KEYSTORE_FILE";
    /**
     * environment variable that can be used to define the password of the kms keystore file
     */
    private static final String DEV_KMS_KEYSTORE_ENV_PASSWORD = "PP_KMS_KEYSTORE_PASSWORD";
    /**
     * default kms keystore file located in user's home directory
     */
    private static final String DEV_KMS_KEYSTORE_DEFAULT_FILENAME = ".placepass" + File.separator + "kms.keystore";
    /**
     * default kms keystore password
     */
    private static final String DEV_KMS_KEYSTORE_DEFAULT_PASSWORD = "changeme";
    /**
     * Default Java keystore type
     */
    private static final String DEV_KMS_KEYSTORE_TYPE = "pkcs12";

    private static PlacepassMasterKeyProvider thisInstance;
    private static final Logger logger = LoggerFactory.getLogger(PlacepassMasterKeyProvider.class);

    private AWSKMS awskmsClient;
    private KeyStore keyStore;
    private String keyStorePassword;
    private boolean devMode;
    private MasterKeyProvider masterKeyProviderInternal;
    private KmsMasterKeyProvider kmsMasterKeyProvider;

    private PlacepassMasterKeyProvider() {
        DefaultAWSCredentialsProviderChain awsCredentialsProviderChain = DefaultAWSCredentialsProviderChain
                .getInstance();
        DefaultAwsRegionProviderChain regionProviderChain = new DefaultAwsRegionProviderChain();
        try {
//            AWSCredentials awsCredentials = awsCredentialsProviderChain.getCredentials();
            String currentRegion = regionProviderChain.getRegion();
            awskmsClient = AWSKMSClientBuilder.defaultClient();
            kmsMasterKeyProvider = new KmsMasterKeyProvider(awsCredentialsProviderChain);
            kmsMasterKeyProvider.setRegion(RegionUtils.getRegion(currentRegion));
        } catch (SdkClientException e) {
            logger.debug("Unable to load AWS Profile.", e);
            // unable to load aws profile. switch to developer mode
            devMode = true;
            logger.warn("Development Mode enabled.");
        }
        if (devMode) {
            // devMode: Load keys from keystore
            // check system variables for kms keystore file
            String kmsKeyStore = System.getenv(DEV_KMS_KEYSTORE_ENV_FILENAME);
            if (kmsKeyStore == null)
                kmsKeyStore = System.getProperty("user.home") + File.separator + DEV_KMS_KEYSTORE_DEFAULT_FILENAME;
            keyStorePassword = System.getenv(DEV_KMS_KEYSTORE_ENV_PASSWORD);
            if (keyStorePassword == null)
                keyStorePassword = DEV_KMS_KEYSTORE_DEFAULT_PASSWORD;

            try {
                //try to load the keystore
                keyStore = KeyStore.getInstance(DEV_KMS_KEYSTORE_TYPE);
                FileInputStream ksFileInputStream = new FileInputStream(kmsKeyStore);
                keyStore.load(ksFileInputStream, keyStorePassword.toCharArray());
                ksFileInputStream.close();
            } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
                throw new InitializationException("Unable to initialize kms keystore.", e);
            }
        }
    }

    /**
     * Gets the {@link PlacepassMasterKeyProvider} instance for the given aliases.
     * @param keyAliases - Aliases of the master keys to be used for key encryption
     * @return PlacepassMasterKeyProvider instance
     */
    public static PlacepassMasterKeyProvider getInstance(String... keyAliases) {
        if (thisInstance == null)
            thisInstance = new PlacepassMasterKeyProvider();
        thisInstance.initMasterKey(keyAliases);
        return thisInstance;
    }

    /**
     * Initializes the internal masterKeyProvider instance with the KmsMasterKey or the JceMasterKey, based on the
     * Production mode or Developer mode.
     * @param keyAliases - Aliases of the master keys
     */
    private void initMasterKey(String... keyAliases) {
        // initialize kms or jce keystores with appropriate parameters
        if (!devMode) {
            // init KmsMasterKeys
            // lookup key arn for each of the given aliases
            // key arn is used instead of key id due to issues in current KmsMasterKey & KmsMasterKeyProvider
            // implementations
            List<AliasListEntry> aliasListEntries = awskmsClient.listAliases().getAliases();
            List<KeyListEntry> keyListEntries = awskmsClient.listKeys().getKeys();
            List<MasterKey<KmsMasterKey>> kmsMasterKeyList = new ArrayList<>(keyAliases.length);

            for (String keyAlias : keyAliases) {
                Optional<AliasListEntry> optionalAliasListEntry = aliasListEntries.stream().filter(aliasListEntry ->
                        aliasListEntry.getAliasName().equalsIgnoreCase(String.format("alias/%s", keyAlias))).findAny();
                if (!optionalAliasListEntry.isPresent())
                    throw new EncryptionException(String.format("Unable to lookup aws key matching [%s]", keyAlias));
                AliasListEntry aliasListEntry = optionalAliasListEntry.get();
                Optional<KeyListEntry> optionalKeyListEntry = keyListEntries.stream().filter(keyEntry
                        -> keyEntry.getKeyId().equals(aliasListEntry.getTargetKeyId())).findAny();
                KeyListEntry keyListEntry = optionalKeyListEntry.get();
                KmsMasterKey kmsMasterKey = kmsMasterKeyProvider.getMasterKey(keyListEntry.getKeyArn());
                kmsMasterKeyList.add(kmsMasterKey);
            }
            masterKeyProviderInternal = MultipleProviderFactory.buildMultiProvider(kmsMasterKeyList);
        } else {
            // init JceMasterKeys
            KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(keyStorePassword
                    .toCharArray());
            List<MasterKey<JceMasterKey>> jceMasterKeyList = new ArrayList<>(keyAliases.length);
            for (String keyAlias : keyAliases) {
                try {
                    if (!keyStore.isKeyEntry(keyAlias))
                        throw new EncryptionException(String.format("Unable to lookup keystore alias matching [%s]",
                                keyAlias));

                    KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(keyAlias,
                            passwordProtection);
                    JceMasterKey jceMasterKey = JceMasterKey.getInstance(secretKeyEntry.getSecretKey(), "JavaKeyStore",
                            keyAlias,
                            "AES/GCM/NoPadding");
                    jceMasterKeyList.add(jceMasterKey);
                } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
                    throw new EncryptionException(String.format("Unable to lookup keystore alias matching [%s]",
                            keyAlias), e);
                }
            }
            masterKeyProviderInternal = MultipleProviderFactory.buildMultiProvider(jceMasterKeyList);
        }
    }

    @Override
    public String getDefaultProviderId() {
        return masterKeyProviderInternal.getDefaultProviderId();
    }

    @Override
    public MasterKey getMasterKey(String provider, String keyId) throws UnsupportedProviderException, NoSuchMasterKeyException {
        return masterKeyProviderInternal.getMasterKey(provider, keyId);
    }

    @Override
    public List getMasterKeysForEncryption(MasterKeyRequest request) {
        return masterKeyProviderInternal.getMasterKeysForEncryption(request);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataKey decryptDataKey(CryptoAlgorithm algorithm, Collection collection, Map encryptionContext) throws
            UnsupportedProviderException, AwsCryptoException {
        return masterKeyProviderInternal.decryptDataKey(algorithm, collection, encryptionContext);
    }

}
