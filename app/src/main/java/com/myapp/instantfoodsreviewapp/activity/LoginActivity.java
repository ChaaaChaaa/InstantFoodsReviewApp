package com.myapp.instantfoodsreviewapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton;
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    public void init() {
        loginButton = loginBinding.loginButton;
        loginButton.setOnClickListener(this);
        TextView loginTextView = loginBinding.tvRegisterEmail;
        loginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        int buttonId = loginButton.getId();

        if (view.getId() == buttonId) {
            intent = new Intent(this, EmailLoginActivity.class);
        } else {
            intent = new Intent(this, RegisterActivity.class);
        }

        startActivity(intent);
        finish();
    }
}