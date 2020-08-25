package com.example.SmartGuidance_App;
import com.example.SmartGuidance_App.login_siginup.signupacivity;

import org.junit.Test;
import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;

public class signupacivityTest extends TestCase {

    @Test
    public void testSignup() {
        String username= "";
        String password = "";
        try{
            signupacivity sign = new signupacivity();
            assertEquals(true,sign.signup(username,password));
        }catch (Exception e){}
    }
}

