package com.github.dnvriend.aspect;

import com.github.dnvriend.annotation.LogAnotherMessage;
import com.github.dnvriend.annotation.LogMessage;
import java.lang.reflect.Method;
import java.util.Optional;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
public class ExampleAspect {
    /*
        We have annotated our method with @Around. This is our advice, and around advice means
        we are adding extra code both before and after method execution. There are other types of
        advice, such as before and after.

        @Around annotation has a point cut argument. Our pointcut just says, ‘Apply this advice any
        method which is annotated with @LogExecutionTime.'

        The method logExecutionTime() itself is our advice. There is a single argument, ProceedingJoinPoint.
        In our case, this will be an executing method which has been annotated with @LogExecutionTime.
     */
    @Around("@annotation(com.github.dnvriend.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.debug(joinPoint.getSignature() + " executed in " + executionTime + " ms");
        return proceed;
    }

    @Around("@annotation(com.github.dnvriend.annotation.LogMessage)")
    public Object logMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        LogMessage logMessage = method.getAnnotation(LogMessage.class);

        String value = logMessage.value();
        log.debug("logMessage: " + value);
        return joinPoint.proceed();
    }

    // alternativly use the AnnotationUtils of Spring
    private static String findLogMessage(Method method) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(method, LogMessage.class))
            .map(LogMessage::value)
            .orElse("");
    }

    @Around("execution(public * *(..)) && @annotation(logAnotherMessage)")
    public Object logAnotherMessage(ProceedingJoinPoint joinPoint, LogAnotherMessage logAnotherMessage) throws Throwable {
        // alternatively we can just inject the annotation
        log.debug("Logging another message: {}", logAnotherMessage.value());
        return joinPoint.proceed();
    }

    @Before("@annotation(com.github.dnvriend.annotation.LogRequest)")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request = getHttpServletRequest();
        log.info("{} {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
    }

    /**
     * Get request bound to the Thread
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
