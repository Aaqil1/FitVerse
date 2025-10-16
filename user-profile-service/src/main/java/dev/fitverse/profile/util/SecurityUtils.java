package dev.fitverse.profile.util;

import org.springframework.security.oauth2.jwt.Jwt;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Long extractUserId(Jwt jwt) {
        if (jwt == null) {
            throw new IllegalArgumentException("JWT token is required to resolve user id");
        }

        Object claim = jwt.getClaim("user_id");
        if (claim == null) {
            claim = jwt.getSubject();
        }
        if (claim == null) {
            throw new IllegalArgumentException("JWT token does not contain a user identifier");
        }

        if (claim instanceof Number number) {
            return number.longValue();
        }
        String value = claim.toString();
        return Long.parseLong(value);
    }
}
