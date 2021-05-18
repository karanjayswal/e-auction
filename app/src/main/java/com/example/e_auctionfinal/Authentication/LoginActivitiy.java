package com.example.e_auctionfinal.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_auctionfinal.BottomNavigation.Buyer;
import com.example.e_auctionfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivitiy extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseAuth auth;
    ImageView imageView;
    FirebaseDatabase database;
    int rand_number;
    String username;
    EditText etEmail,etPassword,etMobile,etOTP,etUsername;
    Button btnSignUp,btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activitiy);
        progressDialog = new ProgressDialog(LoginActivitiy.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login in account");
        etEmail = findViewById(R.id.etEmailSignIn);
        imageView = findViewById(R.id.ivLogin);

        etUsername= findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPasswordSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.button8);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
//                Toast.makeText(Login.this, etEmail.getText().toString()+etPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                auth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if (auth.getCurrentUser().isEmailVerified()) {

//                                Toast.makeText(Login.this, "Name: "+uname, Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child(etUsername.getText().toString()).exists())
                                        {   username = snapshot.child(etUsername.getText().toString()).child("userName").getValue(String.class);
                                            if (username.equals(etUsername.getText().toString().trim())) {
                                                Intent intent = new Intent(LoginActivitiy.this, Buyer.class);
                                                intent.putExtra("username", etUsername.getText().toString());
                                                intent.putExtra("uid", task.getResult().getUser().getUid());
                                                progressDialog.dismiss();
                                                startActivity(intent);

                                            }}
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivitiy.this, "Check the username", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivitiy.this,  error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else
                            {   progressDialog.dismiss();
                                Toast.makeText(LoginActivitiy.this, "Please First Verify mail", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivitiy.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivitiy.this, SignUp.class);
                startActivity(intent);
            }
        });


    }


    public void ShowPassword(View view) {
        if(etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
        {etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.eye);
        }
        else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.drawable.hide);
        }
    }

    public void ForgotPassword(View view) {
        Intent intent = new Intent(LoginActivitiy.this, ForgotPassword.class);
        startActivity(intent);
    }
}
