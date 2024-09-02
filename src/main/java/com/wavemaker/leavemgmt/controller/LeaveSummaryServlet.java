package com.wavemaker.leavemgmt.controller;

import com.google.gson.Gson;
import com.wavemaker.leavemgmt.model.LeaveSummary;
import com.wavemaker.leavemgmt.service.Impl.LeaveRequestServiceImpl;
import com.wavemaker.leavemgmt.service.LeaveRequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/leaveSummary")
public class LeaveSummaryServlet extends HttpServlet {
    private LeaveRequestService leaveRequestService;

    @Override
    public void init() throws ServletException {
        try {
            leaveRequestService = new LeaveRequestServiceImpl(); // or use dependency injection
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        HttpSession session = req.getSession();
        Integer empId = (Integer) session.getAttribute("empId");

        if (empId == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        try {
            // Fetch leave data from the service
            LeaveSummary leaveSummary = leaveRequestService.getLeaveSummary(empId);

            // Set the response type and send JSON data
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(new Gson().toJson(leaveSummary));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching leave data.");
        }
    }
}

