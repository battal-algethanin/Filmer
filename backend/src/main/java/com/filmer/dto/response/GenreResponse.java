package com.filmer.dto.response;

/**
 * Response DTO for genre information.
 */
public class GenreResponse {

    private Long id;
    private String name;

    public GenreResponse() {
    }

    public GenreResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
