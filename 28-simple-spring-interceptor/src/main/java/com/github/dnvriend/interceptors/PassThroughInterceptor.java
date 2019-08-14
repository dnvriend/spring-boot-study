package com.github.dnvriend.interceptors;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassThroughInterceptor implements HandlerInterceptor {
    public HandlerMethod getHandlerMethod(Object handler) {
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return (HandlerMethod) handler;
        }
        return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre handle: " + handler.getClass() + " -> " + getHandlerMethod(handler));
        HandlerMethod method = getHandlerMethod(handler);
        if(method != null) {
            System.out.println(method.getMethod().getName());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post handle: " + handler.getClass() + " -> " + getHandlerMethod(handler));
        HandlerMethod method = getHandlerMethod(handler);
        if(method != null) {
            System.out.println(method.getMethod().getName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after completion: " + handler.getClass() + " -> " + getHandlerMethod(handler));
        HandlerMethod method = getHandlerMethod(handler);
        if(method != null) {
            System.out.println(method.getMethod().getName());
        }
    }
}
