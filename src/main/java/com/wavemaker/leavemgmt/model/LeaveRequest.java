package com.wavemaker.leavemgmt.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaveRequest {
    private int reqId;
    private int empId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private LeaveStatus leaveStatus;
    private LeaveType leaveType;
    private Timestamp createdAt;


    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }


    public enum LeaveType {
        SICK,
        PTO,
        MATERNITY,
        PATERNITY
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int calculateLeaveDays() {
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("FromDate and ToDate cannot be null");
        }

        LocalDate start = fromDate;
        LocalDate end = toDate;
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }

}
