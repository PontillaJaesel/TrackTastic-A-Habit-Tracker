package habit;

import java.util.Scanner;

import dao.HabitDAO;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Habit {
    public Scanner scanner = new Scanner(System.in);
    protected LocalDate today = LocalDate.now();
    protected DateTimeFormatter day = DateTimeFormatter.ofPattern("EEEE");
    public ArrayList<String> listOfHabits = new ArrayList<>();
    protected String habitName, habitDescription, category, status;
    protected String habitGoal;

    protected HabitDAO habitDAO = new HabitDAO();

    public Habit()
    {
        this.habitDAO = new HabitDAO();
    }

    public abstract void createHabit(String tag);

    public abstract boolean printHabits(int indicator, int type);

    public abstract void updateStatus(int indicator);

    public abstract void deleteHabits();

    public static boolean isValidInteger(String inputValue) {
        try {
            Integer.parseInt(inputValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}