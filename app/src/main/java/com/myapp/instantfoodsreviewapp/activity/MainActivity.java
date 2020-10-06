package com.myapp.instantfoodsreviewapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
=======
import android.view.MenuItem;
>>>>>>> feature/11
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.model.UserAccountData;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
=======
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.myapp.instantfoodsreviewapp.adapter.CustomRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.dialog.TransferDataCallback;
import com.myapp.instantfoodsreviewapp.fragment.WritePostFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListDdokbokkiFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListDumplingFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListFriedRiceFragment;
import com.myapp.instantfoodsreviewapp.fragment.HomeFragment;
import com.myapp.instantfoodsreviewapp.fragment.MyPageFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListNoodleFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListPizzaFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListStewFragment;
import com.myapp.instantfoodsreviewapp.model.entity.AccountDto;
>>>>>>> feature/11
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.BasicAuthInterceptor;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;
<<<<<<< HEAD
import com.myapp.instantfoodsreviewapp.utils.Const;
=======
>>>>>>> feature/11
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = EmailLoginActivity.class.getSimpleName();
    private String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";
    private static final String FILE_SPLIT_PART = "\\.";

    private ImageView navProfileImage;
    private TextView navNickName;

<<<<<<< HEAD
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {
    private String TAG = EmailLoginActivity.class.getSimpleName();

    private TextView getNickName;
=======
>>>>>>> feature/11
    private Button logoutButton;
    private ActivityMainBinding activityMainBinding;
    private UserPreference userPreference;
    private String getToken;
    private RetrofitClient retrofitClient;
    private BasicAuthInterceptor basicAuthInterceptor;
<<<<<<< HEAD
=======
    private ActionBarDrawerToggle toggle;
    private Toolbar mainToolbar;
    private NavigationView mainNavigationView;
    private DrawerLayout drawerLayout;
    private ConstraintLayout llDrawerHeader;
    // private ViewPagerAdapter viewPagerAdapter;
    //  private ViewPager viewPager;
    private CustomRecyclerAdapter adapterCustom;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private TransferDataCallback<String> profileImageDrawerCallback;
    private TransferDataCallback<Integer> categoryCallback;
    private String getProfileImagePath = "";

>>>>>>> feature/11

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initPreference();
<<<<<<< HEAD
        getNickName = activityMainBinding.getNickName;
        logoutButton = activityMainBinding.logoutButton;
        logoutButton.setOnClickListener(this);
        getUser();
    }

//    void bringNickName() {
//        String nickName = Objects.requireNonNull(getIntent().getExtras()).getString("TOKEN");
//
//        if (Const.isNullOrEmptyString(nickName)) {
//            getNickName.setText(nickName);
//        } else {
//            getNickName.setText("입력받은 값이 없습니다.");
//        }
//    }

=======
        init();
        setNavigationView();
        setToolbar();
        bindView();
        showNavigationUserInfo();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, mainToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            mainNavigationView.setCheckedItem(R.id.nav_home);
        }

        getUser();

    }



    private void init() {
        drawerLayout = activityMainBinding.drawerLayout;
        mainNavigationView = findViewById(R.id.main_navigation);
    }


    private void initProfileDrawerImage() {
        profileImageDrawerCallback = new TransferDataCallback<String>() {
            @Override
            public void transfer(String imagePath) {
                getProfileImagePath = imagePath;
                String convertThumbnailProfileImagePath = makeThumbnailPath(getProfileImagePath);
                setNavigationImage(convertThumbnailProfileImagePath);
            }
        };
    }

    private void setNavigationView() {
        mainNavigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
    }

    private void setToolbar() {
        mainToolbar = activityMainBinding.mainToolbar;
        setSupportActionBar(mainToolbar);
    }

    private static long backKeyPressedTime = 0;
    private boolean userPressedBackAgain;
    private static final int TIME_INTERVAL = 1000;
    private Toast toast;

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (count > 0) {
            for (int i = 0; i < count; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        } else if ((backKeyPressedTime + TIME_INTERVAL > System.currentTimeMillis()) || count < 1) {
            // getSupportFragmentManager().popBackStack();
            finishLaunchActivity();
            super.onBackPressed();
        } else {
            toast = Toast.makeText(getBaseContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            super.onBackPressed();
            backKeyPressedTime = System.currentTimeMillis();
        }
    }

    private void finishLaunchActivity() {
        Intent launchNextActivity = new Intent(Intent.ACTION_MAIN);
        launchNextActivity.addCategory(Intent.CATEGORY_HOME);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //현재테스크 모두 비움
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launchNextActivity); //새로 태크스 만들어 실행
        finish();
    }

    private boolean flagFragmentStack() {
        return (fragment != null) && (fragment.getChildFragmentManager().getBackStackEntryCount() > 0);
    }

>>>>>>> feature/11
    public void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }

