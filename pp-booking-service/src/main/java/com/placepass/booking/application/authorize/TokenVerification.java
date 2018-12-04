package com.placepass.booking.application.authorize;

import java.util.ArrayList;
import java.util.List;

public class TokenVerification {

    /** The user id. */
    private String userId;

    /** The username. */
    private String username;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The permissions. */
    private List<UserPermission> permissions = new ArrayList<UserPermission>();

    private String message;

    private String statusCode;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the first name.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    public List<UserPermission> getPermissions() {
        return permissions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
