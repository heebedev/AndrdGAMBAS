package com.pjt.andrdgambas.HOME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.pjt.andrdgambas.LOGIN.LoginActivity;
import com.pjt.andrdgambas.MYCHANNEL.Activity_Mychannel;
import com.pjt.andrdgambas.MYCHANNEL.MyChannel;
import com.pjt.andrdgambas.MYINFO.Fragment_myinfo;
import com.pjt.andrdgambas.SUBSCRIBE.Fragment_Subscribe;
import com.pjt.andrdgambas.Adapter_ViewPager;
import com.pjt.andrdgambas.NOTICE.Fragment_notice;
import com.pjt.andrdgambas.R;

public class MainActivity extends AppCompatActivity {

    String logTitle = "MainActivity";

    Toolbar toolbar;
    ImageView nav_button, profilePic;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView nickName, email;
    DrawerLayout nav_draw;
    NavigationView nav_view;
    View header;
    Adapter_ViewPager adapter;
    Intent intent;

    private BackPressedForFinish backPressedForFinish;

    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressedForFinish = new BackPressedForFinish(this);

        setItems();
        setToolbar();
        setClickListener();
        setNaviagionView();
        setNavItemListener();
        setNavItemListener();
        setViewPager();
        setTabLayout();
    }

    public void setItems() {
        //Toolbar
        toolbar = findViewById(R.id.toolbar_main);
        nav_button = findViewById(R.id.img_toolbar_menu);
        //Navigation_Drawer
        nav_draw = findViewById(R.id.drawer_main);
        nav_view = findViewById(R.id.nv_main);
        //Navigation_Header
        header = nav_view.getHeaderView(0);
        profilePic = findViewById(R.id.iv_navHeader_profilePic);
        nickName = findViewById(R.id.tv_navHeader_nickname);
        email = findViewById(R.id.tv_navHeader_email);
        //ViewPager
        viewPager = findViewById(R.id.vp_Main);
        //TabLayout
        tabLayout = findViewById(R.id.tabLayout_Main);
    }

    public void setToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void setNaviagionView(){
        nav_view.inflateMenu(R.menu.nav_item_main);
    }

    public void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setViewPager() {
        adapter = new Adapter_ViewPager(getSupportFragmentManager());
        adapter.addFragment(new Fragment_home(), "HOME");
        adapter.addFragment(new Fragment_notice(), "NOTICE");
        adapter.addFragment(new Fragment_Subscribe(), "SUBSCRIBE");
        adapter.addFragment(new Fragment_myinfo(), "MYINFO");
        viewPager.setAdapter(adapter);
    }

    public void setClickListener(){
        nav_button.setOnClickListener(click);
    }

    public void setNavItemListener(){
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_item_myChannel:
                        intent = new Intent(MainActivity.this, Activity_Mychannel.class);
                        startActivity(intent);
                        Log.v(logTitle,"goMyChannel()");
                        break;
                    case R.id.nav_item_logout:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent i = new Intent(MainActivity.this/*현재 액티비티 위치*/ , LoginActivity.class/*이동 액티비티 위치*/);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                                .show();

                        Log.v(logTitle,"doLogOut()");
                        break;
                }
                return false;
            }
        });
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.img_toolbar_menu:
                    nav_draw.openDrawer(GravityCompat.START);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        backPressedForFinish.onBackPressed();
    }
}