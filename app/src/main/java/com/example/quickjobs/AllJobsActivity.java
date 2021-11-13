package com.example.quickjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private TextView notFound;
    private Button applyForJobBtn;
    private ProgressBar progressBar;

    //Firebase
    private DatabaseReference allJobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs);
        toolbar = findViewById(R.id.view_jobs_toolbar);
        progressBar = findViewById(R.id.all_jobs_progressbar);
        notFound = findViewById(R.id.no_posts_available);
        applyForJobBtn = findViewById(R.id.applyJobBtn);

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
                if (snapshot.getValue() != null) {
                    notFound.setVisibility(View.GONE);
                }

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
                R.layout.all_job_post_item,
                CustomViewHolder.class,
                allJobs
        ) {
            String jobTitle;
            String receiverEmail;

            @Override
            protected void populateViewHolder(CustomViewHolder viewHolder, JobDetails model, int position) {
                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDesc(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());
                applyForJobBtn = viewHolder.myView.findViewById(R.id.applyJobBtn);
                applyForJobBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String subjectLine = "Apply For " + jobTitle + " position";
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL, new String[]{receiverEmail});
                        i.putExtra(Intent.EXTRA_SUBJECT, subjectLine);
                        i.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body));
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        allJobsRecycler.setAdapter(adapter);
    }
}