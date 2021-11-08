package com.example.quickjobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostJobActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private RecyclerView jobIdRecycler;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference jobPostDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        addBtn=findViewById(R.id.fab_add);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        jobPostDb = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        jobIdRecycler=findViewById(R.id.job_post_id_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        jobIdRecycler.setHasFixedSize(true);
        jobIdRecycler.setLayoutManager(layoutManager);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FabAddJobActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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