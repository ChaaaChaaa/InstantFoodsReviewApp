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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.Post;
import com.myapp.instantfoodsreviewapp.model.PostResponse;
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


public class WritePostFragment extends Fragment {

    private RatingBar ratingBar;
    private TextInputEditText detailPostTitle;
    private TextInputEditText detailPostGoodPoint;
    private TextInputEditText detailPostBadPoint;
    private ImageView detailPostImage = null;
    private UserPreference userPreference;
    private Bitmap getBitmap;
    private int productId;
    private String productName;
    private float rateValue;
    String setDetailPostTitle;
    String setDetailPostGoodPoint;
    String setDetailPostBadPoint;

    private final String TAG = "WritePostFragment";
    private static final String FILE_SPLIT_PART = "\\.";
    private static final int REQUEST_CAMERA = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_post, container, false);
        rootView.setDrawingCacheEnabled(false);
        super.onViewCreated(rootView, savedInstanceState);
        getProducts();
        initPreference();
        init(rootView);
        getRating();
        initButton(rootView);
        return rootView;
    }

    private void init(View view) {
        ratingBar = view.findViewById(R.id.review_rating_bar);
        TextView productTitle = view.findViewById(R.id.tv_product_title);
        productTitle.setText(productName);
        detailPostTitle = view.findViewById(R.id.et_detail_post_title);
        detailPostGoodPoint = view.findViewById(R.id.et_good_point_detail_post);
        detailPostBadPoint = view.findViewById(R.id.et_bad_point_detail_post);
    }

    private void getRating() {
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> rateValue = ratingBar.getRating());
    }

    private void getProducts() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        productId = bundle.getInt("ProductID");
        productName = bundle.getString("ProductName");
    }


    private void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(getContext());
    }

    private void initButton(View view) {
        detailPostImage = view.findViewById(R.id.iv_detail_post_image);
        detailPostImage.setOnClickListener(this::detailPostImageClick);
        Button detailPostConfirm = view.findViewById(R.id.btn_detail_post_write_confirm);
        detailPostConfirm.setOnClickListener(this::detailPostConfirmClick);
    }


    private void setWrite() {
        setDetailPostTitle = Objects.requireNonNull(detailPostTitle.getText()).toString();
        setDetailPostGoodPoint = Objects.requireNonNull(detailPostGoodPoint.getText()).toString();
        setDetailPostBadPoint = Objects.requireNonNull(detailPostBadPoint.getText()).toString();
    }


    public void detailPostImageClick(View v) {
        if (v.getId() == R.id.iv_detail_post_image) {
            selectImage();
        }
    }

    public void detailPostConfirmClick(View v) {
        if (v.getId() == R.id.btn_detail_post_write_confirm && isFillOutContents()) {
            setPostInfo(getBitmap);
        }
    }

    private boolean isFillOutContents() {
        if (getBitmap == null) {
            Toast.makeText(getContext(), "사진을 넣어주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ratingBar.getRating() == 0.0) {
            Toast.makeText(getContext(), "별점을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Objects.requireNonNull(detailPostTitle.getText()).toString().trim().length() == 0) {
            Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Objects.requireNonNull(detailPostGoodPoint.getText()).toString().trim().length() == 0) {
            Toast.makeText(getContext(), "장점을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Objects.requireNonNull(detailPostBadPoint.getText()).toString().trim().length() == 0) {
            Toast.makeText(getContext(), "단점을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
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


    private void selectImage() {
        checkPermission();
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


    public Bitmap fixOrientation(Bitmap bitmap) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return bitmap;
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
                        bitmap = fixOrientation(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    compressImage(uriPath);
                    setImageResource(imageUri.toString(), detailPostImage);
                    getBitmap = bitmap;
                    break;

                case REQUEST_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    String uriPathPicture = MediaStore.Images.Media.insertImage(Objects.requireNonNull(getActivity()).getContentResolver(), bitmap, "title", null);

                    imageUri = Uri.parse(uriPathPicture);
                    bitmap = fixOrientation(bitmap);

                    compressImage(uriPathPicture);
                    setImageResource(imageUri.toString(), detailPostImage);
                    getBitmap = bitmap;
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
        Glide.with(this)
                .load(url)
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


    private void setPostInfo(Bitmap bitmap) {
        setWrite();
        MultipartBody.Part originFile = Const.bitmapConvertToFile(Objects.requireNonNull(getContext()), bitmap, 0);
        Bitmap thumbnail = Const.resizedThumbnail(bitmap, bitmap.getWidth(), bitmap.getHeight());
        MultipartBody.Part thumbnailFile = Const.bitmapConvertToFile(getContext(), thumbnail, 1);
        bitmap.recycle();
        thumbnail.recycle();

        String getToken = userPreference.getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<PostResponse> postResponseCall = retrofitInterface.upload(getToken, setDetailPostTitle, setDetailPostGoodPoint, setDetailPostBadPoint, rateValue, productId, originFile, thumbnailFile);
        postResponseCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    PostResponse postResponse = response.body();
                    Log.e("postResponse", "" + postResponse);
                    goToBackFragment();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    private void goToBackFragment() {
        WritePostFragment writePostFragment = new WritePostFragment();
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(writePostFragment);
        fragmentTransaction.commit();
        fragmentManager.popBackStack();
    }
}