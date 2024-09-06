package com.wavemaker.leavemgmt.repository.Impl;

import com.wavemaker.leavemgmt.exception.EmployeeNotFoundException;
import com.wavemaker.leavemgmt.model.Employee;
import com.wavemaker.leavemgmt.repository.EmployeeRepository;
import com.wavemaker.leavemgmt.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final static String SELECT_QUERY= "SELECT * FROM EMPLOYEE WHERE EMP_ID = ?";
    private final Connection connection;

    public EmployeeRepositoryImpl() throws SQLException {
        this.connection = DBConnectionUtil.getConnection();
    }

    @Override
    public Employee getEmployeeById(int empId) throws EmployeeNotFoundException {

        try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("EMP_ID"));
                employee.setFirstName(rs.getString("FIRST_NAME"));
                employee.setLastName(rs.getString("LAST_NAME"));
                employee.setGender(Employee.Gender.valueOf(rs.getString("GENDER")));
                employee.setDateOfBirth(rs.getDate("DOB").toLocalDate());
                employee.setPhoneNumber(rs.getString("PHONE_NUM"));

                employee.setRole(rs.getString("ROLE"));
                return employee;
            } else {
                throw new EmployeeNotFoundException("employee not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to get employee details", e);
        }
    }
}
