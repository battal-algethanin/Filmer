package com.filmer.dto.response;

/**
 * Response DTO for star list items.
 * Contains summary information for displaying in lists.
 */
public class StarListItemResponse {

    private String id;
    private String name;
    private Short birthYear;
    private Integer movieCount;

    public StarListItemResponse() {
    }

    // Getters and Setters

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

    public Short getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Short birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(Integer movieCount) {
        this.movieCount = movieCount;
    }
}
