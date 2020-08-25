package com.example.SmartGuidance_App.welcom_pages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.SmartGuidance_App.MainActivity;
import com.example.SmartGuidance_App.R;

public class intro1 extends AppCompatActivity implements View.OnClickListener {
    private Button button,button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);






        button = (Button) findViewById(R.id.button3);
        button2=(Button) findViewById(R.id.button7);



        button2.setOnClickListener(this);
        button.setOnClickListener(this);



    }


    public void onClick(View v) {

        if(v.getId()==R.id.button3){

            Intent intent=new Intent(intro1.this, intro2.class);

            startActivity(intent);

        }
        else if (v.getId()==R.id.button7)
        {
            Intent in=new Intent(intro1.this, MainActivity.class);
            startActivity(in);
        }
    }

}
