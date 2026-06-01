package com.finances.page;

import com.finances.service.ExpenseCategoryService;
import com.finances.dto.response.ExpenseCategoryResponse;
import com.finances.util.ConsoleUI;

public class CreateCategoryPage {
    public static void show(ExpenseCategoryService categoryService) {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("CREATE NEW EXPENSE CATEGORY");

        String[] expenseTypes = {
            "HOUSING", "TRANSPORTATION", "FOOD", "HEALTHCARE", "DEBT", 
            "ENTERTAINMENT", "CLOTIHING_AND_PERSONAL_ITEMS", "TRAVEL", "PETS", "SAVINGS"
        };

        System.out.println("\nSelect expense type:");
        for (int i = 0; i < expenseTypes.length; i++) {
            System.out.println((i + 1) + ". " + expenseTypes[i]);
        }
        
        String typeChoice = ConsoleUI.readInput("Enter choice (1-10): ");
        String expenseType;
        try {
            expenseType = expenseTypes[Integer.parseInt(typeChoice) - 1];
        } catch (Exception e) {
            ConsoleUI.printError("Invalid type choice.");
            ConsoleUI.pause();
            return;
        }

        String budgetInput = ConsoleUI.readInput("Enter category budget: ");
        Double categoryBudget;
        try {
            categoryBudget = Double.parseDouble(budgetInput);
        } catch (NumberFormatException e) {
            ConsoleUI.printError("Invalid amount. Please enter a valid number.");
            ConsoleUI.pause();
            return;
        }

        String userIdInput = ConsoleUI.readInput("Enter user ID (your ID): ");
        Integer userId;
        try {
            userId = Integer.parseInt(userIdInput);
        } catch (NumberFormatException e) {
            ConsoleUI.printError("Invalid user ID.");
            ConsoleUI.pause();
            return;
        }

        ExpenseCategoryResponse category = categoryService.createCategory(expenseType, categoryBudget, userId);
        if (category != null) {
            ConsoleUI.printSuccess("Expense category created successfully!");
            System.out.println("Category ID: " + category.getId());
            System.out.println("Type: " + category.getExpenseType());
            System.out.println("Budget: " + category.getCategoryBudget());
            ConsoleUI.pause();
        } else {
            ConsoleUI.printError("Failed to create category. Please try again.");
            ConsoleUI.pause();
        }
    }
}
