package com.example.quickjobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FabAddJobActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //Input Fields
    private EditText job_title;
    private EditText job_desc;
    private EditText job_skills;
    private EditText job_salary;

    //Post Button
    private Button postJobBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_add_job);
        toolbar=findViewById(R.id.toolbar_fab_add_job);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Post A Job");
        postJobToServer();
    }

    private void postJobToServer(){
        job_title=findViewById(R.id.job_title);
        job_desc=findViewById(R.id.job_desp);
        job_skills=findViewById(R.id.job_skill);
        job_salary =findViewById(R.id.job_salary);
        postJobBtn=findViewById(R.id.postJobBtn);

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
                }


            }
        });

    }
}