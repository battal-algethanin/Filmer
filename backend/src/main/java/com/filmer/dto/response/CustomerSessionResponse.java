package com.filmer.dto.response;

/**
 * Response DTO for customer session information.
 */
public class CustomerSessionResponse {

    private boolean authenticated;
    private Long customerId;
    private String email;
    private String firstName;
    private String lastName;

    public CustomerSessionResponse() {
    }

    public CustomerSessionResponse(Long customerId, String email, String firstName, String lastName) {
        this.authenticated = true;
        this.customerId = customerId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
