package com.example.quickjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.JobDetails;

public class ViewJobsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView allJobsRecycler;

    //Firebase
    private DatabaseReference allJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("All Jobs");

        //DB
        allJobs = FirebaseDatabase.getInstance(getString(R.string.db_url)).getReference().child("Job Posts");
        allJobs.keepSynced(true);

        //Recycler View
        allJobsRecycler=findViewById(R.id.all_jobs_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        allJobsRecycler.setHasFixedSize(true);
        allJobsRecycler.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<JobDetails, PostJobActivity.MyViewHolder> adapter = new FirebaseRecyclerAdapter<JobDetails, PostJobActivity.MyViewHolder>(
                JobDetails.class,
                R.layout.job_post_item,
                PostJobActivity.MyViewHolder.class,
                allJobs
        ) {
            @Override
            protected void populateViewHolder(PostJobActivity.MyViewHolder viewHolder, JobDetails model, int position) {
                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDesc(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());
            }
        };

        allJobsRecycler.setAdapter(adapter);
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        View myView;
        public MyViewHolder(View itemView) {
            super(itemView);
            myView=itemView;
        }

        public void setJobTitle(String title){
            TextView jTitle = myView.findViewById(R.id.job_title_display);
            jTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView jDate= myView.findViewById(R.id.job_post_date);
            jDate.setText(date);
        }

        public void setJobDesc(String desc){
            TextView jDesc= myView.findViewById(R.id.job_desc_display);
            jDesc.setText(desc);
        }

        public void setJobSkills(String skills){
            TextView jSkills= myView.findViewById(R.id.job_skill_display);
            jSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView jSalary= myView.findViewById(R.id.job_salary_display);
            jSalary.setText(salary);
        }

    }
}