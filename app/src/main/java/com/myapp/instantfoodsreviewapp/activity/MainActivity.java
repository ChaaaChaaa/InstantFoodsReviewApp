package com.myapp.instantfoodsreviewapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.instantfoodsreviewapp.model.UserAccountData;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.BasicAuthInterceptor;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;
import com.myapp.instantfoodsreviewapp.utils.Const;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityMainBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private String TAG = EmailLoginActivity.class.getSimpleName();

    private TextView getNickName;
    private Button logoutButton;
    private ActivityMainBinding activityMainBinding;
    private UserPreference userPreference;
    private String getToken;
    private RetrofitClient retrofitClient;
    private BasicAuthInterceptor basicAuthInterceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initPreference();
        getNickName = activityMainBinding.getNickName;
        logoutButton = activityMainBinding.logoutButton;
        logoutButton.setOnClickListener(this);
        getUser();
    }

//    void bringNickName() {
//        String nickName = Objects.requireNonNull(getIntent().getExtras()).getString("TOKEN");
//
//        if (Const.isNullOrEmptyString(nickName)) {
//            getNickName.setText(nickName);
//        } else {
//            getNickName.setText("입력받은 값이 없습니다.");
//        }
//    }

    public void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }

    public void userLogOut(View view) {
        userPreference.setLoggedIn(getApplicationContext(), false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        userLogOut(view);
    }

    private void getUser() {
       // String realToken;
        getToken = userPreference.getString(Config.KEY_TOKEN);
        //RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        //Call<UserAccountData> call = retrofitInterface.account(getToken););
       // Call<UserAccountData> call = retrofitClient.buildHTTPClient().account();

        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<UserAccountData> call = retrofitInterface.account(getToken);

      //  retrofitClient.sendNetworkRequest(getToken);


       // UserAccountData userAccountData = new UserAccountData();
        //String nickName = userPreference.getString("")
        call.enqueue(new Callback<UserAccountData>() {

            public void onResponse(Call<UserAccountData> call, Response<UserAccountData> response) {
                if (response.isSuccessful()) {
                    String nickName =response.body().getNickname();
                   // Log.i(TAG, "Responser: " + response.body());
                    getNickName.setText(nickName);


                UserPreference userPreference = new UserPreference();
                if(getToken != null){
                     userPreference.putString(Config.KEY_TOKEN,getToken);
                }


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
