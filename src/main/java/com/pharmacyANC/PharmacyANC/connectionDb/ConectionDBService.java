package com.pharmacyANC.PharmacyANC.connectionDb;

import com.pharmacyANC.PharmacyANC.busines.model.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ConectionDBService {
    private static final String dbServer = "postgresql-54685-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 18702; // change it to your database server port
    private static final String dbName = "Pharmacy-ANC";
    private static final String userName = "ViktorRoot";
    private static final String password = "Viktor547";
    private static final String DB = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s",
            dbServer, dbPort, dbName, userName, password);

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB);

        } catch (SQLException throwaways) {
            throwaways.printStackTrace();
        }
    }


    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setDateRegistration(resultSet.getString("date_registration"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public User addUser(User user) {
        User candidate = null;
        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?, ?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                candidate = new User();
                candidate.setUserId(resultSet.getLong("user_id"));
                candidate.setUsername(resultSet.getString("username"));
                candidate.setEmail(resultSet.getString("email"));
                candidate.setDateRegistration(resultSet.getString("date_registration"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return candidate;
    }

    public Optional<User> findByUserName(String username) {
        User candidate = null;
        try {
            Statement statement = connection.createStatement();
            String SQL = String.format("SELECT * FROM users WHERE username IN ('%s')", username);
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                candidate = new User();
                candidate.setUserId(resultSet.getLong("user_id"));
                candidate.setUsername(resultSet.getString("username"));
                candidate.setPassword(resultSet.getString("password"));
                candidate.setEmail(resultSet.getString("email"));
                candidate.setDateRegistration(resultSet.getString("date_registration"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert candidate != null;
        return Optional.of(candidate);
    }

    public Optional<User> findByEmail(String email) {
        User candidate = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email IN (?)");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                candidate = new User();
                candidate.setUserId(resultSet.getLong("user_id"));
                candidate.setUsername(resultSet.getString("username"));
                candidate.setEmail(resultSet.getString("email"));
                candidate.setDateRegistration(resultSet.getString("date_registration"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.of(candidate);
    }

    public User findByName(String name) {
        User candidate = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id,  username, email, date_registration  FROM users WHERE username=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                candidate = new User();
                candidate.setUserId(resultSet.getLong("user_id"));
                candidate.setUsername(resultSet.getString("username"));
                candidate.setEmail(resultSet.getString("email"));
                candidate.setDateRegistration(resultSet.getString("date_registration"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return candidate;
    }


}
