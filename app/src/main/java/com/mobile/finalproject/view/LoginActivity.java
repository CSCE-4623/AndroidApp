package com.mobile.finalproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.mobile.finalproject.HomeActivity;
import com.mobile.finalproject.Prevalent.Prevalent;
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
import com.google.firebase.database.ValueEventListener;
import com.mobile.finalproject.R;
import com.mobile.finalproject.model.Users;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String parentDbName = "Users";

    EditText userEditText;

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPass;
    ProgressBar progressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email_input);
        editTextPass = findViewById(R.id.password_input);

        progressSpinner = findViewById(R.id.progressBar);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        userEditText = (EditText)findViewById(R.id.email_input);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email.");
            editTextEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()) {
            editTextPass.setError("Password is required.");
            editTextPass.requestFocus();
            return;
        }

        if(pass.length() < 6) {
            editTextPass.setError("Minimum length of password should is 6.");
            editTextPass.requestFocus();
            return;
        }

        progressSpinner.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressSpinner.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    //NAVIGATE TO NEW ACTIVITY
                    //Intent intent = new Intent(LoginActivity.this, OtherActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);
                    System.out.println("AYYYYY IT WAS SUCCESSFULLY LOGGED IN");
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                String userName = userEditText.getText().toString();
              //  Prevalent.currentOnlineUser.setName(userName);
                startActivity(intent);
                userLogin();//keep here
                break;
        }
    }
}
