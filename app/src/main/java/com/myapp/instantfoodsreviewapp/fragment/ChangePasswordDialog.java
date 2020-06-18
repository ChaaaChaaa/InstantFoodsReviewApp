package com.myapp.instantfoodsreviewapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.myapp.instantfoodsreviewapp.R;

public class ChangePasswordDialog extends Fragment implements Button.OnClickListener {
    private EditText editCurrentPassword;
    private EditText editNewPassword;
    private Button btnChangePasswordOK;
    private Button btnChangePasswordCancel;
    Dialog customDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_change_password_dialog, container, false);
        setHasOptionsMenu(true);
        customDialog = new Dialog(getActivity());
        init(rootView);
        showCustomDialog();
        initListener();
        onClick(rootView);
       // customDialog.show();
        return rootView;
    }

    private void init(View view) {
        editCurrentPassword = view.findViewById(R.id.edit_current_password);
        editNewPassword = view.findViewById(R.id.edit_new_password);
        btnChangePasswordCancel = view.findViewById(R.id.btn_change_password_cancel);
        btnChangePasswordOK = view.findViewById(R.id.btn_change_password_ok);

    }

    private void initListener(){
        btnChangePasswordCancel.setOnClickListener(this::onClick);
        btnChangePasswordOK.setOnClickListener(this::onClick);
    }


    private void showCustomDialog() {
        customDialog.setContentView(R.layout.layout_change_password_dialog);
        Window window = customDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_change_password_cancel) {
            customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
        } else if (view.getId() == R.id.btn_change_password_ok) {
            String oldPassword = editCurrentPassword.getText().toString();
            String newPassword = editNewPassword.getText().toString();

            checkPasswordNull(oldPassword,newPassword);
            checkPasswordMaxLength(oldPassword,newPassword);
            checkDuplicatePassword(oldPassword,newPassword);
        }
    }


    private void checkPasswordNull(String oldPassword,String newPassword) {
        if (oldPassword.length() == 0) {
            Toast.makeText(getActivity(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            editCurrentPassword.setError("Password Blank");
        } else if (newPassword.length() == 0) {
            Toast.makeText(getActivity(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Password Blank");
        }
    }

    private void checkPasswordMaxLength(String oldPassword,String newPassword){
        if (oldPassword.length() > 15 || oldPassword.length() < 5) {
            Toast.makeText(getActivity(), "비밀번호 길이가 안맞습니다.", Toast.LENGTH_SHORT).show();
            editCurrentPassword.setError("Unable Password");
        } else if (newPassword.length()  < 10|| oldPassword.length() < 5) {
            Toast.makeText(getActivity(), "비밀번호 길이가 안맞습니다.", Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Unable Password");
        }
    }

    private void checkDuplicatePassword(String oldPassword,String newPassword){
        if (oldPassword == newPassword) {
            Toast.makeText(getActivity(), "새로운 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
            editCurrentPassword.setError("Duplicate Password");
        }
    }

    public void showDialog(){
        customDialog.show();
    }


}
