package com.wavemaker.leavemgmt.factory;

import com.wavemaker.leavemgmt.repository.EmployeeRepository;
import com.wavemaker.leavemgmt.repository.Impl.EmployeeRepositoryImpl;

import java.sql.SQLException;

public class EmployeeRepositoryFactory {
    private static EmployeeRepository employeeRepository=null;
    public static EmployeeRepository getEmployeeRepositoryInstance() throws SQLException {
        if(employeeRepository==null)
        {
            synchronized (EmployeeRepositoryFactory.class)
            {
                 if(employeeRepository==null)
                 {
                      employeeRepository=new EmployeeRepositoryImpl();
                 }
            }
        }
        return employeeRepository;
    }
}
