package com.example.quickjobs;

import android.content.Context;
import android.os.health.ProcessHealthStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Models.JobDetails;

public class YourJobsAdapter extends RecyclerView.Adapter<YourJobsAdapter.ViewHolder> {

    private List<JobDetails> jobDetailsList;
    private Context context;

    public YourJobsAdapter(List<JobDetails> jobDetailsList, Context context) {
        this.jobDetailsList = jobDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourJobsAdapter.ViewHolder holder, int position) {
        JobDetails listItem = jobDetailsList.get(position);
        holder.job_title.setText(listItem.getTitle());
        holder.job_desc.setText(listItem.getDescription());
        holder.job_skill.setText(listItem.getSkills());
        holder.job_salary.setText(listItem.getSalary());
    }

    @Override
    public int getItemCount() {
        return jobDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView job_title;
        public TextView job_desc;
        public TextView job_skill;
        public TextView job_salary;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            job_title = itemView.findViewById(R.id.job_title_display);
            job_desc = itemView.findViewById(R.id.job_desc_display);
            job_skill = itemView.findViewById(R.id.job_skill_display);
            job_salary = itemView.findViewById(R.id.job_salary_display);
        }
    }
}
