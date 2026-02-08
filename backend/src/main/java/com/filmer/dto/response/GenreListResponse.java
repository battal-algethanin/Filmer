package com.filmer.dto.response;

import java.util.List;

/**
 * Response DTO for genre list.
 */
public class GenreListResponse {

    private List<GenreResponse> items;

    public GenreListResponse() {
    }

    public GenreListResponse(List<GenreResponse> items) {
        this.items = items;
    }

    public List<GenreResponse> getItems() {
        return items;
    }

    public void setItems(List<GenreResponse> items) {
        this.items = items;
    }
}
