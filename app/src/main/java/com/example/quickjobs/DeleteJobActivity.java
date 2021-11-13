package com.example.quickjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteJobActivity extends AppCompatActivity {

    private Button del;
    private Button cancel;
    private FirebaseAuth mAuth;
    private DatabaseReference jobPostsDb;
    private DatabaseReference usersDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_job);
        del = findViewById(R.id.new_del_btn);
        cancel = findViewById(R.id.cancel_it);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                assert mUser != null;
                String uID = mUser.getUid();
                Intent intent = getIntent();
                String jobId = intent.getStringExtra("jobId");
                jobPostsDb = FirebaseDatabase.getInstance("https://quick-jobs-android-native-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Job Posts");
                usersDb = FirebaseDatabase.getInstance("https://quick-jobs-android-native-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");
                jobPostsDb.child(jobId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        usersDb.child(uID).child(jobId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getApplicationContext(), PostJobActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        });
                    }
                });

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}