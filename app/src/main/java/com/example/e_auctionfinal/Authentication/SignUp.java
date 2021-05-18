package com.example.e_auctionfinal.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_auctionfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseDatabase database;
    Boolean ret = false;
    Button btnSignUp;
    ImageView imageView;
    ProgressDialog progressDialog;
    Button tvSignIn;
    TextView tvVerifySignUp;
    DatabaseReference reference;
    Boolean already = false;
    EditText etMail, etUsername, etPassword;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etMail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        imageView = findViewById(R.id.imageView);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        tvSignIn = findViewById(R.id.btnSignIn);
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are Creating the Account");
        reference = database.getReference().child("Users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                DatabaseReference refer = database.getReference().child("Users");
                refer.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.child(etUsername.getText().toString().trim()).exists()) {
                            auth.createUserWithEmailAndPassword(etMail.getText().toString().trim(),
                                    etPassword.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful())
                                            {
                                                auth.getCurrentUser().sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressDialog.dismiss();
                                                                if (task.isSuccessful()) {
                                                                    User user = new User();
                                                                    user.setUserName(etUsername.getText().toString().trim());
                                                                    user.setMail(etMail.getText().toString().trim());
                                                                    user.setPassword(etPassword.getText().toString().trim());
                                                                    database.getReference().child("Users").child(etUsername.getText().toString().trim()).setValue(user);
                                                                    Toast.makeText(SignUp.this, "Successfully Created \nPlease Check your mail for verification ", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(SignUp.this, LoginActivitiy.class);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(SignUp.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            }
                                            else {
                                                progressDialog.dismiss();
                                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });



                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Username is taken ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this,  error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });
        tvSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(SignUp.this, LoginActivitiy.class);
                startActivity(intent);
            }
        });

    }

    public void ShowPassword(View view) {
        if (etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.eye);
        } else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.hide);
        }
    }
}