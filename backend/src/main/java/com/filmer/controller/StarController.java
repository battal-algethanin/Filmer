package com.filmer.controller;

import com.filmer.dto.response.ApiResponse;
import com.filmer.dto.response.PaginatedResponse;
import com.filmer.dto.response.StarDetailResponse;
import com.filmer.dto.response.StarListItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Controller for star-related endpoints.
 * Handles star listing, searching, and detail retrieval.
 */
@RestController
@RequestMapping("/api/v1/stars")
public class StarController {

    /**
     * Get a paginated list of stars with optional sorting and name search.
     *
     * <p>Returns a paginated list of stars. Supports sorting by name or birth year,
     * and filtering by partial name match.</p>
     *
     * @param page   Page number (1-indexed), defaults to 1
     * @param size   Number of items per page, defaults to 20, max 100
     * @param sortBy Field to sort by: name or birthYear. Defaults to name
     * @param order  Sort order: asc or desc. Defaults to asc
     * @param name   Filter stars by name (partial, case-insensitive match)
     * @return ResponseEntity containing paginated star list
     *
     * <p><b>Query Parameters:</b></p>
     * <ul>
     *   <li>page (optional) - Page number, min 1, default 1</li>
     *   <li>size (optional) - Items per page, min 1, max 100, default 20</li>
     *   <li>sortBy (optional) - Sort field: name|birthYear, default name</li>
     *   <li>order (optional) - Sort order: asc|desc, default asc</li>
     *   <li>name (optional) - Name search filter (partial match)</li>
     * </ul>
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Stars retrieved successfully</li>
     *   <li>400 Bad Request - Invalid query parameters</li>
     * </ul>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<StarListItemResponse>>> listStars(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String name) {
        // TODO: Implement star listing logic
        // 1. Validate pagination parameters
        // 2. Validate sort field and order
        // 3. Apply name filter if provided
        // 4. Query database with pagination
        // 5. Map entities to DTOs with movie count
        PaginatedResponse<StarListItemResponse> response = new PaginatedResponse<>(
                Collections.emptyList(), page, size, 0
        );
        return ResponseEntity.status(501).body(ApiResponse.success(response));
    }

    /**
     * Get detailed information about a specific star.
     *
     * <p>Returns comprehensive star details including all movies
     * the star has appeared in.</p>
     *
     * @param starId The unique identifier of the star (max 10 chars)
     * @return ResponseEntity containing detailed star information
     *
     * <p><b>Path Parameters:</b></p>
     * <ul>
     *   <li>starId (required) - Star ID, max 10 characters</li>
     * </ul>
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Star details retrieved successfully</li>
     *   <li>404 Not Found - Star not found with given ID</li>
     * </ul>
     */
    @GetMapping("/{starId}")
    public ResponseEntity<ApiResponse<StarDetailResponse>> getStarDetails(
            @PathVariable String starId) {
        // TODO: Implement star detail retrieval logic
        // 1. Query star by ID with associated movies
        // 2. Return 404 if not found
        // 3. Map entity to DTO
        StarDetailResponse response = new StarDetailResponse();
        response.setId(starId);
        return ResponseEntity.status(501).body(ApiResponse.success(response));
    }
}
