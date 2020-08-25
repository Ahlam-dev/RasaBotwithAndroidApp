package com.example.SmartGuidance_App;

import com.example.SmartGuidance_App.chat_classes.chat;

import org.junit.Test;

import static org.junit.Assert.*;

public class chatTest {

    @Test
    public void isLastVisible() {
        try {
            chat c = new chat();
            assertEquals(false, c.isLastVisible());
        } catch (Exception e) {


        }
    }
}