<<<<<<< HEAD
    public void userLogOut(View view) {
=======
    private void bindView() {
        View header = mainNavigationView.getHeaderView(0);
        llDrawerHeader = header.findViewById(R.id.llDrawerHeader);
        navNickName = header.findViewById(R.id.nav_nickname);
        navProfileImage = header.findViewById(R.id.nav_profile_image);
    }

    private void showNavigationUserInfo() {
        String getNickName = UserPreference.getInstance().getString(Config.KEY_NICKNAME);
        navNickName.setText(getNickName);
        initProfileDrawerImage();

    }

    private String makeThumbnailPath(String originalImagePath) {
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

    private void setNavigationImage(String url) {
        Glide.with(this)
                .load(url)
                .override(300, 300)
                .circleCrop()
                .into(navProfileImage);
    }

    public void userLogOut() {
>>>>>>> feature/11
        userPreference.setLoggedIn(getApplicationContext(), false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void getUser() {
        getToken = userPreference.getInstance().getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<AccountDto> call = retrofitInterface.account(getToken);

        UserPreference.getInstance().putString(Config.KEY_TOKEN, getToken);
        Log.e("token", "" + getToken);

        call.enqueue(new Callback<AccountDto>() {
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                if (response.isSuccessful()) {
                    AccountDto accountDto = response.body();
                    AccountDto.ResultData resultData = accountDto.getResultData();

                    if (resultData != null) {
                        String userEmail = accountDto.getResultData().getUser().getEmail();
                        String userNickName = accountDto.getResultData().getUser().getNickname();
                        String userProfileImage = accountDto.getResultData().getUser().getProfilepath();
                        profileImageDrawerCallback.transfer(userProfileImage);

                        UserPreference.getInstance().putString(Config.KEY_EMAIL, userEmail);
                        UserPreference.getInstance().putString(Config.KEY_NICKNAME, userNickName);
                        UserPreference.getInstance().putString(Config.KEY_PROFILE_IMAGE, userProfileImage);
                        // String UserPreference.getInstance().getString(Config.KEY_PROFILE_IMAGE);

//                        Log.e("userEmail", "" + userEmail);
//                        Log.e("userNickName", "" + userNickName);
//                        Log.e("userProfileImage", "" + userProfileImage);

                    } else {
                        Log.e("getUser", "Account null ");
                    }


                    if (getToken != null) {


                    }
                    Toast.makeText(getApplicationContext(), "토큰 저장 성공", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountDto> call, Throwable t) {
                Toast.makeText(getApplication(), "토큰 저장 실패", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private static final int RICE_CATEGORY = 1;
    private static final int DDOCK_CATEGORY = 2;
    private static final int NOODLE_CATEGORY = 3;
    private static final int DUMPLING_CATEGORY = 4;
    private static final int PIZZA_CATEGORY = 5;
    private static final int STEW_CATEGORY = 6;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_stew:
                fragment = new ProductListStewFragment();
                break;
            case R.id.nav_noodle:
                fragment = new ProductListNoodleFragment();
                break;

            case R.id.nav_ddokbokki:
                fragment = new ProductListDdokbokkiFragment();
                break;
            case R.id.nav_dumpling:
                fragment = new ProductListDumplingFragment();
                break;
            case R.id.nav_friedRice:
                fragment = new ProductListFriedRiceFragment();
              //  ((ProductListFriedRiceFragment) (fragment)).setCategoryCallback(categoryCallback);
                break;
            case R.id.nav_pizza:
                fragment = new ProductListPizzaFragment();
                break;
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_my_page:
                fragment = new MyPageFragment();
                //((MyPageFragment) (fragment)).setResultCallback(profileImageDrawerCallback);
                ((MyPageFragment) (fragment)).setProfileImageDrawerCallback(profileImageDrawerCallback);
                break;
            case R.id.nav_write_review:
                fragment = new WritePostFragment();
                break;
            case R.id.nav_logout:
                userLogOut();
                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragment.getTag())
                    .addToBackStack(null)
                    .commit();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment myPageFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        myPageFragment.onActivityResult(requestCode, resultCode, data);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    private void getUser() {

        getToken = userPreference.getString(Config.KEY_TOKEN);

        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.account(getToken);

        call.enqueue(new Callback<ApiResultDto>() {

            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                if (response.isSuccessful()) {
                    ApiResultDto dto = response.body();
                    JsonObject resultData = dto.getResultData(); //실제 데이터부를 json으로 받고
                    if(resultData!=null){
                        //받은 json을 내가만든 UserAccountData 클래스형태로 convert
                        UserAccountData userAccountData = new Gson().fromJson(resultData,UserAccountData.class);
                        //String nickName =response.body().getNickname();
                        // Log.i(TAG, "Responser: " + response.body());
                        getNickName.setText(userAccountData.getNickname());
                    }
                    else{
                        Log.e("getUser","Account null ");

                    }



                UserPreference userPreference = new UserPreference();
                if(getToken != null){
                     userPreference.putString(Config.KEY_TOKEN,getToken);
                }
                    Toast.makeText(getApplicationContext(), "토큰 저장 성공", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResultDto> call, Throwable t) {
                Toast.makeText(getApplication(), "토큰 저장 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
=======
}
>>>>>>> feature/11
=======
//    public TransferDataCallback<Integer> getResultCallback() {
//        return categoryCallback;
//    }
//
//    public void setResultCallback(TransferDataCallback<Integer> categoryCallback) {
//        this.categoryCallback = categoryCallback;
//    }

}
>>>>>>> feature/14
