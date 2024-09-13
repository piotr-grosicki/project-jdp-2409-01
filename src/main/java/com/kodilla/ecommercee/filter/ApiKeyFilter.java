package com.kodilla.ecommercee.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ApiKeyFilter implements Filter {

    private static final String API_KEY = "Gh3p123"; //our key
    private static final String API_KEY_HEADER = "x-api-key"; //header

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String apiKey = httpRequest.getHeader(API_KEY_HEADER);

        // checking if provided key is the same as secret key
        if (API_KEY.equals(apiKey)) {
            chain.doFilter(request, response);  // key is valid, proceed
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);  // key is invalid
            httpResponse.getWriter().write("Forbidden: Invalid API Key");
        }
    }
}
