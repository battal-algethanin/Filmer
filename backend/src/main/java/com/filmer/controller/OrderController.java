package com.filmer.controller;

import com.filmer.dto.response.ApiResponse;
import com.filmer.dto.response.OrderDetailResponse;
import com.filmer.dto.response.OrderListItemResponse;
import com.filmer.dto.response.PaginatedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Controller for order-related endpoints.
 * Handles order history and order detail retrieval.
 * All endpoints require authentication.
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    /**
     * Get order history for the authenticated customer.
     *
     * <p>Returns a paginated list of orders placed by the authenticated customer.
     * Orders are sorted by date descending (newest first).</p>
     *
     * @param page    Page number (1-indexed), defaults to 1
     * @param size    Number of items per page, defaults to 20, max 100
     * @param session The HTTP session containing customer authentication
     * @return ResponseEntity containing paginated order list
     *
     * <p><b>Query Parameters:</b></p>
     * <ul>
     *   <li>page (optional) - Page number, min 1, default 1</li>
     *   <li>size (optional) - Items per page, min 1, max 100, default 20</li>
     * </ul>
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Orders retrieved successfully</li>
     *   <li>401 Unauthorized - Not authenticated</li>
     * </ul>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<OrderListItemResponse>>> listOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            HttpSession session) {
        // TODO: Implement order listing logic
        // 1. Verify customer is authenticated
        // 2. Query orders for customer with pagination
        // 3. Map to DTOs with item counts and totals
        PaginatedResponse<OrderListItemResponse> response = new PaginatedResponse<>(
                Collections.emptyList(), page, size, 0
        );
        return ResponseEntity.status(501).body(ApiResponse.success(response));
    }

    /**
     * Get details of a completed order.
     *
     * <p>Returns detailed information about a specific order including all items.
     * The order must belong to the authenticated customer.</p>
     *
     * @param orderId The unique identifier of the order
     * @param session The HTTP session containing customer authentication
     * @return ResponseEntity containing order details
     *
     * <p><b>Path Parameters:</b></p>
     * <ul>
     *   <li>orderId (required) - Order ID, min 1</li>
     * </ul>
     *
     * <p><b>Responses:</b></p>
     * <ul>
     *   <li>200 OK - Order details retrieved successfully</li>
     *   <li>401 Unauthorized - Not authenticated</li>
     *   <li>403 Forbidden - Order belongs to another customer</li>
     *   <li>404 Not Found - Order not found</li>
     * </ul>
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrderDetails(
            @PathVariable Long orderId,
            HttpSession session) {
        // TODO: Implement order detail retrieval logic
        // 1. Verify customer is authenticated
        // 2. Query order by ID
        // 3. Verify order belongs to authenticated customer
        // 4. Return 404 if not found, 403 if wrong customer
        // 5. Map entity to DTO
        OrderDetailResponse response = new OrderDetailResponse();
        response.setOrderId(orderId);
        return ResponseEntity.status(501).body(ApiResponse.success(response));
    }
}
