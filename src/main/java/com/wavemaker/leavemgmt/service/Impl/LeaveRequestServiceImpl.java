package com.wavemaker.leavemgmt.service.Impl;

import com.wavemaker.leavemgmt.factory.LeaveRequestRepositoryFactory;
import com.wavemaker.leavemgmt.model.LeaveRequest;
import com.wavemaker.leavemgmt.model.LeaveRequest.LeaveType;
import com.wavemaker.leavemgmt.model.LeaveSummary;
import com.wavemaker.leavemgmt.repository.Impl.LeaveRequestRepositoryImpl;
import com.wavemaker.leavemgmt.repository.LeaveRequestRepository;
import com.wavemaker.leavemgmt.service.LeaveRequestService;

import java.sql.SQLException;
import java.util.List;

public class LeaveRequestServiceImpl implements LeaveRequestService {
    LeaveRequestRepository leaveRequestRepository = new LeaveRequestRepositoryImpl();

    public LeaveRequestServiceImpl() throws SQLException {
        this.leaveRequestRepository= LeaveRequestRepositoryFactory.getLeaveRequestRepositoryInstance();
    }

    @Override
    public void submitLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequestRepository.submitLeaveRequest(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByEmpId(int empId) {
        return leaveRequestRepository.getLeaveRequestsByEmpId(empId);
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByManagerId(int empId) {
        return leaveRequestRepository.getLeaveRequestsByManagerId(empId);
    }

    @Override
    public boolean approveLeave(int leaveId) throws SQLException {
        return leaveRequestRepository.approveLeave(leaveId);
    }

    @Override
    public boolean rejectLeave(int leaveId) throws SQLException {
        return leaveRequestRepository.rejectLeave(leaveId);
    }


    @Override
    public boolean isLeaveRequestValid(int employeeId, LeaveType leaveType, int numberOfDays) {
        // Define maximum allocated leave values
        int maxSickLeave = 5;
        int maxPTO = 20;
        int maxMaternityLeave = 50;
        int maxPaternityLeave = 10;

        int utilizedLeave = getUtilizedLeave(employeeId, leaveType);

        int maxAllowedLeave;
        switch (leaveType) {
            case SICK:
                maxAllowedLeave = maxSickLeave;
                break;
            case PTO:
                maxAllowedLeave = maxPTO;
                break;
            case MATERNITY:
                maxAllowedLeave = maxMaternityLeave;
                break;
            case PATERNITY:
                maxAllowedLeave = maxPaternityLeave;
                break;
            default:
                throw new IllegalArgumentException("Unknown leave type: " + leaveType);
        }
//        int availableLeave = maxAllowedLeave - utilizedLeave;
        // Check if the requested leave is valid
        return (utilizedLeave + numberOfDays) <= maxAllowedLeave;
    }

    @Override
    public int getUtilizedLeave(int employeeId, LeaveType leaveType) {

        return leaveRequestRepository.getUtilizedLeave(employeeId, leaveType);
    }
    @Override
    public LeaveSummary getLeaveSummary(int employeeId) {
        LeaveSummary leaveSummary = new LeaveSummary();


        int maxSickLeave = 5;
        int maxPTO = 20;
        int maxMaternityLeave = 50;
        int maxPaternityLeave = 10;


        int sickUtilized = getUtilizedLeave(employeeId, LeaveRequest.LeaveType.SICK);
        int ptoUtilized = getUtilizedLeave(employeeId, LeaveRequest.LeaveType.PTO);
        int maternityUtilized = getUtilizedLeave(employeeId, LeaveRequest.LeaveType.MATERNITY);
        int paternityUtilized = getUtilizedLeave(employeeId, LeaveRequest.LeaveType.PATERNITY);


        leaveSummary.setSickAllocated(maxSickLeave);
        leaveSummary.setSickUtilized(sickUtilized);
        leaveSummary.setSickAvailable(maxSickLeave - sickUtilized);

        leaveSummary.setPtoAllocated(maxPTO);
        leaveSummary.setPtoUtilized(ptoUtilized);
        leaveSummary.setPtoAvailable(maxPTO - ptoUtilized);

        leaveSummary.setMaternityAllocated(maxMaternityLeave);
        leaveSummary.setMaternityUtilized(maternityUtilized);
        leaveSummary.setMaternityAvailable(maxMaternityLeave - maternityUtilized);

        leaveSummary.setPaternityAllocated(maxPaternityLeave);
        leaveSummary.setPaternityUtilized(paternityUtilized);
        leaveSummary.setPaternityAvailable(maxPaternityLeave - paternityUtilized);

        return leaveSummary;
    }
}
