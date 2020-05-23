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
import com.myapp.instantfoodsreviewapp.databinding.ActivityRegisterBinding;
import com.myapp.instantfoodsreviewapp.model.UserRegisterData;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements Button.OnClickListener {
    private String TAG = RegisterActivity.class.getSimpleName();

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputNickName;
    private Button confirmButton;
    private ActivityRegisterBinding registerBinding;

    ProgressBar progressBar;

    RetrofitInterface retrofitInterface;

    private String registerEmail;
    private String registerPassword;
    private String registerNickName;

    private TextInputEditText userEmail;
    private TextInputEditText userPassword;
    private TextInputEditText userNickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        init();
        confirmButton.setOnClickListener(this);

    }

    private void init() {
        textInputEmail = registerBinding.textInputEmail;
        textInputPassword = registerBinding.textInputPassword;
        textInputNickName = registerBinding.textInputNickname;
        confirmButton = registerBinding.btnConfirm;
        progressBar = registerBinding.registerProgress;

        userEmail = registerBinding.registerEmail;
        userPassword = registerBinding.registerPassword;
        userNickName = registerBinding.registerNickname;
    }

    @Override
    public void onClick(View view) {
        confirmInput(view);
    }

    private boolean validateEmail() {
        String emailInput = userEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            userEmail.setError("이메일 주소를 입력해주세요");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"이메일 형식이 잘못되었습니다.",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            userEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String emailInput = userPassword.getText().toString().trim();
        if (emailInput.isEmpty()) {
            userPassword.setError("비밀번호를 입력해주세요");
            return false;
        } else {
            userPassword.setError(null);
            return true;
        }
    }

    private boolean validateNickName() {
        String nickNameInput = userNickName.getText().toString().trim();
        if (nickNameInput.isEmpty()) {
            userNickName.setError("닉네임을 입력해주세요");
            return false;
        } else if (nickNameInput.length() > 15) {
            userNickName.setError("닉네임 길이를 맞춰주세요");
            return false;
        } else {
            userNickName.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword() | !validateNickName()) {
            return;
        } else {
            doRegister();
        }
    }

//    public UserRegisterData createRegisterRequest() {
//        UserRegisterData userRegisterData = new UserRegisterData();
//        userRegisterData.setEmail(textInputEmail.getEditText().getText().toString());
//        userRegisterData.setNickName(textInputNickName.getEditText().getText().toString());
//        userRegisterData.setPassword(textInputPassword.getEditText().getText().toString());
//        return userRegisterData;
//    }

    void doRegister() {
        //  userEmail = Objects.requireNonNull(loginEmail.getText()).toString();



        registerEmail = userEmail.getText().toString();
        registerNickName = userNickName.getText().toString();
        registerPassword = userPassword.getText().toString();

        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<UserRegisterData> call = retrofitInterface.regist(registerEmail,registerNickName,registerPassword);

        if (!Const.isNullOrEmptyString(registerEmail, registerNickName, registerPassword)) {
            showLoading(true);
            call.enqueue(new Callback<UserRegisterData>() {
                @Override
                public void onResponse(Call<UserRegisterData> call, Response<UserRegisterData> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, EmailLoginActivity.class);
                        startActivity(intent);
                        finish();
                        Log.i(TAG, "Responser: " + response.body());
                    } else {
                        Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                    showLoading(false);
                }

                @Override
                public void onFailure(Call<UserRegisterData> call, Throwable t) {
                    Log.e("fail error", t.getLocalizedMessage());
                }
            });
//            restMethods.regist(registerEmail, registerNickName, registerPassword).enqueue(new Callback<UserRegisterData>() {
//              //Log.i("로그","55" );
//                @Override
//                public void onResponse(Call<UserRegisterData> call, Response<UserRegisterData> response) {
//                    Log.v("로그","55" );
//                    if (response.isSuccessful()) {
//                        Log.v("로그","66" );
//                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(RegisterActivity.this, EmailLoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Log.i(TAG, "Responser: " + response.body());
//                    } else {
//                        Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
//                    }
//                    showLoading(false);
//                }
//
//                @Override
//                public void onFailure(Call<UserRegisterData> call, Throwable t) {
//                    Log.v("로그","77" );
//                    Log.e("fail error", t.getLocalizedMessage());
//                }
//            });
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
