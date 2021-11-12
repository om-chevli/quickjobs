package com.example.quickjobs;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    View myView;

    public CustomViewHolder(View itemView) {
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

