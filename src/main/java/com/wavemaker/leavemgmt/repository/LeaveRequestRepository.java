package com.wavemaker.leavemgmt.repository;

import com.wavemaker.leavemgmt.model.LeaveRequest;

import java.sql.SQLException;
import java.util.List;

public interface LeaveRequestRepository {
    void submitLeaveRequest(LeaveRequest leaveRequest);

    List<LeaveRequest> getLeaveRequestsByEmpId(int empId);

    List<LeaveRequest> getLeaveRequestsByManagerId(int empId);

    boolean approveLeave(int leaveId) throws SQLException;

    boolean rejectLeave(int leaveId) throws SQLException;
    int getUtilizedLeave(int employeeId, LeaveRequest.LeaveType leaveType);
}
