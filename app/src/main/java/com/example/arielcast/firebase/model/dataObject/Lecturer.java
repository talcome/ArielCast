package com.example.arielcast.firebase.model.dataObject;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.arielcast.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Lecturer {
    String lecturerId;
    String email;
    String password;
    String fullname;
    String phone;


    public Lecturer(String lecturerId, String email, String password, String fullname, String phone) {
        this.lecturerId = lecturerId;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;

    }

    public Lecturer() {

    }

    public <T> Lecturer(Lecturer lec) {
        this.lecturerId=lec.getLecturerId();
        this.email=lec.getEmail();
        this.password=lec.getPassword();
        this.fullname=lec.getFullname();
        this.phone=lec.getPhone();
    }

    public void addCourse(Context t, ProgressBar progressBar, View v, EditText courseName, EditText startDate, EditText endDate) {

        String uniqueID = UUID.randomUUID().toString();

        Course course = new Course(uniqueID, courseName.getText().toString().trim(), lecturerId, startDate.getText().toString().trim(),
                endDate.getText().toString().trim(),"");
        FirebaseDatabase.getInstance().getReference().child("Courses").child(uniqueID).setValue(course)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(t,
                                    "your new course added successfully!",
                                    Toast.LENGTH_LONG).show();

                            // back to Main Screen - lecturer activity
                            Intent intent = new Intent(t, MainActivity.class);
                            intent.putExtra("Email", email);
                            intent.putExtra("ID", lecturerId);
                            t.startActivity(intent);
                        } else {
                            Toast.makeText(t, "Failed to add this course! try again", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                });
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerId() {
        return lecturerId;
    }


    public static int generatRandomPositiveNegitiveValue(int max, int min) {
        //Random rand = new Random();
        int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
        return ii;
    }
}
