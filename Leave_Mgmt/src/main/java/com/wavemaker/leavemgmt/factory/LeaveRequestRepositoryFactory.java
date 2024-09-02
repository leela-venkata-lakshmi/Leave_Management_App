package com.wavemaker.leavemgmt.factory;

import com.wavemaker.leavemgmt.repository.Impl.LeaveRequestRepositoryImpl;
import com.wavemaker.leavemgmt.repository.LeaveRequestRepository;

import java.sql.SQLException;

public class LeaveRequestRepositoryFactory {
    private static LeaveRequestRepository leaveRequestRepository=null;
    public static LeaveRequestRepository getLeaveRequestRepositoryInstance() throws SQLException {
        if(leaveRequestRepository==null)
        {
            synchronized (LeaveRequestRepositoryFactory.class)
            {
                leaveRequestRepository=new LeaveRequestRepositoryImpl();
            }
        }
        return leaveRequestRepository;
    }
}
