package com.placepass.encryption.kms;

import com.amazonaws.SDKGlobalConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AwsKmsCryptoUtilTest {
    @ClassRule
    public static EnvironmentVariables environmentVariables = new EnvironmentVariables();

    // Setting "user.home" property to the class output path, to avoid loading any aws credentials or user kms.keystore
    // files.
    @ClassRule
    public static ProvideSystemProperty sysPropUserHome = new ProvideSystemProperty("user.home",
            AwsKmsCryptoUtilTest.class.getResource("/").getPath());

    // Setting ec2 metadata host to non existing value to override default ec2 metadata host. Needed for running
    // within EC2 instances.
    @ClassRule
    public static ProvideSystemProperty sysPropEC2MetaHost = new ProvideSystemProperty(SDKGlobalConfiguration
            .EC2_METADATA_SERVICE_OVERRIDE_SYSTEM_PROPERTY, "com.placepass.encryption.kms.non.existing.host");

    private static String keyAlias = "com.placepass.encryption.kms.api-enc-test-key";

    @BeforeClass
    public static void initTests() {
        try {
            // Remove any existing AWS related environment variables temporarily to avoid getting loaded by the KmsAPIs
            environmentVariables.set("AWS_ACCESS_KEY_ID", null);
            environmentVariables.set("AWS_SECRET_ACCESS_KEY", null);
            environmentVariables.set("AWS_SESSION_TOKEN", null);
            environmentVariables.set("AWS_SHARED_CREDENTIALS_FILE", null);
            environmentVariables.set("AWS_CONFIG_FILE", null);

            // create .placepass/kms.keystore in the temp user.home
            File tmpUsrPPHome = new File(AwsKmsCryptoUtilTest.class.getResource("/").getPath(), ".placepass");
            tmpUsrPPHome.mkdirs();

            File keyStorePath = new File(tmpUsrPPHome, "kms.keystore");
            char[] password = "changeme".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            keyStore.load(null, password);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(192);
            SecretKey secretKey = keyGenerator.generateKey();
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
            keyStore.setEntry(keyAlias, secretKeyEntry, new KeyStore.PasswordProtection(password));
            FileOutputStream ksFileOutputStream = new FileOutputStream(keyStorePath);
            keyStore.store(ksFileOutputStream, password);
            ksFileOutputStream.flush();
            ksFileOutputStream.close();
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Unable to create/read test keystore file.");
        }
    }

    @AfterClass
    public static void tearDown() {
        // remove created keystore files & directories
        File kmsKSPath = new File(AwsKmsCryptoUtilTest.class.getResource("/.placepass/kms.keystore").getPath());
        if (kmsKSPath.exists()) {
            kmsKSPath.delete();
            kmsKSPath.getParentFile().delete();
        }
    }

    @Ignore
    @Test
    public void encryptionDecryption() {
        String plainText = "Value to be encrypted";
        // define additional authenticated data
        Map<String, String> encryptionContext = new HashMap<>();
        encryptionContext.put("additional-data-1", "value-1");
        encryptionContext.put("additional-data-2", "value-2");

        // initialize and encrypt plain text
        AwsKmsCryptoUtil awsKmsCryptoUtil = AwsKmsCryptoUtil.getInstance();
        String cipherText = awsKmsCryptoUtil.encrypt(keyAlias, encryptionContext, plainText);

        assertNotNull("cipherText cannot be null.", cipherText);

        // decrypt using the same key and also the additional authenticated data
        String decryptedText = awsKmsCryptoUtil.decrypt(keyAlias, encryptionContext, cipherText);
        assertNotNull("decryptedText cannot be null.", decryptedText);
        assertEquals("plain text value and decrypted value is not equal.", plainText, decryptedText);
    }
}
