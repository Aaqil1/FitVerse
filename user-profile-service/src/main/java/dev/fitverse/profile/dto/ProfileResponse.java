package dev.fitverse.profile.dto;

import java.math.BigDecimal;
import java.time.Instant;

import dev.fitverse.profile.domain.Gender;

public class ProfileResponse {

    private Long userId;
    private BigDecimal heightCm;
    private BigDecimal weightKg;
    private Integer age;
    private Gender gender;
    private String goal;
    private BigDecimal bodyMassIndex;
    private Instant updatedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(BigDecimal heightCm) {
        this.heightCm = heightCm;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public BigDecimal getBodyMassIndex() {
        return bodyMassIndex;
    }

    public void setBodyMassIndex(BigDecimal bodyMassIndex) {
        this.bodyMassIndex = bodyMassIndex;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
