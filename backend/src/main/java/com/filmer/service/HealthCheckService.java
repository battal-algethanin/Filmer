package com.filmer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Service for performing health checks including database connectivity.
 * Used for end-to-end connectivity testing and monitoring.
 */
@Service
public class HealthCheckService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Checks database connectivity by executing a simple SELECT 1 query.
     * 
     * @return A map containing:
     *         - "success": true if the query executed successfully
     *         - "result": The query result (integer 1)
     *         - "message": A descriptive message
     *         - "database_status": "UP" if connection successful, "DOWN" otherwise
     */
    public Map<String, Object> checkDatabaseConnectivity() {
        try {
            // Execute a simple query to test database connectivity
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            
            return Map.of(
                "success", true,
                "result", result,
                "message", "Database connection successful",
                "database_status", "UP"
            );
        } catch (Exception e) {
            return Map.of(
                "success", false,
                "result", null,
                "message", "Database connection failed: " + e.getMessage(),
                "database_status", "DOWN"
            );
        }
    }
}
