//package com.myapp.instantfoodsreviewapp.fragment;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.Fragment;
//import androidx.loader.content.CursorLoader;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;
//import com.myapp.instantfoodsreviewapp.R;
//import com.myapp.instantfoodsreviewapp.activity.FullImageActivity;
//import com.myapp.instantfoodsreviewapp.model.PImageData;
//import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
//import com.myapp.instantfoodsreviewapp.preference.UserPreference;
//import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
//import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
//import com.myapp.instantfoodsreviewapp.utils.Config;
//import com.myapp.instantfoodsreviewapp.utils.Const;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class ProfileFragment extends Fragment implements Button.OnClickListener {
//
//    private static final String TAG = "ProfileFragment";
//    private ImageView profileView;
//    private ImageButton imageButton;
//    // private Bitmap cameraBitmap;
//
//    private String IMG_BASE_URL = "http://www.ppizil.kro.kr/review/file/";
//    private static final String FILE_SPLIT_PART = "\\.";
//
//
//    private static final int REQUEST_CAMERA = 101;
//    private static final int PICK_IMAGE_REQUEST = 102;
//    private static final int REQUEST_CODE_CROP_IMAGE = 103;
//
//
//    String cameraPermissions[];
//    String storagePermissions[];
//
//    Uri imageUri;
//    File photoFile;
//    String imageUriRetrofit;
//
//    private Bitmap bitmap;
//    SharedPreferences sharedPreferences;
//
//    public ProfileFragment() {
//        // Required empty public constructor
//        super();
//    }
//
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.id.profile_image, container, false);
//        bindView(view);
//        imageButton.setOnClickListener(this);
//        profileView.setOnClickListener(this);
//
//        return view;
//    }
//
//    private void bindView(View view) {
//        profileView = view.findViewById(R.id.profile_image);
//        imageButton = view.findViewById(R.id.btn_choose_picture);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_choose_picture :
//                selectImage();
//                break;
//
//            case R.id.profile_image:
//                setFullImageActivity();
//                break;
//        }
//    }
//
//    private void setFullImageActivity(){
//        Intent sendOriginalImageIntent = new Intent(getActivity(), FullImageActivity.class);
//        String originalImagePath = UserPreference.getInstance().getString("PROFILE_IMAGE_PATH");
//        sendOriginalImageIntent.putExtra("originalImage",originalImagePath);
//        startActivity(sendOriginalImageIntent);
//    }
//
//    private void selectImage() {
//        checkPermission();
//        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Take Photo")) {
//                    cameraIntent();
//                } else if (items[item].equals("Choose from Library")) {
//                    galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//
//    private void cameraIntent() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        getActivity().startActivityForResult(cameraIntent, REQUEST_CAMERA);
//
//    }
//
//    private void galleryIntent() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        getActivity().startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
//    }
//
//
//    private void checkPermission() {
//        PermissionListener permissionListener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        TedPermission.with(getActivity())
//                .setPermissionListener(permissionListener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                .check();
//    }
//
//
//
//    public Bitmap fixOrientation(Bitmap bitmap) {
//        if (bitmap.getWidth() > bitmap.getHeight()) {
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        }
//
//        return bitmap;
//    }
//
//
//
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Log.e("aaa", requestCode + "," + resultCode);
//        if (resultCode != getActivity().RESULT_CANCELED) {
//            switch (requestCode) {
//                case PICK_IMAGE_REQUEST:
//                    imageUri = data.getData();
//                    String uriPath = getRealPathFromURI(imageUri);
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    fixOrientation(bitmap);
//                    String imageUriToString = imageUri.toString();
//                    setImageResource(imageUriToString,profileView);
//                    updateData(bitmap);
//                    break;
//
//                case REQUEST_CAMERA:
//                    bitmap = (Bitmap) data.getExtras().get("data");
//                    String uriPathPicture = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmap,"title",null);
//                    bitmap = fixOrientation(bitmap);
//                    compressImage(uriPathPicture);
//                    String uriPathReal = getRealPathFromURI(imageUri);
//                    updateData(bitmap);
//                    break;
//            }
//        }
//    }
//
//    private void compressImage(String uriPath) {
//        File file = new File(uriPath);
//        try {
//            bitmap = BitmapFactory.decodeFile(file.getPath());
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
//        } catch (Throwable t) {
//            Log.e("ERROR", "Error compressing file." + t.toString());
//            t.printStackTrace();
//        }
//    }
//
//    private void setImageResource(String url, ImageView imageView){
//        Glide.with(this)
//                .load(url)
//                .circleCrop()
//                .into(imageView);
//    }
//
//
//
//    private String getRealPathFromURI(Uri contentUri) {
//        //String[] filePathColumn = {MediaStore.Images.Media.DATA};
//        String[] filePathColumn = {MediaStore.Images.Thumbnails.DATA};
//        CursorLoader loader = new CursorLoader(getContext(), contentUri, filePathColumn, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//
//        cursor.close();
//        return result;
//    }
//
//    private UserPreference userPreference;
//
//    private void updateData(Bitmap bitmap) {
//
//        MultipartBody.Part originFile = Const.bitmapConvertToFile(getContext(),bitmap,0);
//        Bitmap thumbnail = Const.resizedThumbnail(bitmap,bitmap.getWidth(),bitmap.getHeight());
//        MultipartBody.Part thumbnailFile = Const.bitmapConvertToFile(getContext(),thumbnail,1);
//
//        bitmap.recycle();
//        thumbnail.recycle();
//
//        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
//        String getToken = userPreference.getInstance().getString(Config.KEY_TOKEN);
//
//        // Log.e("connect00", "lll");
//        Call<ApiResultDto> apiResultDtoCall = retrofitInterface.pimage(getToken, originFile, thumbnailFile);
//        // Log.e("connect0", "aaaaa");
//        apiResultDtoCall.enqueue(new Callback<ApiResultDto>() {
//            @Override
//            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
//                //     Log.e("connect1", "aaaa" + response);
//                if (response.isSuccessful()) {
//                    //       Log.e("connect1", "vvvv" + response);
//                    ApiResultDto apiResultDto = response.body();
//                    JsonObject resultData = apiResultDto.getResultData();
//
//                    PImageData pImageData = new Gson().fromJson(resultData, PImageData.class);
//
//                    String originalImage = IMG_BASE_URL+pImageData.getStoredPath();
//                    UserPreference.getInstance().putString("PROFILE_IMAGE_PATH",pImageData.getStoredPath());
//                    String originalImagePath = userPreference.getInstance().getString("PROFILE_IMAGE_PATH");
//
//                    if(originalImagePath != null && originalImagePath.isEmpty()){
//                        String[] pathNames = originalImagePath.split(FILE_SPLIT_PART);
//                        String thumbNailPath = pathNames[0]+"Thumbnail";
//                        setImageResource(IMG_BASE_URL+thumbNailPath,profileView);
//                    }
//                    Toast.makeText(getActivity(),originalImagePath,Toast.LENGTH_SHORT).show();
//                    //Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_LONG).show();
//                }
//                else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResultDto> call, Throwable t) {
//                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
//                //Logm 으로 하면 자동 Log가 입력이 된다. 이후 TAG 설정을 위해 Logt를 입력하여서 자동 완성 시킨다.
//            }
//        });
//
//
//    }
//}
