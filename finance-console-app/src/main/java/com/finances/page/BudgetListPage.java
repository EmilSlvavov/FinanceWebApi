package com.finances.page;

import com.finances.service.BudgetService;
import com.finances.dto.response.BudgetResponse;
import com.finances.dto.response.PagedResponse;
import com.finances.util.ConsoleUI;
import java.util.List;

public class BudgetListPage {
    private static final int PAGE_SIZE = 5;

    public static void show(BudgetService budgetService) {
        int currentPage = 0;

        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("YOUR BUDGETS");

            PagedResponse<BudgetResponse> response = budgetService.getAllBudgets(currentPage, PAGE_SIZE);
            
            if (response == null || response.getContent() == null || response.getContent().isEmpty()) {
                ConsoleUI.printInfo("No budgets found.");
                System.out.println("\nPress Enter to go back...");
                ConsoleUI.readInput("");
                return;
            }

            List<BudgetResponse> budgets = response.getContent();
            
            System.out.println("\n" + String.format("%-4s %-20s %-12s %-10s %-15s", 
                    "ID", "Name", "Value", "Currency", "Balance"));
            ConsoleUI.printLine();

            for (BudgetResponse budget : budgets) {
                Double balance = budget.getRemainingBalance() != null ? budget.getRemainingBalance() : 
                                 budget.getValue() - (budget.getTotalSpent() != null ? budget.getTotalSpent() : 0);
                System.out.println(String.format("%-4d %-20s %-12.2f %-10s %-15.2f", 
                        budget.getId(), 
                        truncate(budget.getName(), 20),
                        budget.getValue(),
                        budget.getCurrency(),
                        balance));
            }

            ConsoleUI.printLine();
            int totalPages = response.getTotalPages() != null ? response.getTotalPages() : 1;
            long totalElements = response.getTotalElements() != null ? response.getTotalElements() : 0;
            
            System.out.println("\nPage " + (currentPage + 1) + " of " + totalPages);
            System.out.println("Total budgets: " + totalElements);

            System.out.println("\n1. Next Page");
            System.out.println("2. Previous Page");
            System.out.println("3. Edit Budget");
            System.out.println("4. Delete Budget");
            System.out.println("5. Back to Menu");
            String choice = ConsoleUI.readInput("\nSelect option: ");

            switch (choice) {
                case "1":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        ConsoleUI.printInfo("You are on the last page.");
                        ConsoleUI.pause();
                    }
                    break;
                case "2":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        ConsoleUI.printInfo("You are on the first page.");
                        ConsoleUI.pause();
                    }
                    break;
                case "3":
                    String budgetIdStr = ConsoleUI.readInput("Enter budget ID to edit: ");
                    try {
                        Integer budgetId = Integer.parseInt(budgetIdStr);
                        EditBudgetPage.show(budgetService, budgetId);
                    } catch (NumberFormatException e) {
                        ConsoleUI.printError("Invalid ID format.");
                        ConsoleUI.pause();
                    }
                    break;
                case "4":
                    String deleteIdStr = ConsoleUI.readInput("Enter budget ID to delete: ");
                    try {
                        Integer budgetId = Integer.parseInt(deleteIdStr);
                        if (budgetService.deleteBudget(budgetId)) {
                            ConsoleUI.printSuccess("Budget deleted successfully!");
                        } else {
                            ConsoleUI.printError("Failed to delete budget.");
                        }
                        ConsoleUI.pause();
                    } catch (NumberFormatException e) {
                        ConsoleUI.printError("Invalid ID format.");
                        ConsoleUI.pause();
                    }
                    break;
                case "5":
                    return;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }
    }

    private static String truncate(String str, int length) {
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}
