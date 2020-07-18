package com.myapp.instantfoodsreviewapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.adapter.CustomRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.fragment.DdokbokkiFragment;
import com.myapp.instantfoodsreviewapp.fragment.DumplingFragment;
import com.myapp.instantfoodsreviewapp.fragment.FriedRiceFragment;
import com.myapp.instantfoodsreviewapp.fragment.HomeFragment;
import com.myapp.instantfoodsreviewapp.fragment.MyPageFragment;
import com.myapp.instantfoodsreviewapp.fragment.NoodleFragment;
import com.myapp.instantfoodsreviewapp.fragment.PizzaFragment;
import com.myapp.instantfoodsreviewapp.fragment.StewFragment;
import com.myapp.instantfoodsreviewapp.fragment.WriteReviewFragment;
import com.myapp.instantfoodsreviewapp.model.AccountData;
import com.myapp.instantfoodsreviewapp.model.User;
import com.myapp.instantfoodsreviewapp.model.entity.AccountDto;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.BasicAuthInterceptor;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = EmailLoginActivity.class.getSimpleName();

    private TextView getNickName;
    private Button logoutButton;
    private ActivityMainBinding activityMainBinding;
    private UserPreference userPreference;
    private String getToken;
    private RetrofitClient retrofitClient;
    private BasicAuthInterceptor basicAuthInterceptor;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private LinearLayout llDrawerHeader;
    // private ViewPagerAdapter viewPagerAdapter;
    //  private ViewPager viewPager;
    private CustomRecyclerAdapter adapterCustom;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initPreference();
        init();
        setNavigationView();
        setToolbar();
        bindView();
        // displayView(0);
        //setViewPager();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        //  adapterCustom = new CustomRecyclerAdapter(adapterCustom.);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        getUser();
    }


//    private void setViewPager(){
//        viewPager = activityMainBinding.viewpager;
//        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
//    }

    private void init() {
        drawerLayout = activityMainBinding.drawerLayout;
        navigationView = findViewById(R.id.nav_view);
    }

    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
    }

    private void setToolbar() {
        toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);
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

    public void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }

    private void bindView() {
        View header = navigationView.getHeaderView(0);
        llDrawerHeader = header.findViewById(R.id.llDrawerHeader);
        getNickName = header.findViewById(R.id.getNickName);
    }

    public void userLogOut() {
        userPreference.setLoggedIn(getApplicationContext(), false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }




    private void getUser() {

        //getToken = userPreference.getString(Config.KEY_TOKEN);
        getToken = userPreference.getInstance().getString(Config.KEY_TOKEN);
        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<AccountDto> call = retrofitInterface.account(getToken);

        UserPreference.getInstance().putString(Config.KEY_TOKEN, getToken);
        Log.e("token", "" + getToken);//null나옴-> 잘나옴


        /*
        Call<ApiResultDto> apiResultDtoCall = retrofitInterface.pimage(getToken, originFile, thumbnailFile);
        apiResultDtoCall.enqueue(new Callback<ApiResultDto>() {
            @Override
            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {

                if (response.isSuccessful()) {
                    ApiResultDto apiResultDto = response.body();
                    JsonObject resultData = apiResultDto.getResultData();
                    PImageData pImageData = new Gson().fromJson(resultData, PImageData.class);

                    String originalImage = IMG_BASE_URL+pImageData.getStoredPath();
                    UserPreference.getInstance().putString("PROFILE_IMAGE_PATH",pImageData.getStoredPath());
         */


        call.enqueue(new Callback<AccountDto>() {
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                if (response.isSuccessful()) {
                    AccountDto accountDto = response.body();
                    AccountDto.ResultData resultData = accountDto.getResultData();

                   // User user = new Gson().fromJson(String.valueOf(accountDto),User.class);

                    if (resultData  != null) {

                       // String userEmail =  apiResultDto.getResultData().get("email").getAsString();
                       // String userNickName = apiResultDto.getResultData().get("nickname").getAsString();

                        String userEmail =  accountDto.getResultData().getUser().getEmail();
                        String userNickName = accountDto.getResultData().getUser().getNickname();

                        UserPreference.getInstance().putString(Config.KEY_EMAIL,userEmail);
                        UserPreference.getInstance().putString(Config.KEY_NICKNAME,userNickName);

                        Log.e("userEmail", "" + userEmail);
                        Log.e("userNickName", "" + userNickName);

                        Log.e("userEmail2", "" + UserPreference.getInstance().getString(Config.KEY_EMAIL));
                        Log.e("userNickName2", "" + UserPreference.getInstance().getString(Config.KEY_NICKNAME));

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_stew:
                fragment = new StewFragment();
                break;
            case R.id.nav_noodle:
                fragment = new NoodleFragment();
                break;

            case R.id.nav_ddokbokki:
                fragment = new DdokbokkiFragment();
                break;
            case R.id.nav_dumpling:
                fragment = new DumplingFragment();
                break;
            case R.id.nav_friedRice:
                fragment = new FriedRiceFragment();
                break;
            case R.id.nav_pizza:
                fragment = new PizzaFragment();
                break;
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_my_page:
                fragment = new MyPageFragment();
                break;
            case R.id.nav_write_review:
                fragment = new WriteReviewFragment();
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


}