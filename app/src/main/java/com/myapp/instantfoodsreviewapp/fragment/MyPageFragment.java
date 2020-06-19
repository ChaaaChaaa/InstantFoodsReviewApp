package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.BuildConfig;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.UserAccountData;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageFragment extends Fragment implements Button.OnClickListener {
    private TextView currentVersion;
    private TextView currentNickName;
    private TextView getEmail;
    private TextView btnChangePassword;
    private TextView btnSecession;
    private UserPreference userPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);
        init(rootView);
        initButton(rootView);
        initPreference();
        getUserInfo();
        return rootView;
    }

    private void init(View view) {
        String versionName = BuildConfig.VERSION_NAME;
        currentVersion = view.findViewById(R.id.version_info);
        currentVersion.setText(versionName);
        currentNickName = view.findViewById(R.id.tv_setting_nickname);
        getEmail = view.findViewById(R.id.tv_setting_email);

    }

    private void initButton(View view) {
        btnChangePassword = view.findViewById(R.id.tv_change_password);
        btnChangePassword.setOnClickListener(this);
        btnSecession = view.findViewById(R.id.tv_secession);
        btnSecession.setOnClickListener(this);
    }

    private void initPreference(){
        userPreference = new UserPreference();
        userPreference.setContext(getContext());
    }

    private void getUserInfo() {
        String getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.account(getToken);
        call.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                if(response.isSuccessful()){
                    ApiResultDto dto = response.body();
                    JsonObject resultData = dto.getResultData();
                    if(resultData != null){
                        UserAccountData userAccountData = new Gson().fromJson(resultData,UserAccountData.class);
                        getEmail.setText(userAccountData.getEmail());
                        currentNickName.setText(userAccountData.getNickname());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResultDto> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change_password:
                openChangePasswordDialog();
                break;
            case R.id.tv_secession:
                openSecessionDialog();
                break;
        }
    }

    private void openChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.show(getFragmentManager(), "change password dialog");
    }

    private void openSecessionDialog() {
        SecessionDialog secessionDialog = new SecessionDialog();
        secessionDialog.show(getFragmentManager(), "secession dialog");
    }


}
