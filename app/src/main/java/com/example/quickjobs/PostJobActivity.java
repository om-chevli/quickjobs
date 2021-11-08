package com.example.quickjobs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

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
    private Toolbar toolbar;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference usersDb;
    private DatabaseReference jobPostsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        addBtn = findViewById(R.id.fab_add);

        toolbar = findViewById(R.id.toolbar_post_job);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Your Jobs");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String uId = mUser.getUid();

        jobPostsDb = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Job Posts");
        usersDb = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Users").child(uId);

        List<String> jobIds = new ArrayList<String>();
        List<JobDetails> jobDetailsList = new ArrayList<JobDetails>();


        usersDb.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                    @Override
                    public void accept(DataSnapshot dataSnapshot) {
                        String id = dataSnapshot.getKey();
                        jobIds.add(id);
                        assert id != null;
                        System.out.println("jhbsfd:- "+id);
                        jobPostsDb.child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String json = new Gson().toJson(snapshot.getValue());
                                JobDetails jobDetails= new Gson().fromJson(json, JobDetails.class);
                                jobDetailsList.add(jobDetails);
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




        jobIdRecycler = findViewById(R.id.job_post_id_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        jobIdRecycler.setHasFixedSize(true);
        jobIdRecycler.setLayoutManager(layoutManager);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FabAddJobActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseRecyclerAdapter<JobDetails,MyViewHolder> adapter = new FirebaseRecyclerAdapter<JobDetails, MyViewHolder>(
//                JobDetails.class,
//                R.layout.job_post_item,
//                MyViewHolder.class,
//                jobPostDb
//        ) {
//            @Override
//            protected void populateViewHolder(MyViewHolder viewHolder, JobDetails model, int position) {
//                viewHolder.setJobTitle(model.getTitle());
//                viewHolder.setJobDate(model.getDate());
//                viewHolder.setJobDesc(model.getDescription());
//                viewHolder.setJobSkills(model.getSkills());
//                viewHolder.setJobSalary(model.getSalary());
//            }
//        };
//
//        jobIdRecycler.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View myView;

        public MyViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
        }

        public void setJobTitle(String title) {
            TextView jTitle = myView.findViewById(R.id.job_title_display);
            jTitle.setText(title);
        }

        public void setJobDate(String date) {
            TextView jDate = myView.findViewById(R.id.job_post_date);
            jDate.setText(date);
        }

        public void setJobDesc(String desc) {
            TextView jDesc = myView.findViewById(R.id.job_desc_display);
            jDesc.setText(desc);
        }

        public void setJobSkills(String skills) {
            TextView jSkills = myView.findViewById(R.id.job_skill_display);
            jSkills.setText(skills);
        }

        public void setJobSalary(String salary) {
            TextView jSalary = myView.findViewById(R.id.job_salary_display);
            jSalary.setText(salary);
        }

    }
}