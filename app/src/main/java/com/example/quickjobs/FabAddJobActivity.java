package com.example.quickjobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import Models.JobDetails;

public class FabAddJobActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //Input Fields
    private EditText job_title;
    private EditText job_desc;
    private EditText job_skills;
    private EditText job_salary;

    //Post Button
    private Button postJobBtn;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_add_job);
        toolbar=findViewById(R.id.toolbar_fab_add_job);

        //ToolBar/Appbar Settings
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Post A Job");
        //Firebase
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser(); //get current user
        assert mUser != null; //assertion for null check
        String uId = mUser.getUid(); //user firebase id
        mJobPost = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Job Post").child(uId);

        postJobToServer();
    }

    //Posts Job to firebase database
    private void postJobToServer(){
        job_title=findViewById(R.id.job_title);
        job_desc=findViewById(R.id.job_desp);
        job_skills=findViewById(R.id.job_skill);
        job_salary =findViewById(R.id.job_salary);
        postJobBtn=findViewById(R.id.jobPostBtn);

        postJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = job_title.getText().toString().trim();
                String desc = job_desc.getText().toString().trim();
                String skills = job_skills.getText().toString().trim();
                String salary = job_salary.getText().toString().trim();

                if(TextUtils.isEmpty(title)){
                    job_title.setError("Required Field!");
                    return;
                }
                if(TextUtils.isEmpty(desc)){
                    job_desc.setError("Required Field!");
                    return;
                }
                if(TextUtils.isEmpty(skills)){
                    job_skills.setError("Required Field!");
                    return;
                }
                if(TextUtils.isEmpty(salary)){
                    job_salary.setError("Required Field!");
                    return;
                }

                String id = mJobPost.push().getKey(); //db key
                String date = DateFormat.getDateInstance().format(new Date());
                JobDetails details = new JobDetails(title,desc,skills,salary,id,date);
                assert id != null;
                mJobPost.child(id).setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), PostJobActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });//posting JobDetail object


            }
        });

    }
}