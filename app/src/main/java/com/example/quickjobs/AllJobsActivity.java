package com.example.quickjobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Models.JobDetails;

public class AllJobsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView allJobsRecycler;

    //Firebase
    private DatabaseReference allJobs;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs);
        toolbar=findViewById(R.id.view_jobs_toolbar);
        progressBar=findViewById(R.id.all_jobs_progressbar);

        //Toolbar
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("All Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //DB
        allJobs = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Job Posts");
        allJobs.keepSynced(true);

        allJobs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Recycler View
        allJobsRecycler = findViewById(R.id.all_jobs_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        allJobsRecycler.setHasFixedSize(true);
        allJobsRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<JobDetails, CustomViewHolder> adapter = new FirebaseRecyclerAdapter<JobDetails, CustomViewHolder>(
                JobDetails.class,
                R.layout.job_post_item,
                CustomViewHolder.class,
                allJobs
        ) {
            @Override
            protected void populateViewHolder(CustomViewHolder viewHolder, JobDetails model, int position) {
                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDesc(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());
            }
        };
        allJobsRecycler.setAdapter(adapter);
    }
}