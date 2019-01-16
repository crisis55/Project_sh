package com.example.surreal.shotpingdef;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailtxt, nametxt, usernametxt, passwordtxt, confirmpasswordtxt;
    private ProgressBar progressBar;




    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailtxt = findViewById(R.id.email);
        nametxt =  findViewById(R.id.name_register);
        usernametxt = findViewById(R.id.username_register);
        passwordtxt = findViewById(R.id.password_register);
        confirmpasswordtxt = findViewById(R.id.password_confirmation);


        progressBar = (ProgressBar) findViewById(R.id.login_progress_register);
        

        auth = FirebaseAuth.getInstance();



        findViewById(R.id.register_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {

        }
    }

    private void registerUser() {
        final String name = nametxt.getText().toString().trim();
        final String email = emailtxt.getText().toString().trim();
        final String password = passwordtxt.getText().toString().trim();
        final String username = usernametxt.getText().toString().trim();
        final String confirmPassword = confirmpasswordtxt.getText().toString().trim();
        Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(username);
        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0) {
                    Toast.makeText(RegisterActivity.this, "Nombre de usuario en uso, escoge otro", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (name.isEmpty()) {
                        nametxt.setError(getString(R.string.input_error_name));
                        nametxt.requestFocus();
                        return;
                    }

                    if (email.isEmpty()) {
                        emailtxt.setError(getString(R.string.input_error_email));
                        emailtxt.requestFocus();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailtxt.setError(getString(R.string.input_error_email_invalid));
                        emailtxt.requestFocus();
                        return;
                    }

                    if (password.isEmpty()) {
                        passwordtxt.setError(getString(R.string.input_error_password));
                        passwordtxt.requestFocus();
                        return;
                    }

                    if (password.length() < 6) {
                        passwordtxt.setError(getString(R.string.input_error_password_length));
                        passwordtxt.requestFocus();
                        return;
                    }

                    if (username.isEmpty()) {
                        usernametxt.setError(getString(R.string.input_error_username));
                        usernametxt.requestFocus();
                        return;
                    }

                    if (confirmPassword.isEmpty()) {
                        confirmpasswordtxt.setError(getString(R.string.input_error_confirmPassword));
                        confirmpasswordtxt.requestFocus();
                        return;
                    }

                    if (confirmPassword.equals(password)) {

                    } else {
                        confirmpasswordtxt.setError (getString(R.string.input_error_comparison));
                        confirmpasswordtxt.requestFocus();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        User user = new User(
                                                name,
                                                email,
                                                username
                                        );

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, getString(R.string.registration_failure), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                registerUser();
                break;
        }
    }
}
