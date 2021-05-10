package com.myapp.instantfoodsreviewapp.fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.library.baseAdapters.BuildConfig;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.dialog.ChangeNickNameDialog;
import com.myapp.instantfoodsreviewapp.dialog.ChangePasswordDialog;
import com.myapp.instantfoodsreviewapp.dialog.SecessionDialog;
import com.myapp.instantfoodsreviewapp.dialog.TransferDataCallback;
import com.myapp.instantfoodsreviewapp.model.PImageData;
import com.myapp.instantfoodsreviewapp.model.AccountDto;
import com.myapp.instantfoodsreviewapp.model.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;
import com.myapp.instantfoodsreviewapp.utils.Const;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class MyPageFragment extends Fragment implements Button.OnClickListener {
    private static final String TAG = "MyPageFragment";
    private final String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";
    private static final String FILE_SPLIT_PART = "\\.";

    private static final int REQUEST_CAMERA = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    private TextView currentNickName;
    private TextView getEmail;
    private UserPreference userPreference;
    private ImageView profilePicture;
    private TransferDataCallback<String> imageResultCallback;
    private TransferDataCallback<String> profileImageDrawerCallback;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);
        init(rootView);
        getInfo(rootView);
        initButton(rootView);
        initPreference();
        getUserInfo();
        //initProfileImage();
        return rootView;
    }

    private void init(View view) {
        String versionName = BuildConfig.BUILD_TYPE;
        TextView currentVersion = view.findViewById(R.id.version_info);
        currentVersion.setText(versionName);
        profilePicture = view.findViewById(R.id.profile_image);
    }


    private void getInfo(View view) {
        currentNickName = view.findViewById(R.id.tv_setting_nickname);
        getEmail = view.findViewById(R.id.tv_setting_email);
    }


    private String makeThumbnailPath(String originalImagePath) {
        String thumbNailPath = "";
        if (originalImagePath != null && !originalImagePath.isEmpty()) {
            String[] pathNames = originalImagePath.split(FILE_SPLIT_PART);
            thumbNailPath = IMG_BASE_URL + pathNames[0] + "Thumbnail";
        } else {
            Log.d(TAG, "onFailure()");
        }
        return thumbNailPath;
    }


    private void initButton(View view) {
        TextView btnChangePassword = view.findViewById(R.id.tv_change_password);
        btnChangePassword.setOnClickListener(this);
        TextView btnSecession = view.findViewById(R.id.tv_secession);
        btnSecession.setOnClickListener(this);
        ImageButton btnChangeNickname = view.findViewById(R.id.btn_change_nickname);
        btnChangeNickname.setOnClickListener(this);
        ImageButton btnChangePicture = view.findViewById(R.id.btn_choose_picture);
        btnChangePicture.setOnClickListener(this);
        profilePicture.setOnClickListener(this);
    }

    private void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(getContext());
    }


    private void getUserInfo() {
        String getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<AccountDto> call = retrofitInterface.account(getToken);
        call.enqueue(new Callback<AccountDto>() {
            @Override
            public void onResponse(@NotNull Call<AccountDto> call, @NotNull Response<AccountDto> response) {
                if (response.isSuccessful()) {
                    AccountDto dto = response.body();
                    assert dto != null;
                    AccountDto.ResultData resultData = dto.getResultData();
                    if (resultData != null) {
                        String userEmail = resultData.getUser().getEmail();
                        getEmail.setText(userEmail);
                        String userNickName = resultData.getUser().getNickname();
                        currentNickName.setText(userNickName);
                        String userProfilePath = UserPreference.getInstance().getString(Config.KEY_PROFILE_IMAGE);
                        String storedThumbnail = makeThumbnailPath(userProfilePath);
                        setImageResource(storedThumbnail, profilePicture);

                    } else {
                        Toast.makeText(getActivity(), "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccountDto> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), "서버 에러", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_change_password) {
            openChangePasswordDialog();
        } else if (view.getId() == R.id.tv_secession) {
            openSecessionDialog();
        } else if (view.getId() == R.id.btn_change_nickname) {
            openChangeNickNameDialog();
        } else if (view.getId() == R.id.btn_choose_picture) {
            selectImage();
        }
    }

    private void openChangePasswordDialog() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.show(getParentFragmentManager(), "change password dialog");
    }

    private void openSecessionDialog() {
        SecessionDialog secessionDialog = new SecessionDialog();
        secessionDialog.show(getParentFragmentManager(), "secession dialog");
    }

    private void openChangeNickNameDialog() {
        ChangeNickNameDialog changeNickNameDialog = new ChangeNickNameDialog();
        TransferDataCallback<String> resultNickNameCallBack = changeNickName -> {
            currentNickName.setText(changeNickName);
            UserPreference.getInstance().putString(Config.KEY_NICKNAME, changeNickName);
            Toast.makeText(getContext(), changeNickName, Toast.LENGTH_SHORT).show();
        };
        changeNickNameDialog.setResultCallback(resultNickNameCallBack);
        changeNickNameDialog.show(getParentFragmentManager(), "change nickname dialog");
    }

    private void selectImage() {
        checkPermission();
        initProfileImage();
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                cameraIntent();
            } else if (items[item].equals("Choose from Library")) {
                galleryIntent();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void cameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Objects.requireNonNull(getActivity()).startActivityForResult(cameraIntent, REQUEST_CAMERA);

    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Objects.requireNonNull(getActivity()).startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }


    private void checkPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(Objects.requireNonNull(getActivity()))
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    Uri imageUri = data.getData();
                    String uriPath = getRealPathFromURI(imageUri);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    compressImage(uriPath);
                    setImageResource(imageUri.toString(), profilePicture);
                    updateData(bitmap);
                    break;

                case REQUEST_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    String uriPathPicture = MediaStore.Images.Media.insertImage(Objects.requireNonNull(getActivity()).getContentResolver(), bitmap, "title", null);
                    imageUri = Uri.parse(uriPathPicture);
                    compressImage(uriPathPicture);
                    setImageResource(imageUri.toString(), profilePicture);
                    updateData(bitmap);
                    break;
            }
        }
    }

    private void compressImage(String uriPath) {
        File file = new File(uriPath);
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        } catch (Throwable t) {
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }
    }

    private void setImageResource(String url, ImageView imageView) {
        Log.e("MyPage Glide", " " + url);
        Glide.with(this)
                .load(url)
                .circleCrop()
                .into(imageView);
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] filePathColumn = {MediaStore.Images.Thumbnails.DATA};
        CursorLoader loader = new CursorLoader(Objects.requireNonNull(getContext()), contentUri, filePathColumn, null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);

        cursor.close();
        return result;
    }


    private void initProfileImage() {
        imageResultCallback = userProfilePath -> {
            String storedThumbnail = makeThumbnailPath(userProfilePath);
            setImageResource(storedThumbnail, profilePicture);
        };
    }

    private void updateData(Bitmap bitmap) {
        MultipartBody.Part originFile = Const.bitmapConvertToFile(Objects.requireNonNull(getContext()), bitmap, 0);
        Bitmap thumbnail = Const.resizedThumbnail(bitmap, bitmap.getWidth(), bitmap.getHeight());
        MultipartBody.Part thumbnailFile = Const.bitmapConvertToFile(getContext(), thumbnail, 1);

        bitmap.recycle();
        thumbnail.recycle();

        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        String getToken = UserPreference.getInstance().getString(Config.KEY_TOKEN);


        Call<ApiResultDto> apiResultDtoCall = retrofitInterface.pimage(getToken, originFile, thumbnailFile);
        apiResultDtoCall.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(@NotNull Call<ApiResultDto> call, @NotNull Response<ApiResultDto> response) {
                if (response.isSuccessful()) {
                    ApiResultDto apiResultDto = response.body();
                    assert apiResultDto != null;
                    JsonObject resultData = apiResultDto.getResultData();
                    PImageData pImageData = new Gson().fromJson(resultData, PImageData.class);
                    String originalImage = IMG_BASE_URL + pImageData.getStoredPath();
                    Log.e("888 originalImage : ", " " + originalImage);
                    UserPreference.getInstance().putString(Config.KEY_PROFILE_IMAGE, pImageData.getStoredPath());
                    imageResultCallback.transfer(pImageData.getStoredPath());
                    profileImageDrawerCallback.transfer(pImageData.getStoredPath());

                } else {
                    Toast.makeText(getActivity(), "프로필 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiResultDto> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                //Logm 으로 하면 자동 Log가 입력이 된다. 이후 TAG 설정을 위해 Logt를 입력하여서 자동 완성 시킨다.
            }
        });
    }

    public void setProfileImageDrawerCallback(TransferDataCallback<String> profileImageDrawerCallback) {
        this.profileImageDrawerCallback = profileImageDrawerCallback;
    }
}