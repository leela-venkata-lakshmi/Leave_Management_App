package com.wavemaker.leavemgmt.service.Impl;

import com.wavemaker.leavemgmt.exception.EmployeeNotFoundException;
import com.wavemaker.leavemgmt.factory.EmployeeRepositoryFactory;
import com.wavemaker.leavemgmt.model.Employee;
import com.wavemaker.leavemgmt.repository.EmployeeRepository;
import com.wavemaker.leavemgmt.repository.Impl.EmployeeRepositoryImpl;
import com.wavemaker.leavemgmt.service.EmployeeService;

import java.sql.SQLException;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(); // Implement this repository to fetch employee data

    public EmployeeServiceImpl() throws SQLException {
        this.employeeRepository= EmployeeRepositoryFactory.getEmployeeRepositoryInstance();
    }

    @Override
    public Employee getEmployeeById(int empId) throws EmployeeNotFoundException {
        return employeeRepository.getEmployeeById(empId);
    }
}
