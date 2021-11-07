package com.example.quickjobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private  EditText password;
    private  EditText confirmPassword;

    private Button loginBtn;
    private Button registerBtn;

    //Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();
        registerUser();
    }

    private void registerUser(){
        email=findViewById(R.id.registration_email);
        password=findViewById(R.id.registration_password);
        confirmPassword=findViewById(R.id.confirm_password);

        loginBtn = findViewById(R.id.loginBackButton);
        registerBtn = findViewById(R.id.registerUserBtn);

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
                if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                    email.setError("Invalid Email!");
                }

                mAuth.createUserWithEmailAndPassword(emailTxt,passTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}