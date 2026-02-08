package com.filmer.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for movie list items.
 * Contains summary information for displaying in lists.
 */
public class MovieListItemResponse {

    private String id;
    private String title;
    private Short year;
    private String director;
    private BigDecimal rating;
    private Integer numVotes;
    private List<String> genres;
    private List<StarSummary> stars;

    public MovieListItemResponse() {
    }

    // Getters and Setters

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

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<StarSummary> getStars() {
        return stars;
    }

    public void setStars(List<StarSummary> stars) {
        this.stars = stars;
    }

    /**
     * Star summary for movie list display.
     */
    public static class StarSummary {
        private String id;
        private String name;

        public StarSummary() {
        }

        public StarSummary(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
