package com.finances.service;

import com.finances.dto.request.ExpenseCategoryRequest;
import com.finances.dto.response.ExpenseCategoryResponse;
import com.finances.dto.response.PagedResponse;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;

public class ExpenseCategoryService {
    private final ApiClient apiClient;

    public ExpenseCategoryService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ExpenseCategoryResponse createCategory(String expenseType, Double categoryBudget, Integer userId) {
        try {
            ExpenseCategoryRequest request = new ExpenseCategoryRequest(expenseType, categoryBudget, userId);
            return apiClient.post("/api/expense-categories", request, ExpenseCategoryResponse.class);
        } catch (IOException e) {
            System.err.println("Create category failed: " + e.getMessage());
            return null;
        }
    }

    public PagedResponse<ExpenseCategoryResponse> getAllCategories(int page, int pageSize) {
        try {
            // Try simpler endpoint first without complex filter parameters
            String endpoint = String.format("/api/expense-categories?page=%d&size=%d", page, pageSize);
            Type type = new TypeToken<PagedResponse<ExpenseCategoryResponse>>(){}.getType();
            return apiClient.getWithType(endpoint, type);
        } catch (IOException e) {
            System.err.println("Get categories failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ExpenseCategoryResponse getCategoryById(Integer id) {
        try {
            return apiClient.get("/api/expense-categories/" + id, ExpenseCategoryResponse.class);
        } catch (IOException e) {
            System.err.println("Get category failed: " + e.getMessage());
            return null;
        }
    }
}
