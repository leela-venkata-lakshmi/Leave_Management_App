package com.wavemaker.leavemgmt.service.Impl;

import com.wavemaker.leavemgmt.factory.LoginRepositoryFactory;
import com.wavemaker.leavemgmt.repository.Impl.LoginRepositoryImpl;
import com.wavemaker.leavemgmt.repository.LoginRepository;
import com.wavemaker.leavemgmt.service.LoginService;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    LoginRepository loginRepository =new LoginRepositoryImpl();

    public LoginServiceImpl() throws SQLException {
        this.loginRepository= LoginRepositoryFactory.getLoginRepositoryInstance();
    }

    public int isValidate(String email,String password)
    {
       return loginRepository.isValidate(email,password);
    }
}
