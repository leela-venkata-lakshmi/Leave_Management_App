package com.wavemaker.leavemgmt.repository;

import com.wavemaker.leavemgmt.model.Employee;

public interface EmployeeRepository {
    Employee getEmployeeById(int empId);
}
