package com.example.SmartGuidance_App.login_siginup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;


public class signupacivity extends AppCompatActivity {

    private Button Registerbutton;
    String password,email;
    private EditText reEmail, rePass;

    private FirebaseAuth authinticationf;
    DatabaseReference dbref;
    FirebaseAuth.AuthStateListener mAuthListner;



    public static final String TAG="LOGIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupacivity);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        authinticationf=FirebaseAuth.getInstance();


        reEmail=findViewById(R.id.etEmail);
        rePass=findViewById(R.id.etPass);

        Registerbutton=findViewById(R.id.btRegister);


        dbref = FirebaseDatabase.getInstance().getReference().child("Users");


        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email=reEmail.getText().toString();
                password=rePass.getText().toString();


                signup(email,password);




            }



        });

    }



    public boolean signup(String email,String password){

        final ArrayList<Boolean> res = new ArrayList<>();
        res.add(true);
        if (email.equals("")){
            Toast.makeText(signupacivity.this, "الرجاء إدخال البريد الإلكتروني", Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.equals("")){
            Toast.makeText(signupacivity.this, "الرجاء إدخال الرمز السري", Toast.LENGTH_SHORT).show();
            return true;}

        authinticationf.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    Toast.makeText(signupacivity.this,"تم إنشاء الحساب",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signupacivity.this, chat.class);
                    startActivity(intent);
                    res.add(false);
                }
                else{



                    Log.e(TAG,task.getException().getMessage());
                    Toast.makeText(signupacivity.this,"حدث خطأ ما،أعد المحاولة",Toast.LENGTH_SHORT).show();
                    res.add(true);
                }
            }
        });
        int x = (res.size() - 1);
        return (res.get(x == -1 ? 0 : (res.size() - 1)));
    }


    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
        User user = BuildNewuser();
        dbref.child(uid).setValue(user);
    }
    private User BuildNewuser(){
        return new User(

                getUserEmail(),
                new Date().getTime()
        );
    }



    public String getUserEmail() {
        return email;
    }






}
