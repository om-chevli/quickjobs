package com.example.quickjobs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import Models.JobDetails;

public class PostJobActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private RecyclerView jobIdRecycler;
    private RecyclerView.Adapter adapter;
    private Toolbar toolbar;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference usersDb;
    private DatabaseReference jobPostsDb;
    private ProgressBar progressBar;
    private TextView notFound;


    List<JobDetails> jobDetailsList = new ArrayList<JobDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        addBtn = findViewById(R.id.fab_add);//FAB
        progressBar = findViewById(R.id.your_jobs_progressbar);
        notFound = findViewById(R.id.no_post);


        //Toolbar
        toolbar = findViewById(R.id.toolbar_post_job);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Your Jobs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uId = mUser.getUid();

        jobPostsDb = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Job Posts");
        usersDb = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Users").child(uId);

        adapter = new YourJobsAdapter(jobDetailsList, this);

        jobIdRecycler = findViewById(R.id.job_post_id_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        jobIdRecycler.setHasFixedSize(true);
        jobIdRecycler.setLayoutManager(layoutManager);
        jobIdRecycler.setAdapter(adapter);

        getUserJobs();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddJobActivity.class));
            }
        });
    }


    private void getUserJobs() {
        List<String> jobIds = new ArrayList<String>();
        //Add User Posted Jobs
        usersDb.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    notFound.setVisibility(View.GONE);
                }
                snapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                    @Override
                    public void accept(DataSnapshot dataSnapshot) {
                        String id = dataSnapshot.getKey();
                        jobIds.add(id);
                        assert id != null;
                        jobPostsDb.child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String json = new Gson().toJson(snapshot.getValue());
                                JobDetails jobDetails = new Gson().fromJson(json, JobDetails.class);
                                jobDetailsList.add(jobDetails);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println(error.getCode());
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Canceled");
            }
        });
    }
}