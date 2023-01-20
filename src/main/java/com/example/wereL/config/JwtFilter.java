package com.example.wereL.config;

import com.example.wereL.security.JwtAuth;
import com.example.wereL.security.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter implements Filter {
    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String token = gerBearerToken(request);
        System.out.println("request: "+servletRequest.getInputStream());
        System.out.println("path");
        System.out.println(request.getRequestURI());
        System.out.println(token);
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("response");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//        response.setHeader("Access-Control-Max-Age", "3600");
        if(token != null && jwtUtils.validateAccessToken(token)){
            final Claims claims = jwtUtils.getAccessClaims(token);
            final JwtAuth jwtAuth = jwtUtils.generate(claims);

            if(jwtAuth.getRoles() != null){
                jwtAuth.setAuthenticated(true);
            }
            SecurityContextHolder.getContext().setAuthentication(jwtAuth);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
    private String gerBearerToken(final HttpServletRequest request){
        final String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
