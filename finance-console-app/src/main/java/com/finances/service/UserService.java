package com.finances.service;

import com.finances.dto.request.UserRequest;
import com.finances.dto.response.UserResponse;
import java.io.IOException;

public class UserService {
    private final ApiClient apiClient;

    public UserService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserResponse createUser(String username, String password, String role) {
        try {
            UserRequest request = new UserRequest(username, password, role);
            return apiClient.post("/api/users", request, UserResponse.class);
        } catch (IOException e) {
            System.err.println("Create user failed: " + e.getMessage());
            return null;
        }
    }

    public UserResponse getCurrentUser() {
        return null;
    }

    public UserResponse getUserById(Integer id) {
        try {
            return apiClient.get("/api/users/" + id, UserResponse.class);
        } catch (IOException e) {
            System.err.println("Get user failed: " + e.getMessage());
            return null;
        }
    }
}
