package com.myapp.instantfoodsreviewapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.fragment.DdokbokkiFragment;
import com.myapp.instantfoodsreviewapp.fragment.DumplingFragment;
import com.myapp.instantfoodsreviewapp.fragment.FriedRiceFragment;
import com.myapp.instantfoodsreviewapp.fragment.HomeFragment;
import com.myapp.instantfoodsreviewapp.fragment.MyPageFragment;
import com.myapp.instantfoodsreviewapp.fragment.NoodleFragment;
import com.myapp.instantfoodsreviewapp.fragment.PizzaFragment;
import com.myapp.instantfoodsreviewapp.fragment.StewFragment;
import com.myapp.instantfoodsreviewapp.fragment.ViewPagerAdapter;
import com.myapp.instantfoodsreviewapp.fragment.WriteReviewFragment;
import com.myapp.instantfoodsreviewapp.model.UserAccountData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initPreference();
        init();
        setNavigationView();
        setToolbar();
        bindView();

        //setViewPager();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


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

    private void init(){
        drawerLayout = activityMainBinding.drawerLayout;
        // drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void setNavigationView(){
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
    }

    private void setToolbar(){
        toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void initPreference() {
        userPreference = new UserPreference();
        userPreference.setContext(this);
    }

    private void bindView(){
        View header = navigationView.getHeaderView(0);
        llDrawerHeader = header.findViewById(R.id.llDrawerHeader);
       // drawerLayout = header.findViewById(R.id.drawer_layout);
        getNickName = header.findViewById(R.id.getNickName);
    }

    public void userLogOut() {
        userPreference.setLoggedIn(getApplicationContext(), false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    private void getUser() {

        getToken = userPreference.getString(Config.KEY_TOKEN);

        RetrofitInterface retrofitInterface = RetrofitClient.buildHTTPClient();
        Call<ApiResultDto> call = retrofitInterface.account(getToken);

        call.enqueue(new Callback<ApiResultDto>() {

            public void onResponse(Call<ApiResultDto> call, Response<ApiResultDto> response) {
                if (response.isSuccessful()) {
                    ApiResultDto dto = response.body();
                    JsonObject resultData = dto.getResultData(); //실제 데이터부를 json으로 받고
                    if (resultData != null) {
                        //받은 json을 내가만든 UserAccountData 클래스형태로 convert
                        UserAccountData userAccountData = new Gson().fromJson(resultData, UserAccountData.class);
                        //String nickName =response.body().getNickname();
                        // Log.i(TAG, "Responser: " + response.body());
                       // String test = userAccountData.getNickname();
                        getNickName.setText(userAccountData.getNickname());


                        //UserPreference userPrefs = new UserPreference();
                        //userPrefs.setContext(userAccountData.getNickname());
                    } else {
                        Log.e("getUser", "Account null ");

                    }


                    UserPreference userPreference = new UserPreference();
                    if (getToken != null) {
                        userPreference.putString(Config.KEY_TOKEN, getToken);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_stew:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StewFragment()).commit();
                break;
            case R.id.nav_noodle:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NoodleFragment()).commit();
                break;

            case R.id.nav_ddokbokki:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DdokbokkiFragment()).commit();
                break;
            case R.id.nav_dumpling:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DumplingFragment()).commit();
                break;
            case R.id.nav_friedRice:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FriedRiceFragment()).commit();
                break;
            case R.id.nav_pizza:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PizzaFragment()).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_my_page:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyPageFragment()).commit();
                break;
            case R.id.nav_write_review:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WriteReviewFragment()).commit();
                break;
            case R.id.nav_logout:
                userLogOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}