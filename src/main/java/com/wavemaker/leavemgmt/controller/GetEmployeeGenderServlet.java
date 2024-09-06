package com.wavemaker.leavemgmt.controller;

import com.wavemaker.leavemgmt.exception.EmployeeNotFoundException;
import com.wavemaker.leavemgmt.model.Employee;
import com.wavemaker.leavemgmt.service.EmployeeService;
import com.wavemaker.leavemgmt.service.Impl.EmployeeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/getEmployeeGender")
public class GetEmployeeGenderServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GetEmployeeGenderServlet.class);
    EmployeeService employeeService=new EmployeeServiceImpl();

    public GetEmployeeGenderServlet() throws SQLException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer empId = (Integer) session.getAttribute("empId");
        Employee employee = null;
        try {
            employee = employeeService.getEmployeeById(empId);
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }

        logger.info("it came inside");
        if (session != null) {
            String gender = String.valueOf(employee.getGender()); // Fetch the gender from session
logger.info("{}",gender);
            if (gender != null) {
                logger.info("Gender: {}", gender);
                response.setContentType("application/json");
                response.getWriter().write("{\"gender\": \"" + gender + "\"}");
            } else {
                logger.info("it came iinside");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Gender not found in session");
            }
        } else {
            logger.info("it came iiiinside");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session not found");
        }
    }
}
