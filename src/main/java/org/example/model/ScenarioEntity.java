
package com.gym.model;

public record ScenarioEntity(
        int id,
        String fullName,
        String tier,
        double monthlyFee,
        String status
) {
}
