package com.finances.service;

import com.finances.dto.request.BudgetRequest;
import com.finances.dto.response.BudgetResponse;
import com.finances.dto.response.PagedResponse;
import java.io.IOException;

public class BudgetService {
    private final ApiClient apiClient;

    public BudgetService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public BudgetResponse createBudget(String name, Double value, String currency, Boolean isRecurring) {
        try {
            BudgetRequest request = new BudgetRequest(name, value, currency, isRecurring);
            return apiClient.post("/api/budgets", request, BudgetResponse.class);
        } catch (IOException e) {
            System.err.println("Create budget failed: " + e.getMessage());
            return null;
        }
    }

    public PagedResponse<BudgetResponse> getAllBudgets(int page, int pageSize) {
        try {
            String endpoint = String.format("/api/budgets?page=%d&pageSize=%d&sortBy=createdAt&sortDirection=DESC", 
                    page, pageSize);
            return apiClient.get(endpoint, PagedResponse.class);
        } catch (IOException e) {
            System.err.println("Get budgets failed: " + e.getMessage());
            return null;
        }
    }

    public BudgetResponse getBudgetById(Integer id) {
        try {
            return apiClient.get("/api/budgets/" + id, BudgetResponse.class);
        } catch (IOException e) {
            System.err.println("Get budget failed: " + e.getMessage());
            return null;
        }
    }

    public BudgetResponse updateBudget(Integer id, String name, Double value, String currency, Boolean isRecurring) {
        try {
            BudgetRequest request = new BudgetRequest(name, value, currency, isRecurring);
            return apiClient.put("/api/budgets/" + id, request, BudgetResponse.class);
        } catch (IOException e) {
            System.err.println("Update budget failed: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteBudget(Integer id) {
        try {
            apiClient.delete("/api/budgets/" + id);
            return true;
        } catch (IOException e) {
            System.err.println("Delete budget failed: " + e.getMessage());
            return false;
        }
    }
}
