package com.filmer.dto.request;

/**
 * Request DTO for adding an item to the cart.
 */
public class AddToCartRequest {

    private String movieId;
    private Integer quantity = 1;

    public AddToCartRequest() {
    }

    public AddToCartRequest(String movieId, Integer quantity) {
        this.movieId = movieId;
        this.quantity = quantity != null ? quantity : 1;
    }

    // Getters and Setters

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
