package com.wavemaker.leavemgmt.repository.Impl;

import com.wavemaker.leavemgmt.repository.LoginRepository;
import com.wavemaker.leavemgmt.util.DBConnectionUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepositoryImpl implements LoginRepository {

    private static final Logger logger = LoggerFactory.getLogger(LoginRepositoryImpl.class);
    private final Connection connection;
    private final static String QUERY="Select PASSWORD,EMP_ID from LOGIN where EMAIL=?";
    public LoginRepositoryImpl() throws SQLException {
        this.connection= DBConnectionUtil.getConnection();
    }

    public int isValidate(String email,String password){
        String storedHashedPassword=null;
        int empId;

        try(PreparedStatement stmt=connection.prepareStatement(QUERY))
        {
            stmt.setString(1,email);
           try(ResultSet rs= stmt.executeQuery()) {
               if (rs.next()) {
                    storedHashedPassword= rs.getString("PASSWORD");
                    empId=rs.getInt("EMP_ID");
                   if(storedHashedPassword!=null && BCrypt.checkpw(password,storedHashedPassword))
                   {
                       return empId;
                   }
                    logger.info("empid:{}",empId);
               }


           }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
