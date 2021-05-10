package com.myapp.instantfoodsreviewapp.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.ChangeNickNameData;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeNickNameDialog extends AppCompatDialogFragment {
    private String newNickName;
    private TextInputEditText editNewNickname;
    private TransferDataCallback<String> resultCallback;
    private TransferDataCallback<String> nickNameDrawerCallback;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_change_nickname, null);
        init(view);
        initPreference();
       // getUserNickName();
        AlertDialog nicknameDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Change Nickname")
                .setPositiveButton(R.string.ok,null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String changedNickName = editNewNickname.getText().toString();
                        dismiss();
                    }
                })
                .create();

        nicknameDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button okButton = nicknameDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newNickName = editNewNickname.getText().toString();
                        checkNickNameCondition(nicknameDialog);
                    }
                });
            }
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
    private String getToken;

    private void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(getContext());
    }

    private void getUserNickName() {
        getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.nickname(getToken, newNickName);
        call.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                if (response.isSuccessful()) {
                    ApiResultDto apiResultDto = response.body();
                    JsonObject resultData = apiResultDto.getResultData();
                    if (resultData != null) {
                        ChangeNickNameData changeNickNameData = new Gson().fromJson(resultData, ChangeNickNameData.class);
                        UserPreference.getInstance().putString(Config.KEY_NICKNAME,newNickName);
                        resultCallback.transfer(changeNickNameData.getNewNickName());
                        //nickNameDrawerCallback.transfer(changeNickNameData.getNewNickName());
                    } else {
                        Log.e("new nickname", "new nickname is null");
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResultDto> call, Throwable t) {
                editNewNickname.setError("duplicated nickname");
                Toast.makeText(getActivity(), "기존 닉네임과 동일합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public TransferDataCallback<String> getResultCallback() {
        return resultCallback;
    }

    public void setResultCallback(TransferDataCallback<String> resultCallback) {
        this.resultCallback = resultCallback;
    }

    public TransferDataCallback<String> getNickNameDrawerCallback() {
        return nickNameDrawerCallback;
    }

    public void setNickNameDrawerCallback(TransferDataCallback<String> nickNameDrawerCallback) {
        this.nickNameDrawerCallback = nickNameDrawerCallback;
    }
}
