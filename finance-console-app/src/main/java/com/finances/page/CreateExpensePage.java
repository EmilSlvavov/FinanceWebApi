package com.finances.page;

import com.finances.service.ExpenseService;
import com.finances.service.ExpenseCategoryService;
import com.finances.dto.response.ExpenseCategoryResponse;
import com.finances.dto.response.PagedResponse;
import com.finances.util.ConsoleUI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateExpensePage {
    public static void show(ExpenseService expenseService, ExpenseCategoryService categoryService) {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("CREATE NEW EXPENSE");

        PagedResponse<ExpenseCategoryResponse> categoryResponse = categoryService.getAllCategories(0, 100);
        
        if (categoryResponse == null || categoryResponse.getContent() == null || categoryResponse.getContent().isEmpty()) {
            ConsoleUI.printError("No expense categories available. Please create a category first.");
            ConsoleUI.pause();
            return;
        }

        System.out.println("\nSelect expense category:");
        var categories = categoryResponse.getContent();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getExpenseType() + 
                             " (Budget: " + categories.get(i).getCategoryBudget() + ")");
        }

        String categoryChoice = ConsoleUI.readInput("Enter choice: ");
        Integer expenseCategoryId;
        try {
            expenseCategoryId = categories.get(Integer.parseInt(categoryChoice) - 1).getId();
        } catch (Exception e) {
            ConsoleUI.printError("Invalid category choice.");
            ConsoleUI.pause();
            return;
        }

        String amountInput = ConsoleUI.readInput("\nEnter expense amount: ");
        Double amount;
        try {
            amount = Double.parseDouble(amountInput);
        } catch (NumberFormatException e) {
            ConsoleUI.printError("Invalid amount. Please enter a valid number.");
            ConsoleUI.pause();
            return;
        }

        String dateInput = ConsoleUI.readInput("Enter expense date (yyyy-MM-dd HH:mm:ss) [default: now]: ");
        LocalDateTime expenseDate;
        try {
            if (dateInput.isEmpty()) {
                expenseDate = LocalDateTime.now();
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                expenseDate = LocalDateTime.parse(dateInput, formatter);
            }
        } catch (Exception e) {
            ConsoleUI.printError("Invalid date format. Using current time.");
            expenseDate = LocalDateTime.now();
        }

        System.out.println("\nIs this a recurring expense?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        String recurringChoice = ConsoleUI.readInput("Enter choice (1 or 2): ");
        Boolean isRecurring = recurringChoice.equals("1");

        String description = ConsoleUI.readInput("Enter description (optional): ");
        if (description.isEmpty()) {
            description = null;
        }

        var expense = expenseService.createExpense(expenseCategoryId, amount, expenseDate, isRecurring, description);
        if (expense != null) {
            ConsoleUI.printSuccess("Expense created successfully!");
            System.out.println("Expense ID: " + expense.getId());
            System.out.println("Category: " + expense.getExpenseCategoryType());
            System.out.println("Amount: " + expense.getAmount());
            System.out.println("Date: " + expense.getExpenseDate());
            ConsoleUI.pause();
        } else {
            ConsoleUI.printError("Failed to create expense. Please try again.");
            ConsoleUI.pause();
        }
    }
}
