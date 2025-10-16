package dev.fitverse.profile.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class BodyMetricsCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_UP);

    public Optional<BigDecimal> calculateBodyMassIndex(BigDecimal heightCm, BigDecimal weightKg) {
        if (heightCm == null || weightKg == null) {
            return Optional.empty();
        }
        if (heightCm.compareTo(BigDecimal.ZERO) <= 0 || weightKg.compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.empty();
        }

        BigDecimal heightMeters = heightCm.divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
        BigDecimal bmi = weightKg.divide(heightMeters.pow(2, MATH_CONTEXT), 2, RoundingMode.HALF_UP);
        return Optional.of(bmi);
    }
}
