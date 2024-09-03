
package com.wavemaker.leavemgmt.controller;

import com.google.gson.Gson;
import com.wavemaker.leavemgmt.model.LeaveRequest;
import com.wavemaker.leavemgmt.service.Impl.LeaveRequestServiceImpl;
import com.wavemaker.leavemgmt.service.LeaveRequestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/leaveRequest")
public class LeaveRequestServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LeaveRequestServlet.class);
    private LeaveRequestService leaveRequestService;

    @Override
    public void init() throws ServletException {
        try {
            leaveRequestService = new LeaveRequestServiceImpl();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize LeaveRequestService", e);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer empId = (Integer) session.getAttribute("empId");
        String action = req.getParameter("action");
        String reqIdParam = req.getParameter("reqId");

        if (empId == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        try {
            if ("approve".equals(action)) {
                if (reqIdParam != null) {
                    int reqId = Integer.parseInt(reqIdParam);
                    leaveRequestService.approveLeave(reqId);
                }
                resp.sendRedirect(req.getContextPath() + "/leaveApproval.html");
            } else if ("reject".equals(action)) {
                if (reqIdParam != null) {
                    int reqId = Integer.parseInt(reqIdParam);
                    leaveRequestService.rejectLeave(reqId);
                }
                resp.sendRedirect(req.getContextPath() + "/leaveApproval.html");
            } else {
                String fromDate = req.getParameter("fromDate");
                String toDate = req.getParameter("toDate");
                String reason = req.getParameter("reason");
                String leaveType = req.getParameter("leaveType");

                if (fromDate == null || toDate == null) {
                    throw new ServletException("Date parameters are missing");
                }

                try {
                    Date from = Date.valueOf(fromDate);
                    Date to = Date.valueOf(toDate);

                    LeaveRequest leaveRequest = new LeaveRequest();
                    leaveRequest.setEmpId(empId);
                    leaveRequest.setFromDate(from);
                    leaveRequest.setToDate(to);
                    leaveRequest.setReason(reason);
                    leaveRequest.setLeaveStatus(LeaveRequest.LeaveStatus.PENDING); // Default status
                    leaveRequest.setLeaveType(LeaveRequest.LeaveType.valueOf(leaveType.toUpperCase()));

                    // Calculate the number of days requested
                    int requestedDays = leaveRequest.calculateLeaveDays();

                    // Validate the leave request before submitting
                    boolean isValid = leaveRequestService.isLeaveRequestValid(empId, leaveRequest.getLeaveType(), requestedDays);

                    if (isValid) {
                        // Submit the leave request
                        leaveRequestService.submitLeaveRequest(leaveRequest);
                        resp.sendRedirect(req.getContextPath() + "/dashboard.html");
                    } else {
                        // Handle invalid leave request (e.g., exceeding available leave balance)
                        resp.sendRedirect(req.getContextPath() + "/leaveApplication.html?error=Leave request exceeds available balance");
                    }
                } catch (IllegalArgumentException e) {
                    logger.error("Invalid date format: fromDate={}, toDate={}", fromDate, toDate, e);
                    resp.sendRedirect(req.getContextPath() + "/leaveApplication.html?error=Invalid date format");
                }
            }
        } catch (SQLException | NumberFormatException e) {
            logger.error("Error processing leave request", e);
            resp.sendRedirect(req.getContextPath() + "/index.html");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer empId = (Integer) session.getAttribute("empId");

        if (empId == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmpId(empId);
        Gson gson = new Gson();
        String json = gson.toJson(leaveRequests);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
