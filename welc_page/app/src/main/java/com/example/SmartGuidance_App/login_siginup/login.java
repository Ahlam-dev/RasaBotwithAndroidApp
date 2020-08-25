package com.example.SmartGuidance_App.login_siginup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SmartGuidance_App.R;
import com.example.SmartGuidance_App.chat_classes.chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    EditText Email, Password;
    Button LogInButton, backButton;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String email, password;
    ProgressDialog dialog;
    public static final String userEmail="";

    public static final String TAG="LOGIN";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        LogInButton = (Button) findViewById(R.id.button3);
        backButton = (Button) findViewById(R.id.button9);




        Email = (EditText) findViewById(R.id.siemail);
        Password = (EditText) findViewById(R.id.sipass);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();



        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(login.this, chat.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Log.d(TAG,"AuthStateChanged:Logout");
                }

            }
        };

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userSign();


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent2 = new Intent(login.this, loginOptions.class);

                startActivity(intent2);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }



    public boolean userSign() {
        final ArrayList<Boolean> res = new ArrayList<>();
        res.add(true);
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (email.equals("")) {
            Toast.makeText(login.this, "أدخل البريد الإلكتروني الصحيح", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(login.this, "أدخل الرمز السري الصحيح", Toast.LENGTH_SHORT).show();
            return false;
        }
        dialog.setMessage("جاري التحقق...");
        dialog.setIndeterminate(true);
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();

                    Toast.makeText(login.this, "فشل عملية تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    res.add(false);
                } else {
                    dialog.dismiss();


                    Intent intent = new Intent(login.this, chat.class);
                    startActivity(intent);
                    res.add(true);

                }
            }
        });

        int x = (res.size() - 1);
        return (res.get(x == -1 ? 0 : (res.size() - 1)));
    }




}
