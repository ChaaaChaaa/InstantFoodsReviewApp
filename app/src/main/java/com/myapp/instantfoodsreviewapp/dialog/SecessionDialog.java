package com.myapp.instantfoodsreviewapp.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.activity.EmailLoginActivity;
import com.myapp.instantfoodsreviewapp.activity.LoginActivity;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecessionDialog extends AppCompatDialogFragment {
    private UserPreference userPreference;
    private String TAG = SecessionDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_secession,null);
        builder.setView(view)
                .setTitle("Session Alert")
                .setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initPreference();
                        doSecession();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        dialog.dismiss();
                        startActivity(intent);
                        getActivity().finish();

                    }
                })
                .setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    private void initPreference(){
        userPreference = new UserPreference();
        userPreference.setContext(getActivity());
    }

    private void doSecession(){
        String getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.secession(getToken);

        call.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                    if(response.isSuccessful()){
                        ApiResultDto secessionData = response.body();
                       // JsonObject resultData = secessionData.getResultData();

                        Log.i(TAG, "Response:" + response.body());
                    }
                    else {
                        Toast.makeText(getActivity(),"탈퇴 거부",Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<ApiResultDto> call, Throwable t) {

            }
        });

    }


}
