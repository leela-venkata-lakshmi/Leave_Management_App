
package com.wavemaker.leavemgmt.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavemaker.leavemgmt.model.LeaveRequest;
import com.wavemaker.leavemgmt.service.Impl.LeaveRequestServiceImpl;
import com.wavemaker.leavemgmt.service.LeaveRequestService;
import com.wavemaker.leavemgmt.util.LocalDateConverter;
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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
                String fromDateStr = req.getParameter("fromDate");
                String toDateStr = req.getParameter("toDate");
                String reason = req.getParameter("reason");
                String leaveType = req.getParameter("leaveType");

                if (fromDateStr == null || toDateStr == null) {
                    resp.sendRedirect(req.getContextPath() + "/leaveApplication.html?error=Date parameters are missing");
                    return;
                }

                try {
                    LocalDate fromDate = LocalDate.parse(fromDateStr); // Use LocalDate for easier date handling
                    LocalDate toDate = LocalDate.parse(toDateStr);

                    if (fromDate.isAfter(toDate)) {
                        resp.sendRedirect(req.getContextPath() + "/leaveApplication.html?error=From date cannot be after to date");
                        return;
                    }

                    LeaveRequest leaveRequest = new LeaveRequest();
                    leaveRequest.setEmpId(empId);
                    leaveRequest.setFromDate(fromDate); // Convert LocalDate to java.sql.Date
                    leaveRequest.setToDate(toDate);
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
                        logger.error("Leave request exceeds available balance");
                        resp.sendRedirect(req.getContextPath() + "/leaveApplication.html?error=exceedsAllocatedLeave");
                    }
                } catch (DateTimeParseException e) {
                    logger.error("Invalid date format: fromDate={}, toDate={}", fromDateStr, toDateStr, e);
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
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();
        String json = gson.toJson(leaveRequests);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
