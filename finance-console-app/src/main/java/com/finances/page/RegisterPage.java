package com.finances.page;

import com.finances.dto.response.UserResponse;
import com.finances.service.UserService;
import com.finances.util.ConsoleUI;

public class RegisterPage {
    public static void show(UserService userService) {
        ConsoleUI.clearScreen();
        ConsoleUI.printHeader("REGISTER NEW USER");

        String username = ConsoleUI.readInput("\nEnter username: ");
        String password = ConsoleUI.readPassword("Enter password (min 8 characters): ");
        String confirmPassword = ConsoleUI.readPassword("Confirm password: ");

        if (!password.equals(confirmPassword)) {
            ConsoleUI.printError("Passwords do not match!");
            ConsoleUI.pause();
            return;
        }

        if (password.length() < 8) {
            ConsoleUI.printError("Password must be at least 8 characters long!");
            ConsoleUI.pause();
            return;
        }

        System.out.println("\nSelect role:");
        System.out.println("1. USER");
        System.out.println("2. ADMIN");
        String roleChoice = ConsoleUI.readInput("Enter choice (1 or 2): ");

        String role = roleChoice.equals("2") ? "ADMIN" : "USER";

        UserResponse user = userService.createUser(username, password, role);
        if (user != null) {
            ConsoleUI.printSuccess("User registered successfully! Please login with your credentials.");
            ConsoleUI.pause();
        } else {
            ConsoleUI.printError("Registration failed. Please try again.");
            ConsoleUI.pause();
        }
    }
}
