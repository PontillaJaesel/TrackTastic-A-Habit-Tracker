package habit;

import dao.HabitDAO;
import utils.Design;

import java.util.List;

public class Daily extends Habit {
    private HabitDAO habitDAO;

    public Daily() {
        this.habitDAO = new HabitDAO(); // Initialize the DAO
    }

    public void createHabit(String tag) {
        Design.sub(3);
        System.out.println("Today is: " + today.format(day));

        System.out.print("Enter the name of the Habit: \t\t");
        this.habitName = scanner.nextLine();

        if (habitName.isEmpty()) {
            System.out.print("Habit name cannot be empty!");
            Design.delay(1500);
            return;
        }

        System.out.print("Enter a description for the habit: \t");
        this.habitDescription = scanner.nextLine();

        System.out.print("How many times you want to this habit: \t");
        this.habitGoal = scanner.nextLine();

        this.status = "0";

        if (isValidInteger(habitGoal)) {
            habitDAO.addHabit(habitName, habitDescription, status, habitGoal, "Daily");
            System.out.println("Successfully created habit \"" + habitName + "\" as a daily habit!");
            Design.delay(1500);
        } else {
            System.out.println("Failed adding new habit.");
            Design.delay(1500);
            return;
        }
    }

    public boolean printHabits(int indicator, int type) {
        Design.sub(7);
        System.out.println("Habits created: ");

        List<String> habitNames = habitDAO.getAllHabits();
        if (habitNames.isEmpty()) {
            System.out.println("No habits found.");
            return false;
        }

        int n = 1;
        for (String habitName : habitNames) {
            String[] habitDetails = habitDAO.getHabitDetails(habitName); // You'll need to implement this method in DAO

            String description = habitDetails[0];
            String status = habitDetails[1];
            String goal = habitDetails[2];
            String frequency = habitDetails[3];

            if (type == 0) {
                if (status.equals("Completed")) {
                    continue;
                }
            }

            System.out.println(n + ". Name: \t" + habitName);

            if (indicator == 1) {
                System.out.println("   Description: " + description);
                System.out.println("   Status: \t" + status);
                System.out.println("   Goal: \t" + goal);
                System.out.println("   Frequency: \t" + frequency);
            }
            n++;
        }
        return true;
    }

    public void updateStatus(int indicator) {
        if (printHabits(1, 0)) {
            System.out.print("Enter name you want to update: ");

            String habitToEdit = scanner.nextLine();

            if (habitDAO.habitExists(habitToEdit)) { // You'll need to implement this method in DAO
                String[] status = habitDAO.getHabitDetails(habitToEdit);

                int statusNum = Integer.parseInt(status[1]);
                int goal = Integer.parseInt(status[2]);

                if (statusNum == goal) {
                    System.out.println("The habit has already reached the goal.");
                    return;
                }

                System.out.println("\nSelected Habit: " + habitToEdit);
                System.out.println("Goal: \t\t" + goal + "x a day");

                if (indicator == 0) {
                    habitDAO.updateStatus(habitToEdit, "0");
                    System.out.println("Status reset to 0.");
                } else {
                    if (statusNum < goal) {
                        statusNum++;
                        habitDAO.updateStatus(habitToEdit, Integer.toString(statusNum));
                        System.out.println("Updated status: " + statusNum + "/" + goal);

                        if (statusNum == goal) {
                            habitDAO.updateStatus(habitToEdit, "Completed");
                            System.out.println("Goal reached! Status marked as 'Completed'.");
                        }
                    }
                }
                System.out.println("\nSuccessful update!");
                System.out.println("\nPress [Enter] to continue...");
                scanner.nextLine();
            } else {
                System.out.println("Failed! Habit not found.");
            }
        }
    }

    public void deleteHabits() {
        if (habitDAO.getAllHabits().isEmpty()) {
            System.out.println("No habits available to delete.");
            Design.delay(1500);
            return;
        }

        Design.sub(7);
        System.out.println("\nHere are all your daily habits with their current status:");
        List<String> habitNames = habitDAO.getAllHabits();

        for (String habitName : habitNames) {
            String[] habitDetails = habitDAO.getHabitDetails(habitName);

            String status = habitDetails[1];
            String statusMessage;

            switch (status) {
                case "0":
                    statusMessage = "not yet started";
                    break;
                case "Completed":
                    statusMessage = "complete";
                    break;
                default:
                    statusMessage = "in progress";
                    break;
            }

            System.out.println("Name: " + habitName + " - Status: " + statusMessage);
        }

        System.out.print("\nEnter the name of the habit you want to delete: ");
        String habitToDelete = scanner.nextLine();

        if (habitDAO.habitExists(habitToDelete)) {
            System.out.print("Are you sure you want to delete the habit \"" + habitToDelete + "\"? (y/n): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                habitDAO.deleteHabit(habitToDelete);
                System.out.println("Habit \"" + habitToDelete + "\" has been deleted successfully.");
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("Habit not found. Please check the name and try again.");
        }
    }
}
