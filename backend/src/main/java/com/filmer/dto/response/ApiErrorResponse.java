package com.filmer.dto.response;

import java.time.Instant;

/**
 * Standard API error response for all error cases.
 * Provides a consistent error structure across all endpoints.
 */
public class ApiErrorResponse {

    private boolean success;
    private ErrorDetails error;
    private Instant timestamp;

    public ApiErrorResponse() {
        this.success = false;
        this.timestamp = Instant.now();
    }

    public ApiErrorResponse(String code, String message) {
        this();
        this.error = new ErrorDetails(code, message);
    }

    public ApiErrorResponse(String code, String message, Object details) {
        this();
        this.error = new ErrorDetails(code, message, details);
    }

    /**
     * Creates an error response with the given code and message.
     *
     * @param code    The error code
     * @param message The human-readable error message
     * @return An ApiErrorResponse instance
     */
    public static ApiErrorResponse of(String code, String message) {
        return new ApiErrorResponse(code, message);
    }

    /**
     * Creates an error response with additional details.
     *
     * @param code    The error code
     * @param message The human-readable error message
     * @param details Additional error details
     * @return An ApiErrorResponse instance
     */
    public static ApiErrorResponse of(String code, String message, Object details) {
        return new ApiErrorResponse(code, message, details);
    }

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorDetails getError() {
        return error;
    }

    public void setError(ErrorDetails error) {
        this.error = error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Inner class representing error details.
     */
    public static class ErrorDetails {
        private String code;
        private String message;
        private Object details;

        public ErrorDetails() {
        }

        public ErrorDetails(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public ErrorDetails(String code, String message, Object details) {
            this.code = code;
            this.message = message;
            this.details = details;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }
    }
}
