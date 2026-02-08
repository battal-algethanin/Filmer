package com.filmer.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for genre movies browsing.
 * Includes genre info with paginated movie list.
 */
public class GenreMoviesResponse {

    private GenreResponse genre;
    private List<GenreMovieItem> items;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

    public GenreMoviesResponse() {
    }

    // Getters and Setters

    public GenreResponse getGenre() {
        return genre;
    }

    public void setGenre(GenreResponse genre) {
        this.genre = genre;
    }

    public List<GenreMovieItem> getItems() {
        return items;
    }

    public void setItems(List<GenreMovieItem> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Movie item for genre browsing.
     */
    public static class GenreMovieItem {
        private String id;
        private String title;
        private Short year;
        private String director;
        private BigDecimal rating;

        public GenreMovieItem() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public BigDecimal getRating() {
            return rating;
        }

        public void setRating(BigDecimal rating) {
            this.rating = rating;
        }
    }
}
