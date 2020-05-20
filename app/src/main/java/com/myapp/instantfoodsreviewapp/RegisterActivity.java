package com.myapp.instantfoodsreviewapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputLayout;
import com.myapp.instantfoodsreviewapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements Button.OnClickListener {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputNickName;
    private Button confirmButton;
    private ActivityRegisterBinding registerBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        init();

    }

    private void init() {
        textInputEmail = registerBinding.textInputEmail;
        textInputPassword = registerBinding.textInputPassword;
        textInputNickName = registerBinding.textInputNickname;
        confirmButton = registerBinding.btnConfirm;
    }

    @Override
    public void onClick(View view) {

    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("이메일 주소를 입력해주세요");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String emailInput = textInputPassword.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputPassword.setError("비밀번호를 입력해주세요");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validateNickName() {
        String nickNameInput = textInputNickName.getEditText().getText().toString().trim();
        if (nickNameInput.isEmpty()) {
            textInputPassword.setError("닉네임을 입력해주세요");
            return false;
        } else if (nickNameInput.length() > 15) {
            textInputNickName.setError("닉네임 길이를 맞춰주세요");
            return false;
        } else {
            textInputNickName.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword() | !validatePassword()) {
            return;
        }
    }

}
