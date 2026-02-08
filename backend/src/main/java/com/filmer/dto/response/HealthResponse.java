package com.filmer.dto.response;

import java.time.Instant;

/**
 * Health check response DTO.
 * Returns the status of the service and its dependencies.
 */
public class HealthResponse {

    private String status;
    private String database;
    private Instant timestamp;

    public HealthResponse() {
        this.timestamp = Instant.now();
    }

    public HealthResponse(String status, String database) {
        this();
        this.status = status;
        this.database = database;
    }

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
