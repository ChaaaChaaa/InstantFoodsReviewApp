package com.myapp.instantfoodsreviewapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityMainBinding;
import com.myapp.instantfoodsreviewapp.dialog.ChangeNickNameDialog;
import com.myapp.instantfoodsreviewapp.dialog.TransferDataCallback;
import com.myapp.instantfoodsreviewapp.fragment.HomeFragment;
import com.myapp.instantfoodsreviewapp.fragment.MyPageFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListDdokbokkiFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListDumplingFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListFriedRiceFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListNoodleFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListPizzaFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListStewFragment;
import com.myapp.instantfoodsreviewapp.model.entity.AccountDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = EmailLoginActivity.class.getSimpleName();
    private static final String FILE_SPLIT_PART = "\\.";

    private ImageView navProfileImage;
    private TextView navNickName;
    private ActivityMainBinding activityMainBinding;
    private UserPreference userPreference;
    private ActionBarDrawerToggle toggle;
    private Toolbar mainToolbar;
    private NavigationView mainNavigationView;
    private DrawerLayout drawerLayout;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private TransferDataCallback<String> profileImageDrawerCallback;
    private TransferDataCallback<String> nickNameDrawerCallback;
    private String getProfileImagePath = "";
    private String getCurrentNickName = "";
    private ConstraintLayout llDrawerHeader;
    private ChangeNickNameDialog changeNickNameDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initPreference();
        init();
        setNavigationView();
        setToolbar();
        showNavigationUserInfo();
        bindView();
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
        //mainNavigationView = activityMainBinding.mainNavigation;
        mainNavigationView = findViewById(R.id.main_navigation);
    }

    private void initProfileDrawerImage() {
        profileImageDrawerCallback = new TransferDataCallback<String>() {
            @Override
            public void transfer(String imagePath) {
                String convertThumbnailProfileImagePath = null;
                if (imagePath != null) {
                    getProfileImagePath = imagePath;
                    convertThumbnailProfileImagePath = makeThumbnailPath(getProfileImagePath);
                    //setNavigationImage(convertThumbnailProfileImagePath);
                }

                setNavigationImage(convertThumbnailProfileImagePath);
            }
        };
    }

