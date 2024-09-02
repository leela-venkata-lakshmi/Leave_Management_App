package com.wavemaker.leavemgmt.controller;

import com.wavemaker.leavemgmt.service.Impl.LoginServiceImpl;
import com.wavemaker.leavemgmt.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    LoginService loginService =new LoginServiceImpl();

    public LoginServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("EMAIL");
        String password=req.getParameter("PASSWORD");
        PrintWriter out=resp.getWriter();

        Integer empId= loginService.isValidate(email,password);
        if(empId != null)
        {
            HttpSession session= req.getSession();
            session.setAttribute("userEmail",email);
            session.setAttribute("userPassword",password);
            session.setAttribute("empId",empId);
            session.setAttribute("isLoggedIn", true);
            out.print("valid Credentials.");
            resp.sendRedirect("dashboard.html");
        }else{

            out.print("Invalid Credentials. Please try again.");
            resp.sendRedirect("index.html?error=Invalid+credentials");

        }
    }
}
