package dev.fitverse.gateway.filter;

import java.util.UUID;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class CorrelationIdWebFilter implements WebFilter, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String existingCorrelationId = exchange.getAttribute(CorrelationIdFilter.CORRELATION_ID_HEADER);
        if (!StringUtils.hasText(existingCorrelationId)) {
            existingCorrelationId = exchange.getRequest().getHeaders().getFirst(CorrelationIdFilter.CORRELATION_ID_HEADER);
        }

        final String correlationId = StringUtils.hasText(existingCorrelationId)
                ? existingCorrelationId
                : UUID.randomUUID().toString();

        ServerWebExchange exchangeToFilter = exchange;
        if (!StringUtils.hasText(existingCorrelationId)) {
            exchangeToFilter = exchange.mutate()
                    .request(builder -> builder.headers(headers -> headers.set(CorrelationIdFilter.CORRELATION_ID_HEADER, correlationId)))
                    .build();
        }

        final ServerWebExchange targetExchange = exchangeToFilter;
        final String correlationIdValue = correlationId;

        targetExchange.getAttributes().put(CorrelationIdFilter.CORRELATION_ID_HEADER, correlationIdValue);
        targetExchange.getResponse().beforeCommit(() -> {
            targetExchange.getResponse().getHeaders().set(CorrelationIdFilter.CORRELATION_ID_HEADER, correlationIdValue);
            return Mono.empty();
        });

        return chain.filter(targetExchange);
    }
}
