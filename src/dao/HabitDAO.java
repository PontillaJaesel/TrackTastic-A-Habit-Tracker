package dao;

import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitDAO {
    private final Connection connection;

    public HabitDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed.", e);
        }
    }

    public void addHabit(String habitName, String description, String status, String goal, String tag) {
        String sql = "INSERT INTO habits (habit_name, description, status, goal, tag) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, habitName);
            stmt.setString(2, description);
            stmt.setString(3, status);
            stmt.setString(4, goal);
            stmt.setString(5, tag);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add habit.", e);
        }
    }

    public List<String> getAllHabits() {
        List<String> habits = new ArrayList<>();
        String query = "SELECT * FROM habits";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(resultSet.getString("habit_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all habits.", e);
        }
        return habits;
    }

    public void updateStatus(String habitName, String newStatus) {
        String query = "UPDATE habits SET status = ? WHERE habit_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newStatus);
            statement.setString(2, habitName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update habit status.", e);
        }
    }

    public void deleteHabit(String habitName) {
        String query = "DELETE FROM habits WHERE habit_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, habitName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete habit.", e);
        }
    }

    public String[] getHabitDetails(String habitName) {
        String query = "SELECT * FROM habits WHERE habit_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, habitName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new String[]{
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getString("goal"),
                        resultSet.getString("tag")
                };
            } else {
                return null; 
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve habit details.", e);
        }
    }

    public boolean habitExists(String habitName) {
        String query = "SELECT COUNT(*) FROM habits WHERE habit_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, habitName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check habit existence.", e);
        }
    }

}
