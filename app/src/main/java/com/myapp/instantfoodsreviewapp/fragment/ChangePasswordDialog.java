package com.myapp.instantfoodsreviewapp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.myapp.instantfoodsreviewapp.R;

public class ChangePasswordDialog extends AppCompatDialogFragment {
    private static final int MAX_PASSWORD_LENGTH = 15;
    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int BLANK_PASSWORD = 0;
    private EditText editCurrentPassword;
    private EditText editNewPassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_change_password_dialog, null);
        init(view);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.change_password)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPassword = editCurrentPassword.getText().toString();
                        String newPassword = editNewPassword.getText().toString();
                        checkPasswordCondition(oldPassword, newPassword, alertDialog);
                        Toast.makeText(getActivity(), R.string.success_change_password, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        return alertDialog;
    }

    private void init(View view) {
        editCurrentPassword = view.findViewById(R.id.edit_current_password);
        editNewPassword = view.findViewById(R.id.edit_new_password);
    }


    private void checkPasswordCondition(String oldPassword, String newPassword, AlertDialog dialog) {
        if (!checkPasswordNull(oldPassword, newPassword) || !checkPasswordMaxLength(oldPassword, newPassword) || !checkDuplicatePassword(oldPassword, newPassword)) {

        } else {
            dialog.dismiss();
        }
    }


    private boolean checkPasswordNull(String oldPassword, String newPassword) {
        if (oldPassword.length() == BLANK_PASSWORD) {
            Toast.makeText(getActivity(), R.string.blank_password, Toast.LENGTH_SHORT).show();
            editCurrentPassword.setError("Password Blank");
            return false;
        } else if (newPassword.length() == BLANK_PASSWORD) {
            Toast.makeText(getActivity(), R.string.blank_password, Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Password Blank");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPasswordMaxLength(String oldPassword, String newPassword) {
        if (oldPassword.length() > MAX_PASSWORD_LENGTH || oldPassword.length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(getActivity(), R.string.wrong_length_password, Toast.LENGTH_SHORT).show();
            editCurrentPassword.setError("Unable Password");
            return false;
        } else if (newPassword.length() < MAX_PASSWORD_LENGTH || oldPassword.length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(getActivity(), R.string.wrong_length_password, Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Unable Password");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDuplicatePassword(String oldPassword, String newPassword) {
        if (newPassword.equals(oldPassword)) {
            Toast.makeText(getActivity(), R.string.duplicate_password, Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Duplicate Password");
            return false;
        } else {
            return true;
        }
    }


}
