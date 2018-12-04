package com.placepass.userservice.controller.dto;

public enum Provider {

    GIGYA;

    public static boolean contains(String providerName) {
        for (Provider provider : Provider.values()) {
            if (provider.name().equals(providerName)) {
                return true;
            }
        }

        return false;
    }

}
