package com.example.quickjobs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import Models.JobDetails;

public class YourJobsAdapter extends RecyclerView.Adapter<YourJobsAdapter.ViewHolder> {

    private List<JobDetails> jobDetailsList;
    private Context context;
    private DatabaseReference jobPostsDb;
    private DatabaseReference usersDb;
    private FirebaseAuth mAuth;

    public YourJobsAdapter(List<JobDetails> jobDetailsList, Context context) {
        this.jobDetailsList = jobDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_job_post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourJobsAdapter.ViewHolder holder, int position) {
        JobDetails listItem = jobDetailsList.get(position);
        holder.job_title.setText(listItem.getTitle());
        holder.job_desc.setText(listItem.getDescription());
        holder.job_skill.setText(listItem.getSkills());
        holder.job_salary.setText(listItem.getSalary());
        holder.job_date.setText(listItem.getDate());
        holder.edit_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditJobActivity.class);
                intent.putExtra("name", listItem.getTitle());
                intent.putExtra("desc", listItem.getDescription());
                intent.putExtra("skills", listItem.getSkills());
                intent.putExtra("salary", listItem.getSalary());
                intent.putExtra("jobId", listItem.getId());
                intent.putExtra("email", listItem.getEmail());
                context.startActivity(intent);
            }
        });
        holder.delete_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(context, DeleteJobActivity.class).putExtra("jobId", listItem.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView job_title;
        public TextView job_desc;
        public TextView job_skill;
        public TextView job_salary;
        public TextView job_date;
        public Button edit_job;
        public Button delete_job;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            job_title = itemView.findViewById(R.id.job_title_display);
            job_desc = itemView.findViewById(R.id.job_desc_display);
            job_skill = itemView.findViewById(R.id.job_skill_display);
            job_salary = itemView.findViewById(R.id.job_salary_display);
            job_date = itemView.findViewById(R.id.job_post_date);
            edit_job = itemView.findViewById(R.id.edit_job_btn);
            delete_job = itemView.findViewById(R.id.delete_job_btn);
        }
    }
}
