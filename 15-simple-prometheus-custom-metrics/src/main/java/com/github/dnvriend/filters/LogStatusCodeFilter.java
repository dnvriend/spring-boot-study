package com.github.dnvriend.filters;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class LogStatusCodeFilter implements Filter {

    private final MeterRegistry registry;

    public LogStatusCodeFilter(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        filterChain.doFilter(servletRequest, servletResponse);
        if (req.getRequestURI().contains("/person")) {
            registry.counter("person_http_status_code",
                Collections.singleton(Tag.of("code", String.valueOf(resp.getStatus()))))
                .increment();
        }
    }
}
