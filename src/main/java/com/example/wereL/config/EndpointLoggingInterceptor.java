package com.example.wereL.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class EndpointLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(EndpointLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            String javaMethod = hm.getMethod().getDeclaringClass().getSimpleName()
                    + "#" + hm.getMethod().getName();

            String httpMethod = request.getMethod();
            String requestUri = request.getRequestURI();

            log.info("Вызван эндпоинт {} {} -> {}", httpMethod, requestUri, javaMethod);
        }
        return true;
    }
}