package com.wavemaker.leavemgmt.repository;

import com.wavemaker.leavemgmt.exception.EmployeeNotFoundException;
import com.wavemaker.leavemgmt.model.Employee;

public interface EmployeeRepository {
    /**
     * returns an employee for the given employee id
     * @param empId
     * @return
     * @throws EmployeeNotFoundException if given is not present in db
     */
    Employee getEmployeeById(int empId) throws EmployeeNotFoundException;
}
