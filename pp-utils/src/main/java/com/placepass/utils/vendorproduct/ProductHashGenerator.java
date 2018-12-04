package com.placepass.utils.vendorproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Product code hash and dehash utilities. This house a specific logic used by PlacePass for generating hashed value.
 * 
 * <p>
 * This maintains character map instances for hashing/dehashing purposes and hash-multiplier, hash-adder as instance
 * variables. With use of new set of hashMultiplier, hashAdder constants; need to get a new instance of
 * {@link ProductHashGenerator} which will initialize maps using those constants.
 * 
 * @author wathsala.w
 *
 */
public class ProductHashGenerator {

    public static int VENDOR_KEY_LENGTH = 6;

    String plain = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-";

    String hashed = "07ELSZgnu18FMTahov29GNUbipw3AHOVcjqx4BIPWdkry5CJQXelsz6DKRYfmt*";

    /**
     * Get {@link ProductHashGenerator} instance.
     * 
     * @param hashMultiplier hash multiplier constant
     * @param hashAdder hash adder constant
     * @return
     */
    public static ProductHashGenerator getInstance(int hashMultiplier, int hashAdder) {

        return new ProductHashGenerator(hashMultiplier, hashAdder);
    }

    /**
     * Constructor which initializes character maps.
     * 
     * @param hashMultiplier hash multiplier constant
     * @param hashAdder hash adder constant
     */
    private ProductHashGenerator(int hashMultiplier, int hashAdder) {
    }

    /**
     * PlacePass product code hash generation.
     * 
     * @param plaintext input text to be hashed.
     * @return hashed string.
     */
    public String generateHash(String plaintext) {
        String hashedValue = hashReplacer(plain, hashed, plaintext);
        return hashedValue;
    }

    /**
     * Generate original value (PlacePass product code) from hashed value.
     * 
     * @param hashedValue hashed value
     * @return
     */
    public String degenerateHash(String hashedValue) {
        String plaintext = hashReplacer(hashed, plain, hashedValue);
        return plaintext;
    }

    public String hashReplacer(String from, String to, String text) {
        if (!StringUtils.isNoneBlank(text)) {
            return text;
        }
        String newTxt = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int inx = from.indexOf(c);
            if (inx >= 0) {
                newTxt += to.charAt(inx);
            } else {
                newTxt += c;
            }
        }
        return newTxt;
    }
}
