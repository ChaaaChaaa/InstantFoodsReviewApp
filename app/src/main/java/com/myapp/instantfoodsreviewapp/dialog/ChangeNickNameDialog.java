package com.myapp.instantfoodsreviewapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.ChangeNickNameData;
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

public class ChangeNickNameDialog extends AppCompatDialogFragment {
    private String newNickName;
    private TextInputEditText editNewNickname;
    private TransferDataCallback<String> resultCallback;

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_change_nickname, null);
        init(view);
        initPreference();
        AlertDialog nicknameDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Change Nickname")
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    String changedNickName = Objects.requireNonNull(editNewNickname.getText()).toString();
                    dismiss();
                })
                .create();

        nicknameDialog.setOnShowListener(dialog -> {
            Button okButton = nicknameDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(v -> {
                newNickName = Objects.requireNonNull(editNewNickname.getText()).toString();
                checkNickNameCondition(nicknameDialog);
            });
        });
        return nicknameDialog;
    }

    private void checkNickNameCondition(AlertDialog dialog) {
        if (checkNickNameNull()) {
            getUserNickName();
            Toast.makeText(getActivity(), "닉네임을 변경하였습니다.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }


    private void init(View view) {
        editNewNickname = view.findViewById(R.id.edit_new_nickname);
    }

    private boolean checkNickNameNull() {
        if (newNickName == null) {
            Toast.makeText(getActivity(), "새로운 닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
            editNewNickname.setError("Blank Nickname");
            return false;
        }
        return true;
    }

    private UserPreference userPreference;

    private void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(getContext());
    }

    private void getUserNickName() {
        String getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.nickname(getToken, newNickName);
        call.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(@NotNull Call<ApiResultDto> call, @NotNull Response<ApiResultDto> response) {
                if (response.isSuccessful()) {
                    ApiResultDto apiResultDto = response.body();
                    assert apiResultDto != null;
                    JsonObject resultData = apiResultDto.getResultData();
                    if (resultData != null) {
                        ChangeNickNameData changeNickNameData = new Gson().fromJson(resultData, ChangeNickNameData.class);
                        UserPreference.getInstance().putString(Config.KEY_NICKNAME, newNickName);
                        resultCallback.transfer(changeNickNameData.getNewNickName());
                    } else {
                        Log.e("new nickname", "new nickname is null");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiResultDto> call, @NotNull Throwable t) {
                editNewNickname.setError("duplicated nickname");
                Toast.makeText(getActivity(), "기존 닉네임과 동일합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setResultCallback(TransferDataCallback<String> resultCallback) {
        this.resultCallback = resultCallback;
    }

    public void setNickNameDrawerCallback(TransferDataCallback<String> nickNameDrawerCallback) {
    }
}
