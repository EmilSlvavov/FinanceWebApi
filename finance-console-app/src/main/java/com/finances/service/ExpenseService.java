package com.finances.service;

import com.finances.dto.request.ExpenseRequest;
import com.finances.dto.response.ExpenseResponse;
import com.finances.dto.response.PagedResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ExpenseService {
    private final ApiClient apiClient;

    public ExpenseService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ExpenseResponse createExpense(Integer expenseCategoryId, Double amount, LocalDateTime expenseDate, 
                                         Boolean isRecurring, String description) {
        try {
            ExpenseRequest request = new ExpenseRequest(expenseCategoryId, amount, expenseDate, isRecurring, description);
            return apiClient.post("/api/expenses", request, ExpenseResponse.class);
        } catch (IOException e) {
            System.err.println("Create expense failed: " + e.getMessage());
            return null;
        }
    }

    public PagedResponse<ExpenseResponse> getAllExpenses(int page, int pageSize) {
        try {
            String endpoint = String.format("/api/expenses?page=%d&pageSize=%d&sortBy=expenseDate&sortDirection=DESC", 
                    page, pageSize);
            return apiClient.get(endpoint, PagedResponse.class);
        } catch (IOException e) {
            System.err.println("Get expenses failed: " + e.getMessage());
            return null;
        }
    }

    public PagedResponse<ExpenseResponse> searchExpenses(String categoryType, Double minAmount, Double maxAmount, int page, int pageSize) {
        try {
            StringBuilder endpoint = new StringBuilder("/api/expenses?page=" + page + "&pageSize=" + pageSize);
            
            if (categoryType != null && !categoryType.isEmpty()) {
                endpoint.append("&categoryType=").append(categoryType);
            }
            if (minAmount != null) {
                endpoint.append("&minAmount=").append(minAmount);
            }
            if (maxAmount != null) {
                endpoint.append("&maxAmount=").append(maxAmount);
            }
            
            return apiClient.get(endpoint.toString(), PagedResponse.class);
        } catch (IOException e) {
            System.err.println("Search expenses failed: " + e.getMessage());
            return null;
        }
    }

    public ExpenseResponse getExpenseById(Integer id) {
        try {
            return apiClient.get("/api/expenses/" + id, ExpenseResponse.class);
        } catch (IOException e) {
            System.err.println("Get expense failed: " + e.getMessage());
            return null;
        }
    }

    public ExpenseResponse updateExpense(Integer id, Integer expenseCategoryId, Double amount, LocalDateTime expenseDate, 
                                        Boolean isRecurring, String description) {
        try {
            ExpenseRequest request = new ExpenseRequest(expenseCategoryId, amount, expenseDate, isRecurring, description);
            return apiClient.put("/api/expenses/" + id, request, ExpenseResponse.class);
        } catch (IOException e) {
            System.err.println("Update expense failed: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteExpense(Integer id) {
        try {
            apiClient.delete("/api/expenses/" + id);
            return true;
        } catch (IOException e) {
            System.err.println("Delete expense failed: " + e.getMessage());
            return false;
        }
    }
}
