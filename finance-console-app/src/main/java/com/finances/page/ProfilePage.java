package com.finances.page;

import com.finances.util.ConsoleUI;

public class ProfilePage {
    public static void show() {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("USER PROFILE");

        System.out.println("\nProfile information:");
        System.out.println("- Username: [Will be retrieved from current user]");
        System.out.println("- Role: [Will be retrieved from current user]");
        System.out.println("- Account Status: Active");
        System.out.println("- Created: [Will be retrieved from current user]");

        System.out.println("\n1. Change Password");
        System.out.println("2. View Statistics");
        System.out.println("3. Back to Menu");

        String choice = ConsoleUI.readInput("\nSelect option: ");

        switch (choice) {
            case "1":
                ConsoleUI.printInfo("Password change feature coming soon.");
                ConsoleUI.pause();
                break;
            case "2":
                ConsoleUI.printInfo("Statistics feature coming soon.");
                ConsoleUI.pause();
                break;
            case "3":
                return;
            default:
                ConsoleUI.printError("Invalid option.");
                ConsoleUI.pause();
        }
    }
}
