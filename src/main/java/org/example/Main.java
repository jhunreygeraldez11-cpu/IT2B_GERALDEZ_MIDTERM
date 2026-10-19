
package com.gym;

import com.gym.dao.ScenarioDao;
import com.gym.ui.ScenarioMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ScenarioDao dao = new ScenarioDao();

            System.out.println("Database is ready!");

            ScenarioMenu menu = new ScenarioMenu(dao);
            menu.start();

        } catch (SQLException e) {
            System.err.println("Failed to initialize the database.");
            System.err.println(e.getMessage());
        }
    }
}
