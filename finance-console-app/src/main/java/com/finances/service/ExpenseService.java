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
            String endpoint = String.format("/api/expenses?filter.page=%d&filter.pageSize=%d&filter.sortBy=expenseDate&filter.sortDirection=DESC", 
                    page, pageSize);
            return apiClient.get(endpoint, PagedResponse.class);
        } catch (IOException e) {
            System.err.println("Get expenses failed: " + e.getMessage());
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
}
