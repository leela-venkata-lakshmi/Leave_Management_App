package com.wavemaker.leavemgmt.repository.Impl;

import com.wavemaker.leavemgmt.model.LeaveRequest;
import com.wavemaker.leavemgmt.repository.LeaveRequestRepository;
import com.wavemaker.leavemgmt.util.DBConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestRepositoryImpl implements LeaveRequestRepository {

    private final Connection connection;
    private final static  String INSERT_QUERY = "INSERT INTO leave_request (EMP_ID, FROM_DATE, TO_DATE, REASON, LEAVE_STATUS, LEAVE_TYPE) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SELECT_QUERY = "SELECT * FROM leave_request WHERE EMP_ID = ?";
    private final static String JOIN_QUERY = "SELECT lr.* FROM leave_request lr JOIN employee e ON lr.EMP_ID = e.EMP_ID WHERE e.MANAGER_ID = ?";
    private final static String COUNT_QUERY="SELECT COUNT(*) FROM leave_request WHERE emp_id = ? AND leave_type = ? AND Leave_status = 'APPROVED'";
    private final static String UPDATE_QUERY = "UPDATE leave_request SET leave_status = ? WHERE req_id = ?";
    public LeaveRequestRepositoryImpl() throws SQLException {
        this.connection = DBConnectionUtil.getConnection();
    }

    @Override
    public void submitLeaveRequest(LeaveRequest leaveRequest) {


        try (PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY)) {

            stmt.setInt(1, leaveRequest.getEmpId());
            stmt.setDate(2, java.sql.Date.valueOf(leaveRequest.getFromDate()));
            stmt.setDate(3, java.sql.Date.valueOf(leaveRequest.getToDate()));
            stmt.setString(4, leaveRequest.getReason());
            stmt.setString(5, leaveRequest.getLeaveStatus().name());
            stmt.setString(6, leaveRequest.getLeaveType().name());


            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<LeaveRequest> getLeaveRequestsByEmpId(int empId) {

        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(SELECT_QUERY)) {

            stmt.setInt(1, empId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LeaveRequest leaveRequest = mapResultSetToLeaveRequest(rs);
                    leaveRequests.add(leaveRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveRequests;
    }



    private LeaveRequest mapResultSetToLeaveRequest(ResultSet rs) throws SQLException {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setReqId(rs.getInt("REQ_ID"));
        leaveRequest.setEmpId(rs.getInt("EMP_ID"));
        leaveRequest.setFromDate(rs.getDate("FROM_DATE").toLocalDate());
        leaveRequest.setToDate(rs.getDate("TO_DATE").toLocalDate());
        leaveRequest.setReason(rs.getString("REASON"));
        leaveRequest.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        leaveRequest.setLeaveStatus(LeaveRequest.LeaveStatus.valueOf(rs.getString("LEAVE_STATUS")));
        leaveRequest.setLeaveType(LeaveRequest.LeaveType.valueOf(rs.getString("LEAVE_TYPE")));

        return leaveRequest;
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByManagerId(int empId) {

        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(JOIN_QUERY)) {

            stmt.setInt(1, empId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LeaveRequest leaveRequest = mapResultSetToLeaveRequest(rs);
                    leaveRequests.add(leaveRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveRequests;
    }

    @Override
    public boolean approveLeave(int reqId) throws SQLException {
        return updateLeaveStatus(reqId, LeaveRequest.LeaveStatus.APPROVED);
    }

    @Override
    public boolean rejectLeave(int reqId) throws SQLException {
        return updateLeaveStatus(reqId, LeaveRequest.LeaveStatus.REJECTED);
    }

    @Override
    public int getUtilizedLeave(int employeeId, LeaveRequest.LeaveType leaveType) {


        int utilizedLeave = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY)) {

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, leaveType.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    utilizedLeave = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return utilizedLeave;

    }

    private boolean updateLeaveStatus(int reqId, LeaveRequest.LeaveStatus status) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, reqId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }


}