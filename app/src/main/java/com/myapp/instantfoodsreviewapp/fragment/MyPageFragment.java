package com.myapp.instantfoodsreviewapp.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.myapp.instantfoodsreviewapp.BuildConfig;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.activity.FullImageActivity;
import com.myapp.instantfoodsreviewapp.dialog.ChangeNickNameDialog;
import com.myapp.instantfoodsreviewapp.dialog.ChangePasswordDialog;
import com.myapp.instantfoodsreviewapp.dialog.SecessionDialog;
import com.myapp.instantfoodsreviewapp.dialog.TransferDataCallback;
import com.myapp.instantfoodsreviewapp.model.PImageData;
import com.myapp.instantfoodsreviewapp.model.User;
import com.myapp.instantfoodsreviewapp.model.entity.AccountDto;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;
import com.myapp.instantfoodsreviewapp.utils.Const;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class MyPageFragment extends Fragment implements Button.OnClickListener {
    private static final String TAG = "MyPageFragment";

    private String IMG_BASE_URL = "http://www.ppizil.kro.kr/review/file/";
    private static final String FILE_SPLIT_PART = "\\.";

    private static final int REQUEST_CAMERA = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    Uri imageUri;
    private Bitmap bitmap;

    private TextView currentVersion;
    private TextView currentNickName;
    private TextView getEmail;
    private TextView btnChangePassword;
    private TextView btnSecession;
    private UserPreference userPreference;
    private ImageButton btnChangeNickname;
    private ImageButton btnChangePicture;
    private ImageView profilePicture;
    private TransferDataCallback<String> resultCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);
        init(rootView);
        getInfo(rootView);
        initButton(rootView);
        initPreference();
        getUserInfo();
        return rootView;
    }

    private void init(View view) {
        String versionName = BuildConfig.VERSION_NAME;
        currentVersion = view.findViewById(R.id.version_info);
        currentVersion.setText(versionName);

        profilePicture = view.findViewById(R.id.profile_image);
        // String thumbNailPath = makeThumbnailPath();
//        Log.e("thumbNailPath1",thumbNailPath);
//        setImageResource(thumbNailPath,profilePicture);
    }

    private void getInfo(View view) {
        String userNickname = UserPreference.getInstance().getString(Config.KEY_NICKNAME);
        Log.e("userNickname3", "" + userNickname);
        currentNickName = view.findViewById(R.id.tv_setting_nickname);

        // String thumbNailPath = makeThumbnailPath();
        String userProfileImage = UserPreference.getInstance().getString(Config.KEY_PROFILE_IMAGE);
        Log.e("userProfileImage3", "" + userProfileImage);
        // setImageResource(thumbNailPath,profilePicture);

        String userEmail = UserPreference.getInstance().getString(Config.KEY_EMAIL);
        Log.e("userEmail3", "" + userEmail);
        getEmail = view.findViewById(R.id.tv_setting_email);
    }


    private String makeThumbnailPath(String originalImagePath) {
        // String originalImagePath = UserPreference.getInstance().getString("PROFILE_IMAGE_PATH");
        //Log.e("originalImagePath",""+originalImagePath);
        String thumbNailPath = "";
        if (originalImagePath != null && !originalImagePath.isEmpty()) {
            String[] pathNames = originalImagePath.split(FILE_SPLIT_PART);
            thumbNailPath = IMG_BASE_URL + pathNames[0] + "Thumbnail";
            Log.e("thumbNailPath", "" + thumbNailPath);
        } else {
            Log.d(TAG, "onFailure()");
        }
        Log.e("thumbNailPath2", "" + thumbNailPath);
        return thumbNailPath;
    }


    private void initButton(View view) {
        btnChangePassword = view.findViewById(R.id.tv_change_password);
        btnChangePassword.setOnClickListener(this);
        btnSecession = view.findViewById(R.id.tv_secession);
        btnSecession.setOnClickListener(this);
        btnChangeNickname = view.findViewById(R.id.btn_change_nickname);
        btnChangeNickname.setOnClickListener(this);
        btnChangePicture = view.findViewById(R.id.btn_choose_picture);
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
        //Call<ApiResultDto> call = retrofitInterface.account(getToken);
        Call<AccountDto> call = retrofitInterface.account(getToken);
        call.enqueue(new Callback<AccountDto>() {
            @Override
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                if (response.isSuccessful()) {
                    AccountDto dto = response.body();
                    AccountDto.ResultData resultData = dto.getResultData();
                    if (resultData != null) {
                        String userEmail = resultData.getUser().getEmail();
                        getEmail.setText(userEmail);
                        String userNickName = resultData.getUser().getNickname();
                        currentNickName.setText(userNickName);
                        String userProfilePath = resultData.getUser().getProfilepath();
                        UserPreference.getInstance().putString(Config.KEY_PROFILE_IMAGE, userProfilePath);
                        Log.e("userProfilePath1", "" + userProfilePath);
                        String storedThumbnail = makeThumbnailPath(userProfilePath);
                        Log.e("userProfilePath2", "" + storedThumbnail);
                        setImageResource(storedThumbnail, profilePicture);


                    } else {
                        Toast.makeText(getActivity(), "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountDto> call, Throwable t) {

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

            case R.id.btn_change_nickname:
                openChangeNickNameDialog();
                break;

            case R.id.btn_choose_picture:
                selectImage();
                break;

            case R.id.profile_image:
                setFullImageActivity();
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

    private void openChangeNickNameDialog() {
        ChangeNickNameDialog changeNickNameDialog = new ChangeNickNameDialog();
        TransferDataCallback<String> resultNickNameCallBack = new TransferDataCallback<String>() {
            @Override
            public void transfer(String changeNickName) {
                currentNickName.setText(changeNickName);
                Toast.makeText(getContext(), changeNickName, Toast.LENGTH_SHORT).show();
            }
        };
        changeNickNameDialog.setResultCallback(resultNickNameCallBack);
        changeNickNameDialog.show(getFragmentManager(), "change nickname dialog");
    }

    private void setFullImageActivity() {
        Intent sendOriginalImageIntent = new Intent(getActivity(), FullImageActivity.class);
        String originalImagePath = UserPreference.getInstance().getString(Config.KEY_PROFILE_IMAGE);
        sendOriginalImageIntent.putExtra("originalImage", originalImagePath);
        startActivity(sendOriginalImageIntent);
    }

    private void selectImage() {
        checkPermission();
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void cameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(cameraIntent, REQUEST_CAMERA);

    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
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

        TedPermission.with(getActivity())
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


    public Bitmap fixOrientation(Bitmap bitmap) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return bitmap;
    }

    private void transferUpdatePicture(){
        TransferDataCallback<String> resultProfileImageCallBack = new TransferDataCallback<String>() {
            @Override
            public void transfer(String updatePicturePath) {

            }
        };
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("aaa", requestCode + "," + resultCode);
        if (resultCode != RESULT_CANCELED) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    imageUri = data.getData();

                    Log.e("bbb", "onActivityResult: " + imageUri);
                    String uriPath = getRealPathFromURI(imageUri);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        bitmap = fixOrientation(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    compressImage(uriPath);
                    setImageResource(imageUri.toString(), profilePicture);
                    updateData(bitmap);
                   // setResultCallback(resultProfileImageCallBack);
                    break;

                case REQUEST_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");


                    String uriPathPicture = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "title", null);

                    imageUri = Uri.parse(uriPathPicture);
                    bitmap = fixOrientation(bitmap);

                    compressImage(uriPathPicture);
                    String uriPathReal = getRealPathFromURI(imageUri);
                    setImageResource(imageUri.toString(), profilePicture);
                    updateData(bitmap);
                    break;
            }
        }
    }

    private void compressImage(String uriPath) {
        File file = new File(uriPath);
        try {
            bitmap = BitmapFactory.decodeFile(file.getPath());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        } catch (Throwable t) {
            Log.e("ERROR", "Error compressing file." + t.toString());
            t.printStackTrace();
        }
    }

    private void setImageResource(String url, ImageView imageView) {
        Log.e("Glide", " ");
        Glide.with(this)
                .load(url)
                .circleCrop()
                .into(imageView);
    }


    private String getRealPathFromURI(Uri contentUri) {
        //String[] filePathColumn = {MediaStore.Images.Media.DATA};
        String[] filePathColumn = {MediaStore.Images.Thumbnails.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, filePathColumn, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);

        cursor.close();
        return result;
    }


    private void updateData(Bitmap bitmap) {

        MultipartBody.Part originFile = Const.bitmapConvertToFile(getContext(), bitmap, 0);
        Bitmap thumbnail = Const.resizedThumbnail(bitmap, bitmap.getWidth(), bitmap.getHeight());
        MultipartBody.Part thumbnailFile = Const.bitmapConvertToFile(getContext(), thumbnail, 1);

        bitmap.recycle();
        thumbnail.recycle();

        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        String getToken = userPreference.getInstance().getString(Config.KEY_TOKEN);

        Log.e("connect", "000");
        Call<ApiResultDto> apiResultDtoCall = retrofitInterface.pimage(getToken, originFile, thumbnailFile);
        Log.e("connect", "111");
        apiResultDtoCall.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                Log.e("connect", "222" + response);
                if (response.isSuccessful()) {
                    Log.e("connect", "333" + response);
                    ApiResultDto apiResultDto = response.body();
                    JsonObject resultData = apiResultDto.getResultData();

                    PImageData pImageData = new Gson().fromJson(resultData, PImageData.class);

                    String originalImage = IMG_BASE_URL + pImageData.getStoredPath();
                    resultCallback.transfer(pImageData.getStoredPath());
                    UserPreference.getInstance().putString(Config.KEY_PROFILE_IMAGE, pImageData.getStoredPath());

                    Log.e("originalImage", "" + originalImage);
                    Log.e("PROFILE_IMAGE_PATH", "" + pImageData.getStoredPath());
                    Log.e("userpreference", "" + UserPreference.getInstance().getString(Config.KEY_PROFILE_IMAGE));

                    String originalImagePath = userPreference.getInstance().getString("PROFILE_IMAGE_PATH");

                    String convertedThumbnailPath = makeThumbnailPath(originalImagePath);
                    setImageResource(convertedThumbnailPath, profilePicture);
                } else {
                    Toast.makeText(getActivity(), "프로필 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResultDto> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                //Logm 으로 하면 자동 Log가 입력이 된다. 이후 TAG 설정을 위해 Logt를 입력하여서 자동 완성 시킨다.
            }
        });
    }


    public TransferDataCallback<String> getResultCallback() {
        return resultCallback;
    }

    public void setResultCallback(TransferDataCallback<String> resultCallback) {
        this.resultCallback = resultCallback;
    }
}
