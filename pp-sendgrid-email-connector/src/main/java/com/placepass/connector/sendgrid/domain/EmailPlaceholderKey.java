package com.placepass.connector.sendgrid.domain;

/**
 * This defines email placeholder keys, and can support support add/update template and template placeholder read
 * activities.
 * 
 * // FIXME: it could be that any keys which are not available in PlatformEventKey from pp-utils are defined here, or
 * the full list.
 * 
 * @author wathsala.w
 *
 */
public enum EmailPlaceholderKey {
    
    /**
     * This is a per partner key, which will carry a URL back to partner site with ability populate {} place-holders.
     */
    MORE_DETAILS_URL;
}
