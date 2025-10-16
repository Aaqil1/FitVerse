package dev.fitverse.profile.dto;

import java.math.BigDecimal;

import dev.fitverse.profile.domain.Gender;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ProfileUpdateRequest {

    @DecimalMin(value = "50.0", message = "Height must be at least 50 cm", inclusive = true)
    @DecimalMax(value = "280.0", message = "Height must be at most 280 cm", inclusive = true)
    @Digits(integer = 3, fraction = 2, message = "Height must have at most 2 decimal places")
    private BigDecimal heightCm;

    @DecimalMin(value = "20.0", message = "Weight must be at least 20 kg", inclusive = true)
    @DecimalMax(value = "400.0", message = "Weight must be at most 400 kg", inclusive = true)
    @Digits(integer = 3, fraction = 2, message = "Weight must have at most 2 decimal places")
    private BigDecimal weightKg;

    @Min(value = 5, message = "Age must be at least 5 years")
    @Max(value = 120, message = "Age must be at most 120 years")
    private Integer age;

    private Gender gender;

    private String goal;

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
}
