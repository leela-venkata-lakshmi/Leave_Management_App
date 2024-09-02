package com.wavemaker.leavemgmt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false); // false means don't create a new session if one doesn't exist
        if (session != null) {
            session.invalidate(); // Invalidates the session, removing all attributes
        }

        // Redirect to login page
        resp.sendRedirect("index.html");
    }
}
