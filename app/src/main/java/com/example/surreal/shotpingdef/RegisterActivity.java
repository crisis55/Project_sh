package com.example.surreal.shotpingdef;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailtxt, nametxt, usernametxt, passwordtxt, confirmpasswordtxt;
    private ProgressBar progressBar;
    private Button register;




    private FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailtxt = findViewById(R.id.email);
        nametxt =  findViewById(R.id.name_register);
        usernametxt = findViewById(R.id.username_register);
        passwordtxt = findViewById(R.id.password_register);
        confirmpasswordtxt = findViewById(R.id.password_confirmation);
        register = findViewById(R.id.register_button);


        progressBar = (ProgressBar) findViewById(R.id.login_progress_register);


        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                            if (name.isEmpty()) {
                                nametxt.setError(getString(R.string.input_error_name));
                                nametxt.requestFocus();
                                return;
                            }

                            if (username.isEmpty()) {
                                usernametxt.setError(getString(R.string.input_error_username));
                                usernametxt.requestFocus();
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

                            if (confirmPassword.isEmpty()) {
                                confirmpasswordtxt.setError(getString(R.string.input_error_confirmPassword));
                                confirmpasswordtxt.requestFocus();
                                return;
                            }

                            if (!confirmPassword.equals(password)) {
                                confirmpasswordtxt.setError (getString(R.string.input_error_comparison));
                                confirmpasswordtxt.requestFocus();
                                return;
                            }


                            progressBar.setVisibility(View.VISIBLE);
                            auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                FirebaseUser users = auth.getCurrentUser();
                                                String userid = users.getUid();

                                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("id", userid);
                                                hashMap.put("username", username.toLowerCase());
                                                hashMap.put("name", name);
                                                hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/project-sh-b2ac0.appspot.com/o/placeholder-profile-sq.jpg?alt=media&token=1430db0d-6842-484b-b348-d7a44076c7a5");

                                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                                            finish();
                                                        }
                                                    }

                                                });
                                            } else {

                                                Toast.makeText(RegisterActivity.this, "No se ha podido registrar", Toast.LENGTH_SHORT).show();
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
        });

    }
}
