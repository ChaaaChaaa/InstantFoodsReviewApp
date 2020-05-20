package com.myapp.instantfoodsreviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputLayout;
import com.myapp.instantfoodsreviewapp.databinding.ActivityEmailLoginBinding;

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout textLoginEmail;
    private TextInputLayout textLoginPassword;
    private Button loginButton;
    private ActivityEmailLoginBinding activityEmailLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_email_login);
        activityEmailLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_login);
        initEmailLogin();
    }

    private void initEmailLogin() {
        loginButton = activityEmailLoginBinding.btnLogin;
        textLoginEmail = activityEmailLoginBinding.textLoginEmail;
        textLoginPassword = activityEmailLoginBinding.textLoginPassword;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
