
package com.gym.dao;

import com.gym.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public ScenarioDao() throws SQLException {
        createTable();
    }

    private void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS members (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    full_name TEXT NOT NULL,
                    tier TEXT NOT NULL,
                    monthly_fee REAL NOT NULL,
                    status TEXT NOT NULL
                )
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }
    }

    public void addMember(ScenarioEntity member) throws SQLException {
        String sql = """
                INSERT INTO members
                (full_name, tier, monthly_fee, status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, member.fullName());
            statement.setString(2, member.tier());
            statement.setDouble(3, member.monthlyFee());
            statement.setString(4, member.status());

            statement.executeUpdate();
        }
    }

    public List<ScenarioEntity> getAllMembers() throws SQLException {
        List<ScenarioEntity> members = new ArrayList<>();

        String sql = """
                SELECT id, full_name, tier, monthly_fee, status
                FROM members
                ORDER BY id ASC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                members.add(new ScenarioEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("tier"),
                        resultSet.getDouble("monthly_fee"),
                        resultSet.getString("status")
                ));
            }
        }

        return members;
    }
}
