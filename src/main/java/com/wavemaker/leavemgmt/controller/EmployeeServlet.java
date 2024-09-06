package com.wavemaker.leavemgmt.controller;

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
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/employeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);
    private EmployeeService employeeService = new EmployeeServiceImpl(); // Implement this service to fetch employee data

    public EmployeeServlet() throws SQLException {
    }


protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    HttpSession session = req.getSession(false);
    if (session != null) {
        Integer empId = (Integer) session.getAttribute("empId");
        if (empId != null) {
            try {
                Employee employee = employeeService.getEmployeeById(empId); // Fetch the employee by ID
                if (employee != null) {


                    // Set response type and send employee details
                    resp.setContentType("application/json");
                    PrintWriter out = resp.getWriter();

                    // Construct JSON response string with null handling
                    String jsonResponse = String.format(
                            "{\"empId\":%d, \"empFirstName\":\"%s\", \"empLastName\":\"%s\", \"managerId\":%s, " +
                                    "\"dateOfBirth\":\"%s\", \"phoneNumber\":\"%s\", \"role\":\"%s\", \"gender\":\"%s\"}",
                            empId,
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getManagerId() != null ? employee.getManagerId() : "null",
                            employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : "",
                            employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "",
                            employee.getRole(),
                            employee.getGender() != null ? employee.getGender().toString() : ""
                    );

                    out.print(jsonResponse);
                    out.flush();

                } else {
                    // Handle case where employee is not found
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
                }
            } catch (IllegalArgumentException e) {
                // Handle potential enum value parsing issues
                logger.error("Invalid enum value", e);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data format");
            } catch (Exception e) {
                // Catch-all for any other exceptions
                logger.error("General error fetching employee details", e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No employee ID in session");
        }
    } else {
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No session found");
    }
}
}