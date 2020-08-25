package com.example.SmartGuidance_App;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity ;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import com.example.SmartGuidance_App.login_siginup.loginOptions;
import com.example.SmartGuidance_App.welcom_pages.intro1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button ,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        button = (Button) findViewById(R.id.button0);
        button2=(Button) findViewById(R.id.button2);



    button2.setOnClickListener(this);
        button.setOnClickListener(this);



}


    public void onClick(View v) {

        if(v.getId()==R.id.button0){

            Intent intent=new Intent(MainActivity.this, loginOptions.class);

            startActivity(intent);

        }
        else if (v.getId()==R.id.button2)
        {
            Intent in=new Intent(MainActivity.this, intro1.class);
            startActivity(in);
        }
    }




    }

