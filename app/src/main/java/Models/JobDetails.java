package Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class JobDetails {
    String title;
    String description;
    String skills;
    String salary;
    String id;
    String date;
    String email;

    public JobDetails() {
    }

    public JobDetails(String title, String description, String skills, String salary, String id, String date, String email) {
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.salary = salary;
        this.id = id;
        this.date = date;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("skills", skills);
        result.put("salary", salary);
        result.put("id", id);
        result.put("date", date);
        result.put("email", email);
        return result;
    }
}
