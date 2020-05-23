package com.myapp.instantfoodsreviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.myapp.instantfoodsreviewapp.databinding.ActivityEmailLoginBinding;
import com.myapp.instantfoodsreviewapp.model.UserLoginData;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = EmailLoginActivity.class.getSimpleName();
    private TextInputLayout textLoginEmail;
    private TextInputLayout textLoginPassword;
    private ProgressBar progressBar;
    private Button loginButton;
    private ActivityEmailLoginBinding activityEmailLoginBinding;
    private RetrofitInterface retrofitInterface;

    private String userEmail;
    private static EmailLoginActivity instance;

    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmailLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_login);
        initClient();
        initEmailLogin();
        initListener();
    }

    public void initClient() {
        retrofitInterface = RetrofitClient.buildHTTPClient();
    }

    private void initEmailLogin() {
        loginButton = activityEmailLoginBinding.btnLogin;
        textLoginEmail = activityEmailLoginBinding.textLoginEmail;
        textLoginPassword = activityEmailLoginBinding.textLoginPassword;
        progressBar = activityEmailLoginBinding.loginProgress;
        loginEmail = activityEmailLoginBinding.loginEmail;
        loginPassword = activityEmailLoginBinding.loginPassword;
    }

    private void initListener(){
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"이메일 형식이 잘못되었습니다.",Toast.LENGTH_LONG).show();
            return;
        }
        else if (textLoginPassword.getEditText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
            textLoginPassword.setError("Password Blank");
            return;

        } else {
            doLogin();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    void doLogin() {
        userEmail = Objects.requireNonNull(loginEmail.getText()).toString();
        String userPwd = Objects.requireNonNull(loginPassword.getText()).toString();
        if (!Const.isNullOrEmptyString(userEmail, userPwd)) {
            showLoading(true);
            retrofitInterface.login(userEmail, userPwd).enqueue(new Callback<UserLoginData>() {
                @Override
                public void onResponse(Call<UserLoginData> call, Response<UserLoginData> response) {
                    if (response.isSuccessful()) {
                        UserLoginData loginData = response.body();
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("loginEmail", userEmail);
                        intent.putExtras(bundle);

                        startActivity(intent);
                        finish();

                        Log.i(TAG, "Response:" + response.body());
                    } else {
                        Toast.makeText(getApplication(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                    showLoading(false);
                }

                @Override
                public void onFailure(Call<UserLoginData> call, Throwable t) {
                    Log.e("fail error", t.getLocalizedMessage());
                }
            });
        } else {
            checkNullOrEmpty();
        }
    }


    private void checkNullOrEmpty() {
        if (Const.isNullOrEmptyString(Objects.requireNonNull(textLoginEmail.getEditText()).toString())) {
            textLoginEmail.setError("아이디가 입력되지 않았습니다.");
            return;
        }

        if (Const.isNullOrEmptyString(Objects.requireNonNull(textLoginPassword.getEditText()).toString())) {
            textLoginPassword.setError("비밀번호가 입력되지 않았습니다.");
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public static EmailLoginActivity getInstance() {
        return instance;
    }
}
