package com.example.arielcast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arielcast.firebase.model.dataObject.Lecturer;
import com.example.arielcast.firebase.model.dataObject.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextPhone;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        TextView banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        TextView registerUser = (Button) findViewById(R.id.register);
        registerUser.setOnClickListener(this);

        editTextFullName = findViewById(R.id.fullName);
        editTextEmail = findViewById(R.id.emailAdress);
        editTextPassword = findViewById(R.id.password);
        editTextPhone = findViewById(R.id.phone);
        progressBar = findViewById(R.id.progressBar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.banner)
            startActivity(new Intent(this, LoginActivity.class));
        else if (v.getId() == R.id.register) {
            cb = findViewById(R.id.cbLecturer);
            if (cb.isChecked())
                registerLecturer(v);
            else
                registerUser(v);
        }
    }

    private void registerUser(View v) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (!phone.startsWith("05")) {
            editTextPhone.setError("Please provide valid phone");
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            editTextPhone.setError("Please provide valid phone");
            editTextPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // ADD STUDENT TO DATABASE
                            //FirebaseDBStudents st=new FirebaseDBStudents();
                            String sId=Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            Student user = new Student(sId,email, fullName, phone, password);
                            // st.addStudentToDB(user);

                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(sId)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this,
                                                "user has been registered successfully!",
                                                Toast.LENGTH_LONG).show();

                                        // back to Main Screen - login
                                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else
                                        Toast.makeText(RegisterUser.this,
                                                "Failed to register! try again",
                                                Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            Toast.makeText(RegisterUser.this,
                                    "Failed to register! try again",
                                    Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void registerLecturer(View v) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }


        if (!phone.startsWith("05")) {
            editTextPhone.setError("Please provide valid phone");
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            editTextPhone.setError("Please provide valid phone");
            editTextPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // ADD STUDENT TO DATABASE
                            String LecId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            Lecturer user = new Lecturer(LecId, email, password, fullName, phone);

                            FirebaseDatabase.getInstance().getReference("Lecturers").child(LecId)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this,
                                                "user has been registered successfully!",
                                                Toast.LENGTH_LONG).show();

                                        // back to Main Screen - login
                                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterUser.this,
                                                "Failed to register! try again",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            Toast.makeText(RegisterUser.this,
                                    "Failed to register! try again",
                                    Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
