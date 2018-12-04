# Placepass Encryption Utils
----
A utility module which provides support for cryptographic encryption and decryption operations. Currently this module supports the following cryptograhic algorithms;

* [AWS KMS Encryption][1]

Additionally following utility classes are also provided.

* Mask Formatter
  A utility class that can mask defined sections of a string.

### Pre-Build Instruction
--------------------------
* Create **`GRADLE_USER_HOME`** environment variable and set **`~/.gradle`** location for UNIX or **`%USERPROFILE%\.gradle`** location for Windows.
* Create **`gradle.properties`** file on above location if it doesn't already exists.
* Edit **`gradle.properties`** file and add **`GuserName`** and **`Gpassword`** properties with empty values.

### AWS KMS Configurations
----
\
**Note: For Oracle JDK/JVM installations, make sure to install the [JCE unlimited strength policy files][4]**.
\
\
AWS KMS uses the [Customer Master Keys (CMKs)][2] defined in the AWS account to protect the data keys generated during the encryption process. Therefore the environments that utilize this module would need to have the correct AWS access credentials & profiles configured. For AWS managed environments such as EC2 instances, this can be configured via the IAM roles. For other non-AWS environments, the [AWS security credentials/profiles][3] needed to be setup.

For development purposes, a developer mode is provided. **This mode should not be configured or used in any of the AWS managed environments.** Developer mode uses a Java Keystore to simulate the some of the AWS KMS operations. In order to use the developer mode, a PKCS#12 Java Keystore with one or more AES secret keys needed to be created. Use the same AWS KMS CMK aliases when creating the Keystore entries.

```bash
keytool -genseckey -keystore kms.keystore -alias "my-key" -storetype pkcs12 -keyalg "AES" -keysize 192 -v
```
Default lookup path for the generated Keystore file would be `<user_home>/.placepass/kms.keystore`. However this can be overridden by using the `PP_KMS_KEYSTORE_FILE` environment variable. Default password for the Keystore needed to be given as `changeme`. The `PP_KMS_KEYSTORE_PASSWORD` environment variable can be used to override this.

>If AWS security credentials/profiles are already configured for non-AWS environment, developer mode will not get enabled.

#### Usage Examples
---
* AWS KMS encrypt
```java
import java.util.Map;
import java.util.HashMap;
import com.placepass.encryption.kms.AwsKmsCryptoUtil;

public static void main(String[] args) {
    String plainText = args[0];
    String keyAlias = args[1];
    String contextValue1 = args[2];
    String contextValue2 = args[3];
    String contextValue3 = args[4];
    
    // Using encryptionContext map to provide additional authenticated data(AAD)
    // to implement AEAD. (see https://en.wikipedia.org/wiki/Authenticated_encryption)
    Map<String, String> encryptionContext = new HashMap<>(0);
    encryptionContext.put("AAD-1", contextValue1);
    encryptionContext.put("AAD-2", contextValue2);
    encryptionContext.put("AAD-3", contextValue3);

    AwsKmsCryptoUtil awsKmsCryptoUtil = AwsKmsCryptoUtil.getInstance();
    String cipherText = awsKmsCryptoUtil.encrypt(keyAlias, encryptionContext, plainText);
}
```

* AWS KMS Decrypt
```java
import java.util.Map;
import java.util.HashMap;
import com.placepass.encryption.kms.AwsKmsCryptoUtil;

public static void main(String[] args) {
    String cipherText = args[0];
    String keyAlias = args[1];
    String contextValue1 = args[2];
    String contextValue2 = args[3];
    String contextValue3 = args[4];
    
    // Using encryptionContext map to provide additional authenticated data(AAD)
    // to implement AEAD. (see https://en.wikipedia.org/wiki/Authenticated_encryption)
    Map<String, String> encryptionContext = new HashMap<>(0);
    encryptionContext.put("AAD-1", contextValue1);
    encryptionContext.put("AAD-2", contextValue2);
    encryptionContext.put("AAD-3", contextValue3);

    AwsKmsCryptoUtil awsKmsCryptoUtil = AwsKmsCryptoUtil.getInstance();
    String plainText = awsKmsCryptoUtil.decrypt(keyAlias, encryptionContext, cipherText);
}
```

* Masking
```java
import java.util.Map;
import java.util.HashMap;
import com.placepass.encryption.text.MaskFormatter;

public static void main(String[] args) {
    String sourceStr = args[0];

    MaskFormatter maskFormatter = new MaskFormatter('*',
            new MaskFormatter.Segment(1, 1), // mask 2nd character
            new MaskFormatter.Segment(-11, 3), // mask 3 characters from 11th position of the string end
            new MaskFormatter.Segment(14, -4)); // mask everything from 14th character to (string.length-4)
			
    String maskedString = maskFormatter.format(sourceStr);
}
```

[1]: http://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/introduction.html
[2]: http://docs.aws.amazon.com/kms/latest/developerguide/concepts.html#master_keys
[3]: http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html
[4]: http://www.oracle.com/technetwork/java/javase/downloads/index.html#other