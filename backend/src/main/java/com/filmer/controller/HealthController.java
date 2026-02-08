package com.filmer.controller;

import com.filmer.dto.response.ApiResponse;
import com.filmer.dto.response.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for utility endpoints.
 * Provides health check and system status information.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    /**
     * Health check endpoint to verify API availability and database connectivity.
     *
     * <p>This endpoint is used for monitoring and connection testing.
     * It checks the status of the service and its dependencies.</p>
     *
     * @return ResponseEntity containing health status information
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Service is healthy</li>
     *   <li>503 Service Unavailable - Service or database is down</li>
     * </ul>
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<HealthResponse>> checkHealth() {
        // TODO: Implement actual health check logic
        HealthResponse health = new HealthResponse("UP", "UP");
        return ResponseEntity.status(501).body(ApiResponse.success(health));
    }
}
