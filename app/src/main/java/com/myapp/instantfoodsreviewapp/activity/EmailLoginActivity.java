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
import com.myapp.instantfoodsreviewapp.model.UserAccountData;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;
import com.myapp.instantfoodsreviewapp.utils.Const;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityEmailLoginBinding;
import com.myapp.instantfoodsreviewapp.model.UserLoginData;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.instantfoodsreviewapp.utils.Config.KEY_TOKEN;

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = EmailLoginActivity.class.getSimpleName();
    private TextInputLayout textLoginEmail;
    private TextInputLayout textLoginPassword;
    private ProgressBar progressBar;
    private Button loginButton;
    private CheckBox checkBox;


    private ActivityEmailLoginBinding activityEmailLoginBinding;
    private RetrofitInterface retrofitInterface;
    private UserPreference userPreference;

    private String userEmail;
    private String userPwd;
    private String sendToken;
    private boolean isLogin = true;


    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmailLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_login);
        initConfigValue();
        initClient();
        initEmailLogin();
        initListener();
        checkAutoLogin();
    }

    public void initConfigValue(){
        //객체지향 함수 쌩기초 공부가 필요함.
        //현재 객체 생성에 대한 개념자체가 적립되지 않은 것으로 보임.
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }
    public void initClient() {
        retrofitInterface = RetrofitClient.buildHTTPClient();
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
        } else {
            return;
        }
    }


    public void onCheckboxClicked(View view) {
        switch (view.getId()) {
            case R.id.autoLoginCheck:
                if (checkBox.isChecked()) {
                    //필요 없는 작업
                   /* userEmail = Objects.requireNonNull(loginEmail.getText()).toString();
                    userPwd = Objects.requireNonNull(loginPassword.getText()).toString();*/

                    userPreference.setLoggedIn(getApplicationContext(), true);
                } else {
                    //필요 없는 작업
                    // 이걸 클리어 하게 되면 , 모든 프리퍼런스 값이 다 클리어됨
                   // userPreference.clearUserLogin();

                    // 단순히 false로 replace해주면 됨.
                    userPreference.setLoggedIn(getApplicationContext(), false);

                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show();
        } else if (loginPassword.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
            loginPassword.setError("Password Blank");

        } else {
            doLogin();
        }
    }


    void doLogin() {
        userEmail = Objects.requireNonNull(loginEmail.getText()).toString();
        userPwd = Objects.requireNonNull(loginPassword.getText()).toString();
        if (Const.isNullOrEmptyString(userEmail, userPwd)) {
            showLoading(true);
            //공통 디티오로 <> 안에 값 바꿈
            retrofitInterface.login(userEmail, userPwd).enqueue(new Callback<ApiResultDto>() {
                @Override
                public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                    if (response.isSuccessful()) {
                        Log.e("로그","555");
                        userPreference.putString( KEY_TOKEN,sendToken);

                        //얘는 .. Main으로 넘어가서 호출하세요~
                        //doStore();
                        ApiResultDto loginData = response.body();
                        String token = loginData.getResultData().get("user_token").getAsString();
                        if(Const.isNullOrEmptyString(token)){
                            userPreference.putString("TOKEN",token);
                            String checkToken = userPreference.getString("TOKEN");
                            Log.e("UserToken ",checkToken);
                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("loginEmail", userEmail);
                            intent.putExtras(bundle);

                            startActivity(intent);
                            finish();

                            Log.i(TAG, "Response:" + response.body());
                        }
                        else{

                        }

                    } else {
                        Toast.makeText(getApplication(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }

                    showLoading(false);
                }

                @Override
                public void onFailure(@NotNull Call<ApiResultDto> call, Throwable t) {
                    Log.e("fail error", t.getLocalizedMessage());
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

    void doStore(){
        //retrofitInterface  = RetrofitClient.getRestMethods().
        //userPreference.getString(this,sendToken);

       // sendToken =  UserPreference.getInstance();
        retrofitInterface.account(sendToken,userEmail,userPwd).enqueue(new Callback<UserAccountData>() {
           //Call<UserAccountData> userAccountDataCall = userPreference.getString(this,sendToken);
            @Override
            public void onResponse(Call<UserAccountData> call, Response<UserAccountData> response) {
                if(response.isSuccessful()){
                    Log.e("로그","222");
                    UserAccountData accountData = response.body();
                    Toast.makeText(getApplicationContext(), "토큰 저장 성공", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAccountData> call, Throwable t) {
                Toast.makeText(getApplication(), "토큰 저장 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
