package com.filmer.dto.request;

/**
 * Request DTO for updating cart item quantity.
 */
public class UpdateCartItemRequest {

    private Integer quantity;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(Integer quantity) {
        this.quantity = quantity;
    }

    // Getters and Setters

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
