package com.finances.service;

import com.finances.dto.request.UserRequest;
import com.finances.dto.response.UserResponse;
import com.finances.dto.response.PagedResponse;
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

    public PagedResponse<UserResponse> getAllUsers(int page, int pageSize) {
        try {
            String endpoint = String.format("/api/users?page=%d&pageSize=%d", page, pageSize);
            return apiClient.get(endpoint, PagedResponse.class);
        } catch (IOException e) {
            System.err.println("Get users failed: " + e.getMessage());
            return null;
        }
    }

    public UserResponse getUserById(Integer id) {
        try {
            return apiClient.get("/api/users/" + id, UserResponse.class);
        } catch (IOException e) {
            System.err.println("Get user failed: " + e.getMessage());
            return null;
        }
    }

    public UserResponse updateUser(Integer id, String username, String password, String role) {
        try {
            UserRequest request = new UserRequest(username, password, role);
            return apiClient.put("/api/users/" + id, request, UserResponse.class);
        } catch (IOException e) {
            System.err.println("Update user failed: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteUser(Integer id) {
        try {
            apiClient.delete("/api/users/" + id);
            return true;
        } catch (IOException e) {
            System.err.println("Delete user failed: " + e.getMessage());
            return false;
        }
    }

    public boolean changePassword(Integer userId, String newPassword) {
        try {
            UserRequest request = new UserRequest(null, newPassword, null);
            apiClient.put("/api/users/" + userId, request, UserResponse.class);
            return true;
        } catch (IOException e) {
            System.err.println("Change password failed: " + e.getMessage());
            return false;
        }
    }
}
