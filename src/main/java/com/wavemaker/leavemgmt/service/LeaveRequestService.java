package com.wavemaker.leavemgmt.service;

import com.wavemaker.leavemgmt.model.LeaveRequest;
import com.wavemaker.leavemgmt.model.LeaveSummary;

import java.sql.SQLException;
import java.util.List;

public interface LeaveRequestService {
    void submitLeaveRequest(LeaveRequest leaveRequest);
    List<LeaveRequest> getLeaveRequestsByEmpId(int empId);

    List<LeaveRequest> getLeaveRequestsByManagerId(int empId);

    boolean approveLeave(int leaveId) throws SQLException;

    boolean rejectLeave(int leaveId) throws SQLException;

    boolean isLeaveRequestValid(int employeeId, LeaveRequest.LeaveType leaveType, int numberOfDays);

    int getUtilizedLeave(int employeeId, LeaveRequest.LeaveType leaveType);

    LeaveSummary getLeaveSummary(int employeeId);
}
