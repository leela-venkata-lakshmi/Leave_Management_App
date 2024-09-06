package com.wavemaker.leavemgmt.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wavemaker.leavemgmt.model.LeaveRequest;
import com.wavemaker.leavemgmt.repository.Impl.LeaveRequestRepositoryImpl;
import com.wavemaker.leavemgmt.repository.LeaveRequestRepository;
import com.wavemaker.leavemgmt.service.Impl.LeaveRequestServiceImpl;
import com.wavemaker.leavemgmt.service.LeaveRequestService;
import com.wavemaker.leavemgmt.util.LocalDateConverter;
import jakarta.servlet.RequestDispatcher;
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
import java.util.List;

@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ApprovalServlet.class);
    LeaveRequestService leaveRequestService =new LeaveRequestServiceImpl();

    public ApprovalServlet() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer empId = (Integer) session.getAttribute("empId");

        logger.info("employee:{}",empId);
        if (empId == null) {
            resp.sendRedirect("index.html");
        }
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByManagerId(empId);
        Gson gson = new Gson();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter()).create();
        String json = gson.toJson(leaveRequests);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
