package com.example.SmartGuidance_App.login_siginup;

public class User {




    String Email;
    long createdAt;

    public User (){};
    public User(String email,long createdAt){

        this.Email=email;
        this.createdAt=createdAt;
    }




    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

}
