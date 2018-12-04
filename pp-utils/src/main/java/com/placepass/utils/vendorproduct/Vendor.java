package com.placepass.utils.vendorproduct;

/**
 * Represents platform supported vendor codes (6 characters per code).
 * 
 * <p>
 * Later on vendor will be onboarding via a process.
 * 
 * @author wathsala.w
 *
 */
public enum Vendor {

    /**
     * Musement
     */
    MUSEME("Musement"),

    /**
     * Get Your Guide
     */
    GETYGU("GetYourGuide"),

    /**
     * Viator
     */
    VIATOR("Viator"),

    /**
     * URBANADVENTURES
     */
    URBANA("UrbanAdventures"),

    /**
     * HeadOut
     */
    HADOUT("Headout"),

    /**
     * Isango
     */
    ISANGO("Isango"),

    /**
     * IfOnly
     */
    IFONLY("IfOnly"),

    /**
     * ProjectExpedition
     */
    PROEXP("ProjectExpedition"),

    /**
     * Tiqets
     */
    TIQETS("Tiqets"),

    /**
     * Virtual Partners
     */
    VIREAL("VirtualPartners"),

    /**
     * Ticketmaster
     */
    TKTMST("Ticketmaster"),

    /**
     * Be My Guest
     */
    BEMYGT("BeMyGuest"),

    /**
     * City Discovery
     */
    CTYDSY("CityDiscovery"),

    /**
     * Go Be
     */
    GOBEEE("GoBe"),
	
	 /**
     * Starwood Rewards
     */
    SPGRWD("StarwoodRewards"),
	
	/**
     * Marriott Rewards
     */
    MARRWD("MarriottRewards"),
	
	/**
     * StubHub
     */
	STBHUB("StubHub"),

    /**
     * Proprietary
     */
    PRPRTY("Proprietary"),

    /**
     * Marriott Pilot
     */
    MARRTT("Marriott");

    private String label;

    Vendor(String label) {
        this.label = label;
    }

    public static Vendor getVendor(String name) {
        try {
            return Vendor.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid vendor name: " + name);
        }
    }

    public String getLabel() {
        return label;
    }
}
