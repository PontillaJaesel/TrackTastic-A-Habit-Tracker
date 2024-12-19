package entrance;

import java.util.Scanner;
import dao.UserDAO;
import utils.Design;

public class Register {
    public UserDAO userDAO;
    public Scanner scanner;

    public Register() {
        userDAO = new UserDAO();
        this.userDAO = new UserDAO();
        scanner = new Scanner(System.in);
        this.scanner = new Scanner(System.in);
    }
    public void register() {
        while (true) {
            Design.clearScreen();
            Design.sub(1);
            System.out.print("Enter new username (at least 6 characters): \t");
            String username = scanner.nextLine();


            if (username.isEmpty()) {
                System.out.println("Username is empty. Returning to home page...");
                Design.delay(1500);
                return;
            }

            if (username.length() < 6) {
                System.out.println("\nUsername must be at least 6 characters long. Please try again.");
                Design.delay(1500);
                continue;
            }

            if (userDAO.usernameExists(username)) {
                System.out.println("\nUsername already exists. Please try again.");
                Design.delay(1500);
                continue;
            }

            System.out.print("Enter your password (at least 8 characters): \t");
            String password = scanner.nextLine();


            if (password.isEmpty()) {
                System.out.println("Password is empty. Returning to home page...");
                Design.delay(1500);
                return;
            }

            if (password.length() < 8) {
                System.out.println("\nPassword must be at least 8 characters long. Please try again.");
                Design.delay(1500);
                continue;
            }


            System.out.print("Enter your bio: \t");
            String bio = scanner.nextLine();


            userDAO.addUser(new User(username, password, bio));

            System.out.println("\nRegistration successful. Please log-in!");
            Design.delay(1500);
            break;
        }
    }

    public boolean login() {
        while (true) {
            Design.clearScreen();
            Design.sub(2);
            System.out.print("Enter username: \t");
            String username = scanner.nextLine();


            if (username.isEmpty()) {
                System.out.println("Username is empty. Returning to home page...");
                Design.delay(1500);
                return false;
            }

            if (!userDAO.usernameExists(username)) {
                System.out.println("\nUsername does not exist. Try again...");
                Design.delay(1500);
                return false;
            }

            System.out.print("Enter your password: \t");
            String enteredPassword = scanner.nextLine();


            if (enteredPassword.isEmpty()) {
                System.out.println("Password is empty. Returning to home page...");
                Design.delay(1500);
                return false;
            }

            int userId = userDAO.login(username, enteredPassword);
            if (userId != -1) {
                // Get the user bio after login
                String[] userCredentials = userDAO.getCredentials(username);
                User userProfile = new User(username, enteredPassword, userCredentials[1]); // Passing the bio as well
                UserHP userHP = new UserHP(userProfile.getUsername(), userProfile.getPassword(), userProfile.getBio());
                userHP.userMenu(userProfile); // Navigate to the user menu
                return true;
            }
            System.out.println("Invalid username or password.");
            return false;
        }
    }
}
