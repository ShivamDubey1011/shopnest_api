package com.shopnest.shopnest_api.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter extends OncePerRequestFilter {
    /** HTTP header used to pass the correlation ID between services and clients. */

    public static final String HEADER = "X-Correlation-Id";
    /** MDC key; referenced in the log pattern as %X{correlationId}. */

    public static final String MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String correlationId = resolveCorrelationId(request);
        long start = System.currentTimeMillis();

        MDC.put(MDC_KEY, correlationId);
        response.setHeader(HEADER, correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            long durationMs = System.currentTimeMillis() - start;
            log.info("HTTP {} {} status={} durationMs={}", request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs);
            MDC.remove(MDC_KEY);
        }

    }

    /**
     * Uses the incoming header if present and sane, otherwise generates a new UUID.
     * Length is capped so a malicious client can't inject huge values into logs.
     */
    private String resolveCorrelationId(HttpServletRequest request) {
        String incoming = request.getHeader(HEADER);
        if (incoming != null && !incoming.isBlank() && incoming.length() <= 64) {
            return incoming;

        }
        return UUID.randomUUID().toString();
    }

    /** Skips health checks so monitoring pings don't flood the logs. */

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }
}
