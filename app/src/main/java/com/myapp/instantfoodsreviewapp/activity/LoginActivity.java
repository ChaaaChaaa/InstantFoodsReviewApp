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
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton;
    private TextView loginTextView;
    private ActivityLoginBinding loginBinding;
    private EmailLoginActivity emailLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();


    }

    public void init() {
        loginButton = loginBinding.loginButton;
        loginButton.setOnClickListener(this);
        loginTextView = loginBinding.tvRegisterEmail;
        loginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.login_button:
                intent = new Intent(this,EmailLoginActivity.class);
                break;
            case R.id.tv_register_email:
                intent = new Intent(this, RegisterActivity.class);
                break;
        }
        startActivity(intent);
    }




}
