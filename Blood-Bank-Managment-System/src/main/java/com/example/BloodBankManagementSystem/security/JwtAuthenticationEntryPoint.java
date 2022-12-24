package com.example.BloodBankManagementSystem.security;



import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component //it's object will be automatically created by spring container and we can also autowire it
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*this will be triggered when an un-authorised person try to access the protected route that is
    we try to access the routes without passing the jwt token inside the header*/
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied !!!");
    }
}