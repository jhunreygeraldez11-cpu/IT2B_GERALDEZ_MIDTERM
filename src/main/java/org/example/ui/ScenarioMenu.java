
package com.gym.ui;

import com.gym.dao.ScenarioDao;
import com.gym.model.ScenarioEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {

    private final ScenarioDao dao;
    private final Scanner scanner = new Scanner(System.in);

    public ScenarioMenu(ScenarioDao dao) {
        this.dao = dao;
    }

    public void start() {
        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addMember();
                case "2" -> viewMembers();
                case "3" -> {
                    System.out.println("Exiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println(
                        "Invalid choice! Enter 1, 2, or 3."
                );
            }
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("          GYM MEMBERSHIP MANAGEMENT");
        System.out.println("==============================================");
        System.out.println("[1] Add New Member");
        System.out.println("[2] View All Members");
        System.out.println("[3] Exit System");
        System.out.println("==============================================");
    }

    private void addMember() {
        System.out.println("\n--- ADD NEW MEMBER ---");

        String fullName;

        while (true) {
            System.out.print("Enter Full Name: ");
            fullName = scanner.nextLine().trim();

            if (!fullName.isEmpty()) {
                break;
            }

            System.out.println("Full name cannot be empty.");
        }

        String tier;

        while (true) {
            System.out.print("Enter Tier (BASIC / PREMIUM / VIP): ");
            tier = scanner.nextLine().trim().toUpperCase();

            if (tier.equals("BASIC")
                    || tier.equals("PREMIUM")
                    || tier.equals("VIP")) {
                break;
            }

            System.out.println(
                    "Invalid tier. Choose BASIC, PREMIUM, or VIP."
            );
        }

        double monthlyFee;

        while (true) {
            System.out.print("Enter Monthly Fee: ");
            String input = scanner.nextLine().trim();

            try {
                monthlyFee = Double.parseDouble(input);

                if (Double.isFinite(monthlyFee) && monthlyFee >= 0) {
                    break;
                }

                System.out.println(
                        "Fee must be zero or a positive number."
                );

            } catch (NumberFormatException e) {
                System.out.println("Invalid fee. Example: 2500.00");
            }
        }

        String status;

        while (true) {
            System.out.print("Enter Status (ACTIVE / INACTIVE): ");
            status = scanner.nextLine().trim().toUpperCase();

            if (status.equals("ACTIVE") || status.equals("INACTIVE")) {
                break;
            }

            System.out.println(
                    "Invalid status. Choose ACTIVE or INACTIVE."
            );
        }

        ScenarioEntity member = new ScenarioEntity(
                0, fullName, tier, monthlyFee, status
        );

        try {
            dao.addMember(member);
            System.out.println(
                    "SUCCESS: Member record saved to database!"
            );
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to save member record.");
            System.err.println(e.getMessage());
        }
    }

    private void viewMembers() {
        System.out.println("\n                    ALL GYM MEMBERS");

        try {
            List<ScenarioEntity> members = dao.getAllMembers();

            if (members.isEmpty()) {
                System.out.println("No members found in the database.");
                return;
            }

            String line =
                    "+------+------------------------------+----------+-------------+----------+";

            System.out.println(line);
            System.out.printf(
                    "| %-4s | %-28s | %-8s | %11s | %-8s |%n",
                    "ID", "FULL NAME", "TIER", "MONTHLY FEE", "STATUS"
            );
            System.out.println(line);

            double totalMonthlyFees = 0;

            for (ScenarioEntity member : members) {
                System.out.printf(
                        "| %-4d | %-28.28s | %-8s | %11.2f | %-8s |%n",
                        member.id(),
                        member.fullName(),
                        member.tier(),
                        member.monthlyFee(),
                        member.status()
                );

                totalMonthlyFees += member.monthlyFee();
            }

            System.out.println(line);
            System.out.println(
                    "Total Records Displayed: " + members.size()
            );
            System.out.printf(
                    "Combined Monthly Fees: %.2f%n",
                    totalMonthlyFees
            );

        } catch (SQLException e) {
            System.out.println(
                    "ERROR: Unable to retrieve member records."
            );
            System.err.println(e.getMessage());
        }
    }
}
