package dev.fitverse.gateway.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String attributeCorrelationId = exchange.getAttribute(CORRELATION_ID_HEADER);
        String existingCorrelationId = StringUtils.hasText(attributeCorrelationId)
                ? attributeCorrelationId
                : exchange.getRequest().getHeaders().getFirst(CORRELATION_ID_HEADER);
        final String correlationId = StringUtils.hasText(existingCorrelationId)
                ? existingCorrelationId
                : UUID.randomUUID().toString();

        ServerWebExchange exchangeToFilter = StringUtils.hasText(existingCorrelationId)
                ? exchange
                : mutateExchangeWithCorrelationId(exchange, correlationId);

        exchangeToFilter.getAttributes().put(CORRELATION_ID_HEADER, correlationId);

        exchangeToFilter.getResponse().beforeCommit(() -> {
            exchangeToFilter.getResponse().getHeaders().set(CORRELATION_ID_HEADER, correlationId);
            return Mono.empty();
        });

        return chain.filter(exchangeToFilter)
                .contextWrite(context -> context.put(CORRELATION_ID_HEADER, correlationId));
    }

    private ServerWebExchange mutateExchangeWithCorrelationId(ServerWebExchange exchange, String correlationId) {
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .headers(headers -> headers.set(CORRELATION_ID_HEADER, correlationId))
                .build();
        return exchange.mutate().request(mutatedRequest).build();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
