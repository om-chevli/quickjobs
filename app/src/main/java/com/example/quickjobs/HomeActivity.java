package com.example.quickjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button postAJobBtn;
    private Button seeAllJobBtn;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar=findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Quick Jobs Portal");


        postAJobBtn=findViewById(R.id.postJobBtn);
        seeAllJobBtn=findViewById(R.id.allJobsBtn);

        postAJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PostJobActivity.class));
            }
        });

        seeAllJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AllJobsActivity.class));
            }
        });
    }
}