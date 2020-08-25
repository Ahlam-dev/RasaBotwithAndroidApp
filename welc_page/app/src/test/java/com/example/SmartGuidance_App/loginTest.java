package com.example.SmartGuidance_App;

import com.example.SmartGuidance_App.login_siginup.login;

import org.junit.Test;


import static org.junit.Assert.*;

public class loginTest  {

    @Test
    public void userSign() {
        try{
        login log = new login();
    assertEquals(true,log.userSign());
    }catch (Exception e){}

    }
}

