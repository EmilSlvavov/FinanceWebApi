package com.finances;

import com.finances.service.*;
import com.finances.page.*;
import com.finances.util.ConsoleUI;

public class FinanceConsoleApp {
    private static final String API_BASE_URL = "http://localhost:8080";
    private static final int PAGE_SIZE = 5;

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(API_BASE_URL);
        AuthService authService = new AuthService(apiClient);
        
        try {
            if (authService.isLoggedIn()) {
                showMainMenu(apiClient, authService);
            } else {
                showAuthMenu(apiClient, authService);
            }
        } catch (Exception e) {
            ConsoleUI.printError("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showAuthMenu(ApiClient apiClient, AuthService authService) {
        while (!authService.isLoggedIn()) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("FINANCE MANAGER - AUTHENTICATION");
            
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            
            String choice = ConsoleUI.readInput("\nSelect an option: ");

            switch (choice) {
                case "1":
                    LoginPage.show(authService);
                    break;
                case "2":
                    UserService userService = new UserService(apiClient);
                    RegisterPage.show(userService);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }

        showMainMenu(apiClient, authService);
    }

    private static void showMainMenu(ApiClient apiClient, AuthService authService) {
        UserService userService = new UserService(apiClient);
        BudgetService budgetService = new BudgetService(apiClient);
        ExpenseService expenseService = new ExpenseService(apiClient);
        ExpenseCategoryService categoryService = new ExpenseCategoryService(apiClient);

        while (authService.isLoggedIn()) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("FINANCE MANAGER - DASHBOARD");
            
            System.out.println("\n1. Budgets");
            System.out.println("2. Expenses");
            System.out.println("3. Expense Categories");
            System.out.println("4. Profile");
            System.out.println("5. Admin Panel");
            System.out.println("6. Logout");
            
            String choice = ConsoleUI.readInput("\nSelect an option: ");

            switch (choice) {
                case "1":
                    showBudgetMenu(budgetService);
                    break;
                case "2":
                    showExpenseMenu(expenseService, categoryService);
                    break;
                case "3":
                    showCategoryMenu(categoryService);
                    break;
                case "4":
                    ProfilePage.show();
                    break;
                case "5":
                    showAdminMenu(userService);
                    break;
                case "6":
                    authService.logout();
                    break;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }

        showAuthMenu(apiClient, authService);
    }

    private static void showBudgetMenu(BudgetService budgetService) {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("BUDGET MANAGEMENT");
            
            System.out.println("\n1. View All Budgets");
            System.out.println("2. Create New Budget");
            System.out.println("3. Back to Main Menu");
            
            String choice = ConsoleUI.readInput("\nSelect an option: ");

            switch (choice) {
                case "1":
                    BudgetListPage.show(budgetService);
                    break;
                case "2":
                    CreateBudgetPage.show(budgetService);
                    break;
                case "3":
                    return;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }
    }

    private static void showExpenseMenu(ExpenseService expenseService, ExpenseCategoryService categoryService) {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("EXPENSE MANAGEMENT");
            
            System.out.println("\n1. View All Expenses");
            System.out.println("2. Create New Expense");
            System.out.println("3. Back to Main Menu");
            
            String choice = ConsoleUI.readInput("\nSelect an option: ");

            switch (choice) {
                case "1":
                    ExpenseListPage.show(expenseService);
                    break;
                case "2":
                    CreateExpensePage.show(expenseService, categoryService);
                    break;
                case "3":
                    return;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }
    }

    private static void showCategoryMenu(ExpenseCategoryService categoryService) {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("EXPENSE CATEGORY MANAGEMENT");
            
            System.out.println("\n1. View All Categories");
            System.out.println("2. Create New Category");
            System.out.println("3. Back to Main Menu");
            
            String choice = ConsoleUI.readInput("\nSelect an option: ");

            switch (choice) {
                case "1":
                    CategoryListPage.show(categoryService);
                    break;
                case "2":
                    CreateCategoryPage.show(categoryService);
                    break;
                case "3":
                    return;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }
    }

    private static void showAdminMenu(UserService userService) {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("ADMIN PANEL");
            
            System.out.println("\n1. Create New User");
            System.out.println("2. Back to Main Menu");
            
            String choice = ConsoleUI.readInput("\nSelect an option: ");

            switch (choice) {
                case "1":
                    CreateUserPage.show(userService);
                    break;
                case "2":
                    return;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }
    }
}
