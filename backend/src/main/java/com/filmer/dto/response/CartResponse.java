package com.filmer.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for shopping cart.
 */
public class CartResponse {

    private List<CartItemResponse> items;
    private Integer itemCount;
    private BigDecimal totalPrice;

    public CartResponse() {
    }

    // Getters and Setters

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Individual cart item.
     */
    public static class CartItemResponse {
        private String movieId;
        private String title;
        private Short year;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;

        public CartItemResponse() {
        }

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Short getYear() {
            return year;
        }

        public void setYear(Short year) {
            this.year = year;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }
    }
}
