package com.myapp.instantfoodsreviewapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Const;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private TextView getNickName;
    private Button logoutButton;
    private ActivityMainBinding activityMainBinding;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getNickName = activityMainBinding.getNickName;
        logoutButton = activityMainBinding.logoutButton;
        logoutButton.setOnClickListener(this);
        //bringNickName();
    }

//    void bringNickName() {
//        String nickName = Objects.requireNonNull(getIntent().getExtras()).getString("loginEmail");
//
//        if (!Const.isNullOrEmptyString(nickName)) {
//            getNickName.setText(nickName);
//        } else {
//            getNickName.setText("입력받은 값이 없습니다.");
//        }
//    }

    public void userLogOut(View view) {
        userPreference.setLoggedIn(getApplicationContext(),false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        userLogOut(view);
    }
}
