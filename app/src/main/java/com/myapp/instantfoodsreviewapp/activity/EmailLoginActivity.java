package com.myapp.instantfoodsreviewapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.myapp.instantfoodsreviewapp.model.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;
import com.myapp.instantfoodsreviewapp.utils.Const;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityEmailLoginBinding;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = EmailLoginActivity.class.getSimpleName();
    private TextInputLayout textLoginEmail;
    private TextInputLayout textLoginPassword;
    private ProgressBar progressBar;
    private Button loginButton;
    private CheckBox checkBox;

    private ActivityEmailLoginBinding activityEmailLoginBinding;
    private RetrofitInterface retrofitInterface;
    private UserPreference userPreference;

    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmailLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_login);
        initConfigValue();
        initEmailLogin();
        initListener();
        checkAutoLogin();
        initClient();

    }


    public void initConfigValue() {
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }

    public void initClient() {
        retrofitInterface = RetrofitClient.buildHTTPClient(

        );
    }

    private void initEmailLogin() {
        loginButton = activityEmailLoginBinding.btnLogin;
        textLoginEmail = activityEmailLoginBinding.textLoginEmail;
        textLoginPassword = activityEmailLoginBinding.textLoginPassword;
        progressBar = activityEmailLoginBinding.loginProgress;
        checkBox = activityEmailLoginBinding.autoLoginCheck;
        loginEmail = activityEmailLoginBinding.loginEmail;
        loginPassword = activityEmailLoginBinding.loginPassword;
    }

    private void initListener() {
        loginButton.setOnClickListener(this);
        checkBox.setOnClickListener(this::onCheckboxClicked);
    }

    public void checkAutoLogin() {
        if (userPreference.getLoggedStatus()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    public void onCheckboxClicked(View view) {
        if (view.getId() == R.id.autoLoginCheck) {
            userPreference.setLoggedIn(getApplicationContext(), checkBox.isChecked());
        }
    }

    @Override
    public void onClick(View view) {
        if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(loginEmail.getText()).toString()).matches()) {
            Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show();
        } else if (Objects.requireNonNull(loginPassword.getText()).toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
            loginPassword.setError("Password Blank");

        } else {
            doLogin();
        }
    }


    void doLogin() {
        String userEmail = Objects.requireNonNull(loginEmail.getText()).toString();
        String userPwd = Objects.requireNonNull(loginPassword.getText()).toString();
        if (Const.isNullOrEmptyString(userEmail, userPwd)) {
            showLoading(true);
            retrofitInterface.login(userEmail, userPwd).enqueue(new Callback<ApiResultDto>() {
                @Override
                public void onResponse(@NotNull Call<ApiResultDto> call, @NotNull Response<ApiResultDto> response) {
                    if (response.isSuccessful()) {
                        //userPreference.putString(Config.KEY_TOKEN, sendToken);
                        //Log.e("tokenTest1"," "+ userPreference.getString(Config.KEY_TOKEN));

                        //UserPreference.getInstance().putString(Config.KEY_TOKEN, sendToken);
                        ApiResultDto loginData = response.body();

                        assert loginData != null;
                        if (loginData.getResultCode() == 200) {
                            String token = loginData.getResultData().get("user_token").getAsString();
                            UserPreference.getInstance().putString(Config.KEY_TOKEN, token);

                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(Config.KEY_TOKEN, token);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();

                            Log.i(TAG, "Response:" + response.body());
                        } else {
                            Toast.makeText(getApplication(), "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplication(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }

                    showLoading(false);
                }

                @Override
                public void onFailure(@NotNull Call<ApiResultDto> call, @NotNull Throwable t) {
                    Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");

                }
            });
        } else {
            checkNullOrEmpty();
        }
    }


    private void checkNullOrEmpty() {
        if (!Const.isNullOrEmptyString(Objects.requireNonNull(textLoginEmail.getEditText()).toString())) {
            textLoginEmail.setError("아이디가 입력되지 않았습니다.");
            return;
        }

        if (!Const.isNullOrEmptyString(Objects.requireNonNull(textLoginPassword.getEditText()).toString())) {
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


}