package com.placepass.booking.infrastructure;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placepass.encryption.kms.AwsKmsCryptoUtil;
import com.placepass.encryption.text.MaskFormatter;

@Service
public class CryptoServiceImpl implements CryptoService {
	// Using a prefix to identify non encrypted contents vs encrypted.
	// Required for backward compatibility
	private static final String CRYPTO_PREFIX = "pp:crypto-service:";
	
	@Value("${crypto.aws.kms.key}")
	private String kmsKeyAliasesProperty;
	
	@Value("${crypto.mask.character}")
	private char maskCharacter;
	
	@Value("${crypto.mask.limit.min: 0}")
	private int maskCharactersMinLimit;
	
	@Value("${crypto.mask.limit.max: 0}")
	private int maskCharactersMaxLimit;
	
	private String[] kmsAliases;
	
	@PostConstruct
	public void init() {
		kmsAliases = kmsKeyAliasesProperty.split(",");
		for (int i = 0; i < kmsAliases.length; i++) {
			kmsAliases[i] = kmsAliases[i].trim();
		}
	}
	
	public String awsKmsEncrypt(String plainText, Map<String, String> encryptionContext) {
		AwsKmsCryptoUtil awsKmsCryptoUtil = AwsKmsCryptoUtil.getInstance();
		String encryptedValue = awsKmsCryptoUtil.encrypt(kmsAliases, encryptionContext, plainText);
		return CRYPTO_PREFIX + encryptedValue;
	}
	
	public String awsKmsDecrypt(String cipherText, Map<String, String> decryptionContext) {
		// check cipherText prefix to identify encrypted data or non encrypted data
		if (!cipherText.startsWith(CRYPTO_PREFIX))
			return cipherText;
		
		cipherText = cipherText.replace(CRYPTO_PREFIX, "");
		AwsKmsCryptoUtil awsKmsCryptoUtil = AwsKmsCryptoUtil.getInstance();
		return awsKmsCryptoUtil.decrypt(kmsAliases, decryptionContext, cipherText);
	}
	
	public String maskStringToLength(String str, int length) {
		MaskFormatter maskFormatter = new MaskFormatter(maskCharacter, new MaskFormatter.Segment(0, length));
		maskFormatter.ensureMinMaskCharacters(maskCharactersMinLimit);
		maskFormatter.ensureMaxMaskCharacters(maskCharactersMaxLimit);
		return maskFormatter.format(str);
	}

}
