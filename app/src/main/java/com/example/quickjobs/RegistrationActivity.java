package com.example.quickjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private  EditText password;
    private  EditText confirmPassword;

    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registerUser();
    }

    private void registerUser(){
        email=findViewById(R.id.registration_email);
        password=findViewById(R.id.registration_password);
        confirmPassword=findViewById(R.id.confirm_password);

        loginBtn = findViewById(R.id.loginBackButton);
        registerBtn = findViewById(R.id.registerUserBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString().trim();
                String passTxt = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(emailTxt)){
                    email.setError("Required Field!");
                }
                if(TextUtils.isEmpty(passTxt)){
                    password.setError("Required Field!");
                }
                if(!passTxt.equals(confirmPass)){
                    confirmPassword.setError("Password Mismatch!");
                }
            }
        });
    }
}