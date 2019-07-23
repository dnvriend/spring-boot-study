package com.github.dnvriend.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("X-FILTER-REQ-RESP-LOGGER-FILTER", "MY_VALUE");
        logger.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        // before the chain

        // The sequence is as follows
        // 1. before the code we look at now, the code of AddHeaderFilter has been invoked, the parts 'before the chain'
        // 2. Then AddHeaderFilter calls 'chain.doFilter(req,resp)
        // 3. We get invoked but only the parts 'before the chain'
        // 4. We call chain.doFilter(req, resp)
        // 5. other filters will be invoked that have a higher number, but only the parts 'before the chain'

        // call the chain
        chain.doFilter(request, response);

        // 6. After all the filters have been called, the chain will unwind, so filters will be called in reverse order
        // 7. Other filters will be called, but only the parts 'after the chain'
        // 7. We get invoked, but only the parts 'after the chain'
        // 8. The Filter AddHeaderFilter will be invoked, but only the part after the chain.

        // visually

        //  http req  --> | AddHeaderFilter | --> ReqRespLoggingFilter --> | X-Y-Z Filter | --->
        //                                                                                     |
        //  http resp <-- | AddHeaderFilter | <-- ReqRespLoggingFilter <-- | X-Y-Z Filter | <--- http resp

        // after the chain
        logger.info("Logging Response :{}", resp.getContentType());
        resp.getHeaderNames()
                .stream()
                .map(x -> Tuple.builder()._1(x)._2(resp.getHeaderNames()).build())
                .forEach(System.out::println);
    }
}

@Data
@AllArgsConstructor
@Builder
class Tuple {
    String _1;
    Collection<String> _2;
}
