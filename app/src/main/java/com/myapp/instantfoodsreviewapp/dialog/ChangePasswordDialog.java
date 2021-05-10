package com.myapp.instantfoodsreviewapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialog extends AppCompatDialogFragment {
    private static final int MAX_PASSWORD_LENGTH = 15;
    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int BLANK_PASSWORD = 0;
    private EditText editCurrentPassword;
    private EditText editNewPassword;
    private String oldPassword;
    private String newPassword;
    private UserPreference userPreference;
    private String getToken;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        init(view);
        initPreference();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.change_password)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();

        alertDialog.setOnShowListener(dialog -> {

            Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(v -> {
                oldPassword = editCurrentPassword.getText().toString();
                newPassword = editNewPassword.getText().toString();

                checkPasswordCondition(alertDialog);

            });

        });

        return alertDialog;
    }

    private void init(View view) {
        editCurrentPassword = view.findViewById(R.id.edit_current_password);
        editNewPassword = view.findViewById(R.id.edit_new_password);
    }


    private void checkPasswordCondition(AlertDialog dialog) {
        if (!flagPasswordCondition()) {
            getUserPassword();
            Toast.makeText(getActivity(), R.string.success_change_password, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private boolean flagPasswordCondition() {
        return !checkPasswordNull() || !checkPasswordMaxLength() || !checkDuplicatePassword();
    }


    private boolean checkPasswordNull() {
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

    private boolean checkPasswordMaxLength() {
        if (newPassword.length() > MAX_PASSWORD_LENGTH || newPassword.length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(getActivity(), R.string.wrong_length_password, Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Unable Password");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDuplicatePassword() {
        if (newPassword.equals(oldPassword)) {
            Toast.makeText(getActivity(), R.string.duplicate_password, Toast.LENGTH_SHORT).show();
            editNewPassword.setError("Duplicate Password");
            return false;
        } else {
            return true;
        }
    }

    private void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(getActivity());
    }

    private void getUserPassword() {
        getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.change(getToken, oldPassword, newPassword);
        call.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(@NotNull Call<ApiResultDto> call, @NotNull Response<ApiResultDto> response) {
                if (response.isSuccessful()) {
                    ApiResultDto apiResultDto = response.body();
                    assert apiResultDto != null;
                    JsonObject resultData = apiResultDto.getResultData();
                    if (resultData != null) {
                        String realOldPassword = resultData.get("original_password").getAsString();
                        String realNewPassword = resultData.get("request_password").getAsString();
                        Log.e("original password", realOldPassword);
                        Log.e("request_password", realNewPassword);
                        // Toast.makeText(getActivity(), R.string.success_change_password, Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("getUser", "Account null ");
                    }

                    UserPreference userPreference = new UserPreference();
                    if (getToken != null) {
                        userPreference.putString(Config.KEY_TOKEN, getToken);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiResultDto> call, @NotNull Throwable t) {
                editCurrentPassword.setError("Wrong Password");
                Toast.makeText(getActivity(), "기존 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}