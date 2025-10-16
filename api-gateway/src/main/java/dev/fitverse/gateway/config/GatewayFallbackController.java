package dev.fitverse.gateway.config;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class GatewayFallbackController {

    @GetMapping(path = "/unavailable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> serviceUnavailable() {
        Map<String, Object> payload = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", HttpStatus.SERVICE_UNAVAILABLE.value(),
                "error", "Service Unavailable",
                "message", "Downstream service is temporarily unavailable. Please retry shortly.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(payload);
    }
}
