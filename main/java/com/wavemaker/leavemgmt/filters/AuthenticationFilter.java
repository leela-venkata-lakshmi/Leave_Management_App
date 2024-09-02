package com.wavemaker.leavemgmt.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);
        Boolean isLoggedIn = (session != null) ? (Boolean) session.getAttribute("isLoggedIn") : null;

        // Skip filtering for the following paths
        if (requestURI.startsWith(httpRequest.getContextPath() + "/static/") ||
                requestURI.endsWith(".css") || requestURI.endsWith(".js") ||
                requestURI.endsWith(".png") || requestURI.endsWith(".jpg") ||
                requestURI.endsWith(".gif") ||
                requestURI.equals(httpRequest.getContextPath() + "/index.html") ||
                requestURI.equals(httpRequest.getContextPath() + "/login")) {

            // Allow access to static resources, login page, and login servlet
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isLoggedIn != null && isLoggedIn) {
            // User is authenticated, allow request to proceed
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // User is not authenticated, redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.html");
        }
    }


}