//    private void openChangeNickNameDialog() {
//        ChangeNickNameDialog changeNickNameDialog = new ChangeNickNameDialog();
//        nickNameDrawerCallback = new TransferDataCallback<String>() {
//            @Override
//            public void transfer(String currentNickName) {
//              if(currentNickName != null){
//                  navNickName.setText(currentNickName);
//              }
//            }
//        };
//        changeNickNameDialog.setResultCallback(resultNickNameCallBack);
//    }


    private void openChangeNickNameDialog() {
        changeNickNameDialog = new ChangeNickNameDialog();
        nickNameDrawerCallback = new TransferDataCallback<String>() {
            @Override
            public void transfer(String changeNickName) {
                navNickName.setText(changeNickName);
                UserPreference.getInstance().putString(Config.KEY_NICKNAME, changeNickName);
            }
        };
        changeNickNameDialog.setNickNameDrawerCallback(nickNameDrawerCallback);
    }


    private void showNavigationUserInfo() {
        //String getNickName = UserPreference.getInstance().getString(Config.KEY_NICKNAME);
        //navNickName.setText(getNickName);
        openChangeNickNameDialog();
        initProfileDrawerImage();
    }

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "R.id.nav_profile_image", this.getPackageName());
        return drawableResourceId;
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
    private static final int TIME_INTERVAL = 1000;


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (count == 1) {
            Toast toast = Toast.makeText(getBaseContext(), "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            super.onBackPressed();
            backKeyPressedTime = System.currentTimeMillis();
        } else if ((backKeyPressedTime + TIME_INTERVAL > System.currentTimeMillis()) || count < 1) {
            // getSupportFragmentManager().popBackStack();
            finishLaunchActivity();
            super.onBackPressed();
        } else {
            super.onBackPressed();
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

    public void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }

    private void bindView() {
        View header = mainNavigationView.getHeaderView(0);
        //Header
        llDrawerHeader = header.findViewById(R.id.llDrawerHeader);
        navNickName = header.findViewById(R.id.nav_nickname);
        navProfileImage = header.findViewById(R.id.nav_profile_image);
    }


    private String makeThumbnailPath(String originalImagePath) {
        String thumbNailPath = "";
        if (originalImagePath != null && !originalImagePath.isEmpty()) {
            String[] pathNames = originalImagePath.split(FILE_SPLIT_PART);
            String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";
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
                .placeholder(R.drawable.ic_default_profile_image)
                .into(navProfileImage);
    }

    public void userLogOut() {
        userPreference.setLoggedIn(getApplicationContext(), false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void getUser() {
        String getToken = UserPreference.getInstance().getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<AccountDto> call = retrofitInterface.account(getToken);
        UserPreference.getInstance().putString(Config.KEY_TOKEN, getToken);
        Log.e("token", "" + getToken);

        call.enqueue(new Callback<AccountDto>() {
            public void onResponse(@NotNull Call<AccountDto> call, @NotNull Response<AccountDto> response) {
                if (response.isSuccessful()) {
                    AccountDto accountDto = response.body();
                    assert accountDto != null;
                    AccountDto.ResultData resultData = accountDto.getResultData();

                    if (resultData != null) {

                        String userEmail = accountDto.getResultData().getUser().getEmail();
                        String userNickName = accountDto.getResultData().getUser().getNickname();
                        Log.e("1userNickName", "" + userNickName);
                        String userProfileImage = accountDto.getResultData().getUser().getProfilepath();
                        Log.e("1userProfileImage", "" + userProfileImage);

                        profileImageDrawerCallback.transfer(userProfileImage);
                        nickNameDrawerCallback.transfer(userNickName);

                        UserPreference.getInstance().putString(Config.KEY_EMAIL, userEmail);
                        //UserPreference.getInstance().putString(Config.KEY_NICKNAME, userNickName);
                        UserPreference.getInstance().putString(Config.KEY_PROFILE_IMAGE, userProfileImage);
                        // String UserPreference.getInstance().getString(Config.KEY_PROFILE_IMAGE);

                        Log.e("userEmail", "" + userEmail);
                        Log.e("2userNickName", "" + userNickName);
                        Log.e("2userProfileImage", "" + userProfileImage);

                    } else {
                        Log.e("getUser", "Account null ");
                    }

                    Toast.makeText(getApplicationContext(), "토큰 저장 성공", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccountDto> call, @NotNull Throwable t) {
                Toast.makeText(getApplication(), "토큰 저장 실패", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private static final int RICE_CATEGORY = R.id.nav_friedRice;
    private static final int DDOCK_CATEGORY = R.id.nav_ddokbokki;
    private static final int NOODLE_CATEGORY = R.id.nav_noodle;
    private static final int DUMPLING_CATEGORY = R.id.nav_dumpling;
    private static final int PIZZA_CATEGORY = R.id.nav_pizza;
    private static final int STEW_CATEGORY = R.id.nav_stew;
    private static final int HOME_CATEGORY = R.id.nav_home;
    private static final int MyPage_CATEGORY = R.id.nav_my_page;
    private static final int LogOut_CATEGORY = R.id.nav_logout;

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        int menuItemId = menuItem.getItemId();
//
//        if(menuItemId == RICE_CATEGORY ){
//            fragment = new ProductListStewFragment();
//        }
//        else if(menuItemId == DDOCK_CATEGORY){
//            fragment = new ProductListDdokbokkiFragment();
//        }
//        else if(menuItemId ==NOODLE_CATEGORY ){
//            fragment = new ProductListNoodleFragment();
//        }
//        else if(menuItemId == DUMPLING_CATEGORY){
//            fragment = new ProductListDumplingFragment();
//        }
//        else if(menuItemId ==PIZZA_CATEGORY ){
//            fragment = new ProductListPizzaFragment();
//        }
//        else if(menuItemId == STEW_CATEGORY){
//            fragment = new ProductListStewFragment();
//        }
//        else if(menuItemId == HOME_CATEGORY){
//            fragment = new HomeFragment();
//        }
//        else if(menuItemId == MyPage_CATEGORY){
//            fragment = new MyPageFragment();
//            ((MyPageFragment) (fragment)).setProfileImageDrawerCallback(profileImageDrawerCallback);
//        }
//        else if(menuItemId == LogOut_CATEGORY){
//            userLogOut();
//        }
//
//
//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment, fragment.getTag())
//                    .addToBackStack(null)
//                    .commit();
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }


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
                ((MyPageFragment) (fragment)).setProfileImageDrawerCallback(profileImageDrawerCallback);
                //openChangeNickNameDialog();
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
        assert myPageFragment != null;
        myPageFragment.onActivityResult(requestCode, resultCode, data);
    }

//    public TransferDataCallback<Integer> getResultCallback() {
//        return categoryCallback;
//    }
//
//    public void setResultCallback(TransferDataCallback<Integer> categoryCallback) {
//        this.categoryCallback = categoryCallback;
//    }

}