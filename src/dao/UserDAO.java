package dao;

import config.DBConnection;
import entrance.User;

import java.sql.*;

public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed.", e);
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password, bio) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getBio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean usernameExists(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int login(String username, String password) {
        String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public boolean updateBio(int userId, String newBio) {
        String sql = "UPDATE users SET bio = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newBio);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] getCredentials(String username) {
        try {
            String query = "SELECT password, bio FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String bio = resultSet.getString("bio");
                return new String[] { password, bio };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBio(String username) {
        String sql = "SELECT bio FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("bio");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving bio: " + e.getMessage());
        }
        return null;
    }

}

