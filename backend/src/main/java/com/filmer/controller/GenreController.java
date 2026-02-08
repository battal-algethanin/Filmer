package com.filmer.controller;

import com.filmer.dto.response.ApiResponse;
import com.filmer.dto.response.GenreListResponse;
import com.filmer.dto.response.GenreMoviesResponse;
import com.filmer.dto.response.GenreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Controller for genre-related endpoints.
 * Handles genre listing and browsing movies by genre.
 */
@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {

    /**
     * Get all available genres.
     *
     * <p>Returns a list of all movie genres in the system.
     * This endpoint does not require pagination as the genre list is typically small.</p>
     *
     * @return ResponseEntity containing list of all genres
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Genres retrieved successfully</li>
     * </ul>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<GenreListResponse>> listGenres() {
        // TODO: Implement genre listing logic
        // 1. Query all genres from database
        // 2. Map entities to DTOs
        GenreListResponse response = new GenreListResponse(Collections.emptyList());
        return ResponseEntity.status(501).body(ApiResponse.success(response));
    }

    /**
     * Get movies filtered by a specific genre.
     *
     * <p>Returns a paginated list of movies belonging to the specified genre.
     * Supports sorting by title, year, or rating.</p>
     *
     * @param genreId The unique identifier of the genre
     * @param page    Page number (1-indexed), defaults to 1
     * @param size    Number of items per page, defaults to 20, max 100
     * @param sortBy  Field to sort by: title, year, or rating. Defaults to title
     * @param order   Sort order: asc or desc. Defaults to asc
     * @return ResponseEntity containing genre info and paginated movie list
     *
     * <p><b>Path Parameters:</b></p>
     * <ul>
     *   <li>genreId (required) - Genre ID, min 1</li>
     * </ul>
     *
     * <p><b>Query Parameters:</b></p>
     * <ul>
     *   <li>page (optional) - Page number, min 1, default 1</li>
     *   <li>size (optional) - Items per page, min 1, max 100, default 20</li>
     *   <li>sortBy (optional) - Sort field: title|year|rating, default title</li>
     *   <li>order (optional) - Sort order: asc|desc, default asc</li>
     * </ul>
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Movies retrieved successfully</li>
     *   <li>404 Not Found - Genre not found with given ID</li>
     * </ul>
     */
    @GetMapping("/{genreId}/movies")
    public ResponseEntity<ApiResponse<GenreMoviesResponse>> getMoviesByGenre(
            @PathVariable Long genreId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        // TODO: Implement genre movies retrieval logic
        // 1. Verify genre exists, return 404 if not
        // 2. Validate pagination parameters
        // 3. Query movies by genre with pagination
        // 4. Map entities to DTOs
        GenreMoviesResponse response = new GenreMoviesResponse();
        response.setGenre(new GenreResponse(genreId, "Genre"));
        response.setItems(Collections.emptyList());
        response.setPage(page);
        response.setSize(size);
        response.setTotalItems(0);
        response.setTotalPages(0);
        return ResponseEntity.status(501).body(ApiResponse.success(response));
    }
}
