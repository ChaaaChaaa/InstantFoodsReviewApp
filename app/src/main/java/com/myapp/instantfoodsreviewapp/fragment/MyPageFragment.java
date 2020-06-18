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

import com.myapp.instantfoodsreviewapp.BuildConfig;
import com.myapp.instantfoodsreviewapp.R;

public class MyPageFragment extends Fragment implements Button.OnClickListener {
    private TextView currentVersion;
    private TextView currentNickName;
    private TextView getEmail;
    private TextView btnChangePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);
        init(rootView);
        initButton(rootView);
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


    }

    private void getUserInfo() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change_password:
                openChangePasswordDialog();
                break;
        }
    }

    public void openChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.show(getFragmentManager(),"change password dialog");
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, new ChangePasswordDialog()).commit();

    }
}
