package com.example.quickjobs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import Models.JobDetails;

public class EditJobActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //Input Fields
    private EditText job_title;
    private EditText job_desc;
    private EditText job_skills;
    private EditText job_salary;
    private Button updateJobBtn;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference jobPostsDb;
    private DatabaseReference usersDb;

    public String title;
    public String desc;
    public String skills;
    public String salary;
    public String jobId;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        toolbar = findViewById(R.id.toolbar_edit_job);
        updateJobBtn = findViewById(R.id.updateJobPostBtn);

        //ToolBar/Appbar Settings
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Update Job Post");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser(); //get current user
        assert mUser != null; //assertion for null check
        String uId = mUser.getUid(); //user firebase id
        jobPostsDb = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Job Posts");
        usersDb = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Users").child(uId);
        getDetailsFromIntent();
        setDetailsFromIntent();
        updateJobOnServer();
    }

    private void getDetailsFromIntent() {
        job_title = findViewById(R.id.job_title_edit);
        job_desc = findViewById(R.id.job_desc_edit);
        job_skills = findViewById(R.id.job_skill_edit);
        job_salary = findViewById(R.id.job_salary_edit);

        Intent intent = getIntent();

        //Get
        title = intent.getStringExtra("name");
        desc = intent.getStringExtra("desc");
        skills = intent.getStringExtra("skills");
        salary = intent.getStringExtra("salary");
        jobId = intent.getStringExtra("jobId");
        email = intent.getStringExtra("email");
    }

    private void setDetailsFromIntent() {
        job_title.setText(title);
        job_desc.setText(desc);
        job_skills.setText(skills);
        job_salary.setText(salary);
    }

    private void updateJobOnServer() {

        updateJobBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(title)) {
                    job_title.setError("Required Field!");
                    return;
                }
                if (TextUtils.isEmpty(desc)) {
                    job_desc.setError("Required Field!");
                    return;
                }
                if (TextUtils.isEmpty(skills)) {
                    job_skills.setError("Required Field!");
                    return;
                }
                if (TextUtils.isEmpty(salary)) {
                    job_salary.setError("Required Field!");
                    return;
                }

                String date = DateFormat.getDateInstance().format(new Date());
                title = job_title.getText().toString().trim();
                desc = job_desc.getText().toString().trim();
                skills = job_skills.getText().toString().trim();
                salary = job_salary.getText().toString().trim();

                Map<String, Object> details = new JobDetails(title, desc, skills, salary, jobId, date, email).toMap();
                jobPostsDb.child(jobId).updateChildren(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), PostJobActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}