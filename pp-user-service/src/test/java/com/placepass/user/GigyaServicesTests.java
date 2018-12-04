package com.placepass.user;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.GSRequest;
import com.gigya.socialize.GSResponse;
import com.placepass.userservice.infrastructure.gigya.GSErrorCode;

/**
 * The Class GigyaServicesTests.
 * 
 * Gigya Integration tests
 */
@Ignore
public class GigyaServicesTests {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(GigyaServicesTests.class);

    private final String apiKey = "3_U2YqYPIWaLXpscPh1diu-5MLVD99smo8MmXn1oGkUJvtZIg4XnfMI1Y26cEaRFHt";

    private final String secretKey = "LL0g17qdlBIsuzVRLVCc9P9W80k0Lxxo";

    private final String userKey = "AC7YxEyybwLs";

    private final String accountsExchange = "accounts.exchangeUIDSignature";

    private final String accountInfo = "accounts.getAccountInfo";

    /**
     * Calling the Gigya accounts.exchangeUIDSignature service
     */
    @Test
    public void testGigyaAccountsExchangeUIDSignatureService() {

        GSObject clientParams = new GSObject();
        clientParams.put("UID", "55401058f39f4f5d94d3f94e0a486eac");
        clientParams.put("UIDSignature", "Xi92NMVWYrIlyC8FHg+Or9Ry/yM=");
        clientParams.put("signatureTimestamp", "1506320852");
        clientParams.put("userKey", userKey);
        clientParams.put("secret", secretKey);

        GSRequest request = new GSRequest(apiKey, secretKey, accountsExchange, clientParams, true, userKey);

        GSResponse response = request.send();

        logger.info(" Gigya accounts.exchangeUIDSignature service : " + response.getLog());

        assertEquals(GSErrorCode.SUCCESS.getErrorCode(), response.getErrorCode());

    }

    /**
     * Calling the Gigya accounts.getAccountInfo service
     */
    @Test
    public void testGigyaAccountsGetAccountInfo() {

        GSObject clientParams = new GSObject();
        clientParams.put("UID", "55401058f39f4f5d94d3f94e0a486eac");

        GSRequest request = new GSRequest(apiKey, secretKey, accountInfo, clientParams, true, userKey);

        GSResponse response = request.send();
        
        logger.info("Gigya accounts.getAccountInfo service : "+ response.getResponseText());

        assertEquals(GSErrorCode.SUCCESS.getErrorCode(), response.getErrorCode());
    }

}
