# Frontend ↔ Backend Contract

**Project:** Filmer - IMDb-like Movie Rental Application  
**Course:** SWE 481 - Advanced Web Applications Engineering  
**Version:** 1.0.0  
**Last Updated:** February 2026

---

## Table of Contents

1. [Overview](#overview)
2. [JSON Field Naming Convention](#json-field-naming-convention)
3. [Pagination Format](#pagination-format)
4. [Sorting Convention](#sorting-convention)
5. [Filtering Convention](#filtering-convention)
6. [Date/Time Format](#datetime-format)
7. [Response Envelope](#response-envelope)
8. [Error Response Format](#error-response-format)
9. [Data Type Mappings](#data-type-mappings)
10. [Entity Schemas](#entity-schemas)

---

## Overview

This document defines the strict conventions and contracts between the frontend and backend to ensure consistency, predictability, and ease of integration. Both teams **MUST** adhere to these conventions without deviation.

---

## JSON Field Naming Convention

### Rule: **camelCase** for all JSON field names

- All JSON field names use **camelCase** (first word lowercase, subsequent words capitalized)
- No underscores (`_`) or hyphens (`-`) in field names
- Acronyms are treated as words (e.g., `movieId`, not `movieID`)

### Examples

✅ **Correct:**
```json
{
  "movieId": "tt0000001",
  "firstName": "John",
  "lastName": "Doe",
  "birthYear": 1990,
  "totalItems": 100,
  "numVotes": 50000
}
```

❌ **Incorrect:**
```json
{
  "movie_id": "tt0000001",
  "first-name": "John",
  "MovieID": "tt0000001"
}
```

---

## Pagination Format

All paginated endpoints **MUST** return responses in this exact format:

### Request Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | integer | 1 | Current page number (1-indexed) |
| `size` | integer | 20 | Number of items per page |

### Response Structure

```json
{
  "success": true,
  "data": {
    "items": [],
    "page": 1,
    "size": 20,
    "totalItems": 150,
    "totalPages": 8
  }
}
```

### Field Definitions

| Field | Type | Description |
|-------|------|-------------|
| `items` | array | Array of result objects for current page |
| `page` | integer | Current page number (1-indexed, matches request) |
| `size` | integer | Items per page (matches request) |
| `totalItems` | integer | Total number of items across all pages |
| `totalPages` | integer | Total number of pages |

### Calculation Rules

```
totalPages = ceil(totalItems / size)
```

### Example

Request: `GET /api/v1/movies?page=2&size=10`

Response:
```json
{
  "success": true,
  "data": {
    "items": [
      { "id": "tt0000011", "title": "Movie 11", "year": 2020 },
      { "id": "tt0000012", "title": "Movie 12", "year": 2021 }
    ],
    "page": 2,
    "size": 10,
    "totalItems": 95,
    "totalPages": 10
  }
}
```

### Edge Cases

- Empty results: `items` is an empty array `[]`, `totalItems` is `0`, `totalPages` is `0`
- Page out of range: Return empty `items` array, keep `totalItems` and `totalPages` accurate
- Invalid page/size: Return `400 Bad Request` with validation error

---

## Sorting Convention

### Request Parameters

| Parameter | Type | Allowed Values | Default |
|-----------|------|----------------|---------|
| `sortBy` | string | Endpoint-specific field name | varies |
| `order` | string | `asc`, `desc` | `asc` |

### Rules

1. `sortBy` specifies the field name to sort by (camelCase)
2. `order` specifies the sort direction
3. Default sort order is ascending (`asc`)
4. Invalid `sortBy` values return `400 Bad Request`

### Endpoint-Specific Sort Fields

| Endpoint | Allowed `sortBy` values | Default |
|----------|-------------------------|---------|
| `/api/v1/movies` | `title`, `year`, `rating` | `title` |
| `/api/v1/stars` | `name`, `birthYear` | `name` |
| `/api/v1/search/movies` | `title`, `year`, `rating` | `title` |
| `/api/v1/genres/{id}/movies` | `title`, `year`, `rating` | `title` |

### Example

Request: `GET /api/v1/movies?sortBy=year&order=desc`

---

## Filtering Convention

### Query Parameter Format

Filters are passed as individual query parameters. Multiple filters are combined with **AND** logic.

### Rules

1. Each filter is a separate query parameter
2. Empty or null filter values are ignored
3. String filters use **case-insensitive partial matching** (LIKE `%value%`)
4. Numeric filters use **exact matching** unless range parameters are provided
5. Range filters use `From` and `To` suffixes

### Filter Types

| Type | Format | Example |
|------|--------|---------|
| Exact match | `field=value` | `year=2020` |
| Partial match | `field=value` | `title=shawshank` |
| Range (min) | `fieldFrom=value` | `yearFrom=2000` |
| Range (max) | `fieldTo=value` | `yearTo=2020` |
| ID reference | `fieldId=value` | `genreId=3` |

### Example

Search for drama movies from 2000-2020 directed by someone named "Nolan":

```
GET /api/v1/search/movies?genreId=3&yearFrom=2000&yearTo=2020&director=nolan
```

### Combining Filters

All provided filters are combined with AND:

```
GET /api/v1/search/movies?title=dark&year=2008&director=nolan
```

This returns movies where:
- title contains "dark" AND
- year equals 2008 AND
- director contains "nolan"

---

## Date/Time Format

### Standard: **ISO-8601**

All date and time values **MUST** use ISO-8601 format.

### Formats

| Type | Format | Example |
|------|--------|---------|
| Date only | `YYYY-MM-DD` | `2026-02-08` |
| Date-time (UTC) | `YYYY-MM-DDTHH:mm:ssZ` | `2026-02-08T12:30:00Z` |
| Date-time (offset) | `YYYY-MM-DDTHH:mm:ss±HH:mm` | `2026-02-08T15:30:00+03:00` |
| Year-month only | `YYYY-MM` | `2027-12` |

### Usage by Field

| Field | Format | Example |
|-------|--------|---------|
| `timestamp` | Date-time (UTC) | `2026-02-08T12:30:00Z` |
| `orderDate` | Date only | `2026-02-08` |
| `saleDate` | Date only | `2026-02-08` |
| `expiration` (credit card) | Year-month | `2027-12` |
| `createdAt` | Date-time (UTC) | `2026-02-08T12:30:00Z` |
| `updatedAt` | Date-time (UTC) | `2026-02-08T12:30:00Z` |

### Rules

1. All timestamps from server are in **UTC** (suffix `Z`)
2. Frontend converts UTC to local time for display
3. Frontend sends dates/times in UTC or with explicit offset
4. Year values (like `birthYear`, movie `year`) are integers, not date strings

---

## Response Envelope

All API responses **MUST** use this standard envelope structure.

### Success Response

```json
{
  "success": true,
  "data": {
    // Response payload here
  }
}
```

### Error Response

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable message",
    "details": {}
  },
  "timestamp": "2026-02-08T12:30:00Z"
}
```

### Rules

1. `success` is always present and is a boolean
2. On success: `data` contains the response payload
3. On error: `error` contains error details, `timestamp` indicates when error occurred
4. Never mix `data` and `error` in the same response

---

## Error Response Format

### Standard Error Object

```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Request validation failed",
    "details": {
      "field": "email",
      "reason": "Invalid email format"
    }
  },
  "timestamp": "2026-02-08T12:30:00Z"
}
```

### Error Codes

| Code | HTTP Status | When to Use |
|------|-------------|-------------|
| `VALIDATION_ERROR` | 400 | Request body or query params invalid |
| `INVALID_CREDENTIALS` | 401 | Login failed (wrong email/password) |
| `UNAUTHORIZED` | 401 | No valid session, authentication required |
| `FORBIDDEN` | 403 | Authenticated but not authorized |
| `NOT_FOUND` | 404 | Requested resource doesn't exist |
| `CONFLICT` | 409 | Resource conflict (duplicate entry) |
| `PAYMENT_FAILED` | 402 | Payment validation or processing failed |
| `CART_EMPTY` | 400 | Checkout attempted with empty cart |
| `INTERNAL_ERROR` | 500 | Unexpected server error |

### Validation Error Details

For validation errors, `details` should contain field-level information:

```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Request validation failed",
    "details": {
      "fields": [
        {
          "field": "email",
          "reason": "Email is required"
        },
        {
          "field": "password",
          "reason": "Password must be at least 8 characters"
        }
      ]
    }
  },
  "timestamp": "2026-02-08T12:30:00Z"
}
```

---

## Data Type Mappings

### Database to JSON Type Mapping

| Database Type | Java Type | JSON Type | Example |
|---------------|-----------|-----------|---------|
| `VARCHAR` | `String` | `string` | `"tt0000001"` |
| `SMALLINT` | `Short` / `Integer` | `number` | `1994` |
| `INTEGER` | `Integer` | `number` | `2500000` |
| `BIGINT` / `BIGSERIAL` | `Long` | `number` | `12345` |
| `DECIMAL(3,1)` | `BigDecimal` | `number` | `9.3` |
| `BOOLEAN` | `Boolean` | `boolean` | `true` |
| `DATE` | `LocalDate` | `string` | `"2026-02-08"` |
| `TIMESTAMP` | `Instant` | `string` | `"2026-02-08T12:30:00Z"` |

### Null Handling

1. **Optional fields**: Include in response with `null` value
2. **Required fields**: Must never be `null`
3. **Empty collections**: Return empty array `[]`, not `null`
4. **Missing optional request fields**: Treat as `null`

---

## Entity Schemas

### Movie (List Item)

```json
{
  "id": "string",
  "title": "string",
  "year": "number | null",
  "director": "string | null",
  "rating": "number | null",
  "numVotes": "number | null",
  "genres": ["string"],
  "stars": [
    {
      "id": "string",
      "name": "string"
    }
  ]
}
```

### Movie (Detail)

```json
{
  "id": "string",
  "title": "string",
  "year": "number | null",
  "director": "string | null",
  "rating": "number | null",
  "numVotes": "number | null",
  "genres": [
    {
      "id": "number",
      "name": "string"
    }
  ],
  "stars": [
    {
      "id": "string",
      "name": "string",
      "birthYear": "number | null"
    }
  ]
}
```

### Star (List Item)

```json
{
  "id": "string",
  "name": "string",
  "birthYear": "number | null",
  "movieCount": "number"
}
```

### Star (Detail)

```json
{
  "id": "string",
  "name": "string",
  "birthYear": "number | null",
  "movies": [
    {
      "id": "string",
      "title": "string",
      "year": "number | null",
      "director": "string | null"
    }
  ]
}
```

### Genre

```json
{
  "id": "number",
  "name": "string"
}
```

### Cart Item

```json
{
  "movieId": "string",
  "title": "string",
  "year": "number | null",
  "quantity": "number",
  "price": "number",
  "subtotal": "number"
}
```

### Cart

```json
{
  "items": ["CartItem[]"],
  "itemCount": "number",
  "totalPrice": "number"
}
```

### Order (List Item)

```json
{
  "orderId": "number",
  "orderDate": "string (date)",
  "itemCount": "number",
  "totalPrice": "number"
}
```

### Order (Detail)

```json
{
  "orderId": "number",
  "customerId": "number",
  "items": [
    {
      "saleId": "number",
      "movieId": "string",
      "title": "string",
      "quantity": "number",
      "price": "number"
    }
  ],
  "totalPrice": "number",
  "orderDate": "string (date)"
}
```

### Customer Session

```json
{
  "customerId": "number",
  "email": "string",
  "firstName": "string",
  "lastName": "string"
}
```

---

## HTTP Status Code Usage

| Status | Meaning | When to Use |
|--------|---------|-------------|
| `200 OK` | Success | Successful GET, PUT, DELETE, or POST that returns data |
| `201 Created` | Resource created | Not used (we use 200 for consistency) |
| `400 Bad Request` | Client error | Validation failed, malformed request |
| `401 Unauthorized` | Not authenticated | Missing or invalid session |
| `402 Payment Required` | Payment error | Credit card validation failed |
| `403 Forbidden` | Not authorized | Authenticated but access denied |
| `404 Not Found` | Resource not found | Entity doesn't exist |
| `409 Conflict` | Conflict | Duplicate resource (e.g., email exists) |
| `500 Internal Server Error` | Server error | Unexpected error |
| `501 Not Implemented` | Not implemented | Endpoint skeleton (dev only) |
| `503 Service Unavailable` | Service down | Database or service unavailable |

---

## Content-Type Headers

### Requests

```
Content-Type: application/json
```

### Responses

```
Content-Type: application/json; charset=utf-8
```

---

## CORS Configuration

For development, backend should allow:

- **Origins**: `http://localhost:3000`, `http://localhost:5173`
- **Methods**: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`
- **Headers**: `Content-Type`, `Authorization`
- **Credentials**: `true` (for session cookies)
