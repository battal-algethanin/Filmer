package com.filmer.dto.request;

/**
 * Request DTO for processing checkout.
 * Contains credit card information for payment validation.
 */
public class CheckoutRequest {

    private String creditCardId;
    private String firstName;
    private String lastName;
    private String expiration;

    public CheckoutRequest() {
    }

    public CheckoutRequest(String creditCardId, String firstName, String lastName, String expiration) {
        this.creditCardId = creditCardId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expiration = expiration;
    }

    // Getters and Setters

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
