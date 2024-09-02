package com.wavemaker.leavemgmt.factory;

import com.wavemaker.leavemgmt.repository.Impl.LoginRepositoryImpl;
import com.wavemaker.leavemgmt.repository.LoginRepository;

import java.sql.SQLException;

public class LoginRepositoryFactory {
    private static LoginRepository loginRepository = null;
    public static LoginRepository getLoginRepositoryInstance() throws SQLException {
        if(loginRepository==null)
        {
            synchronized (LoginRepositoryFactory.class)
            {
                 if(loginRepository==null)
                  {
                     loginRepository=new LoginRepositoryImpl();
                  }
            }
        }
        return loginRepository;
    }
}
