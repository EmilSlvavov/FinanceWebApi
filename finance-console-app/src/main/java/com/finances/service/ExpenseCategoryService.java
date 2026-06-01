package com.finances.service;

import com.finances.dto.request.ExpenseCategoryRequest;
import com.finances.dto.response.ExpenseCategoryResponse;
import com.finances.dto.response.PagedResponse;
import java.io.IOException;

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
            String endpoint = String.format("/api/expense-categories?filter.page=%d&filter.pageSize=%d&filter.sortBy=createdAt&filter.sortDirection=DESC", 
                    page, pageSize);
            return apiClient.get(endpoint, PagedResponse.class);
        } catch (IOException e) {
            System.err.println("Get categories failed: " + e.getMessage());
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
