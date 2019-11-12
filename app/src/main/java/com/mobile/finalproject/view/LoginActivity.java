package com.mobile.finalproject.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.mobile.finalproject.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPass;
    ProgressBar progressSpinner;
    private DatabaseReference mDatabase;


    public class Item{
        public String name;

        public Item(String name){
            this.name = name;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("items");

        editTextEmail = findViewById(R.id.email_input);
        editTextPass = findViewById(R.id.password_input);

        progressSpinner = findViewById(R.id.progressBar);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
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

    mDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Item item = dataSnapshot.getValue(Item.class);
            System.out.println(item);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println("THE READ FAILED ON BUTTON CLICK: " + databaseError.getCode());
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
                userLogin();
                break;
        }
    }
}
