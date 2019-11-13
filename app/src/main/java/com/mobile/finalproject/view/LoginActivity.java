package com.mobile.finalproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mobile.finalproject.HomeActivity;
import com.mobile.finalproject.Prevalent.Prevalent;
import com.mobile.finalproject.R;
import com.mobile.finalproject.model.Users;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String parentDbName = "Users";

    EditText userEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        userEditText = (EditText)findViewById(R.id.email_input);
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
                break;
        }
    }
}
