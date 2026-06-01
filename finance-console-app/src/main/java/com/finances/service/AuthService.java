package com.finances.service;

import com.finances.dto.request.AuthRequest;
import com.finances.dto.response.AuthResponse;
import java.io.IOException;

public class AuthService {
    private final ApiClient apiClient;

    public AuthService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public boolean login(String username, String password) {
        try {
            AuthRequest request = new AuthRequest(username, password);
            AuthResponse response = apiClient.post("/api/auth/login", request, AuthResponse.class);
            
            if (response != null && response.getToken() != null) {
                apiClient.setAuthToken(response.getToken());
                return true;
            }
            return false;
        } catch (IOException e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        apiClient.clearAuthToken();
    }

    public boolean isLoggedIn() {
        return apiClient.getAuthToken() != null && !apiClient.getAuthToken().isEmpty();
    }
}
