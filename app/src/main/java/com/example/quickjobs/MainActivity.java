package com.example.quickjobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private  EditText password;

    private Button loginBtn;
    private Button registerBtn;

    //Authentication
    private FirebaseAuth mAuth;

    //Progress Dialog
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mDialog= new ProgressDialog(this);
        loginUser();
    }

    private void loginUser(){
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);

        loginBtn = findViewById(R.id.loginUserBtn);
        registerBtn = findViewById(R.id.registerBackBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString().trim();
                String passTxt = password.getText().toString().trim();
                if(TextUtils.isEmpty(emailTxt)){
                    email.setError("Required Field!");
                }
                if(TextUtils.isEmpty(passTxt)){
                    password.setError("Required Field!");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                    email.setError("Invalid Email!");
                }

                mDialog.setMessage("Logging You In...");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(emailTxt,passTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
    }
}