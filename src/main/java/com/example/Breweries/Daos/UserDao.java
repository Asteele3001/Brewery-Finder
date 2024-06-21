package com.example.Breweries.Daos;

import com.example.Breweries.Models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;

    public UserDao(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * from users;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            users.add(mapRowToUser(results));
        }
        return users;
    }

        public User getUserByUsername(String username) {
            SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT * from users WHERE username = ?", username);
            if (results.next()) {
                return mapRowToUser(results);
        } else {
                return null;
            }
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?, ?);", user.getUsername(), hashedPassword);
        return getUserByUsername((user.getUsername()));
    }

    public void setUserPassword(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", hashedPassword, username);
    }

    public void deleteUser(String username) {
        jdbcTemplate.update("DELETE from users WHERE username = ?;", username);
    }

    public List<String> getUserRoles(String username) {
        List<String> roles = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT role FROM roles WHERE username = ?", username);
        while (results.next()) {
            roles.add(results.getString("role"));
        }
        return roles;
    }

    public void addRoleToUser(String username, String role) {
        jdbcTemplate.update("INSERT into roles (username, role) VALUES (?, ?);", username, role);
    }

    public void removeRoleFromUser(String username, String role) {
        jdbcTemplate.update("UPDATE roles SET role = ? WHERE username = ?;", role, username);
    }

    public User mapRowToUser(SqlRowSet rowSet) {
        User user = new User();
        user.setUsername(rowSet.getString("username"));
        user.setPassword(rowSet.getString("password"));
        return user;
    }
}
