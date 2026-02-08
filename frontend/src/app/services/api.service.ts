import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * API Service for communicating with the Filmer backend.
 * All HTTP requests to the backend are routed through this service.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {
  // Base URL for the backend API
  // In production, this should be moved to an environment configuration
  private readonly API_BASE_URL = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  /**
   * Test database connectivity by calling the /api/v1/health/db endpoint.
   * @returns Observable with the health check response
   */
  testDatabaseConnectivity(): Observable<any> {
    return this.http.get(`${this.API_BASE_URL}/health/db`);
  }

  /**
   * Get basic health status of the API.
   * @returns Observable with the health status response
   */
  getHealth(): Observable<any> {
    return this.http.get(`${this.API_BASE_URL}/health`);
  }
}
