package com.github.dnvriend.filters;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrapFilter implements Filter {

    private final List<Filter> filters;

    public WrapFilter(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        for (Filter filter : filters) {
            log.debug("Applying filter: {}", filter);
            filter.doFilter(servletRequest, servletResponse, filterChain);
        }
        if(filters.isEmpty()) filterChain.doFilter(servletRequest, servletResponse);
    }
}
