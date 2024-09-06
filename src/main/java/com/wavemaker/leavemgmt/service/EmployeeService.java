package com.wavemaker.leavemgmt.service;

import com.wavemaker.leavemgmt.exception.EmployeeNotFoundException;
import com.wavemaker.leavemgmt.model.Employee;

public interface EmployeeService {
    Employee getEmployeeById(int empId) throws EmployeeNotFoundException;
}
