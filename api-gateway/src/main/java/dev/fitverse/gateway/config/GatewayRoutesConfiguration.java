package dev.fitverse.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayRoutesConfiguration {

    private static final HttpStatus[] RETRY_STATUSES = {
            HttpStatus.BAD_GATEWAY,
            HttpStatus.GATEWAY_TIMEOUT,
            HttpStatus.SERVICE_UNAVAILABLE
    };

    private static final HttpMethod[] RETRY_METHODS = {
            HttpMethod.GET,
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.DELETE
    };

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", route -> applyResilience(route,
                        "auth-service", "/auth/**")
                        .uri("http://auth-service:8081"))
                .route("user-profile-service", route -> applyResilience(route,
                        "user-profile-service", "/profiles/**")
                        .uri("http://user-profile-service:8082"))
                .route("foodvision-service", route -> applyResilience(route,
                        "foodvision-service", "/food/**")
                        .uri("http://foodvision-service:8083"))
                .route("calorie-tracker-service", route -> applyResilience(route,
                        "calorie-tracker-service", "/summary/**")
                        .uri("http://calorie-tracker-service:8084"))
                .route("fitness-recommendation-service", route -> applyResilience(route,
                        "fitness-recommendation-service", "/plans/**", "/workouts/**")
                        .uri("http://fitness-recommendation-service:8085"))
                .route("chatbot-service", route -> applyResilience(route,
                        "chatbot-service", "/chat/**", "/ws/chat")
                        .uri("http://chatbot-service:8086"))
                .route("notification-service", route -> applyResilience(route,
                        "notification-service", "/notifications/**", "/ws/notify")
                        .uri("http://notification-service:8087"))
                .route("analytics-service", route -> applyResilience(route,
                        "analytics-service", "/analytics/**")
                        .uri("http://analytics-service:8088"))
                .build();
    }

    private UriSpec applyResilience(PredicateSpec predicateSpec, String circuitBreakerName, String... paths) {
        return predicateSpec.path(paths)
                .filters(filters -> filters
                .circuitBreaker(config -> config
                        .setName(circuitBreakerName)
                        .setFallbackUri("forward:/fallback/unavailable"))
                .retry(config -> {
                    config.setRetries(2);
                    config.setStatuses(RETRY_STATUSES);
                    config.setMethods(RETRY_METHODS);
                }));
    }
}
