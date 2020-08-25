package com.example.SmartGuidance_App.login_siginup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.SmartGuidance_App.R;

public class loginOptions extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);


        button = (Button) findViewById(R.id.button5);
        button2=(Button)findViewById(R.id.button6);

        button2.setOnClickListener(this);
        button.setOnClickListener(this);



    }


    public void onClick(View v) {

        if(v.getId()==R.id.button5){

            Intent intent=new Intent(loginOptions.this, login.class);

            startActivity(intent);

        }
        else if (v.getId()==R.id.button6)
        {
            Intent in=new Intent(loginOptions.this, signupacivity.class);
            startActivity(in);
        }
    }
}


