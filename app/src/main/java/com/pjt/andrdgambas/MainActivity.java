package com.pjt.andrdgambas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.pjt.andrdgambas.SUBSCRIBE.Fragment_Subscribe;

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
    Adapter_MainViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        adapter = new Adapter_MainViewPager(getSupportFragmentManager());
        adapter.addFragment(new Fragment_first(), "탭1");
        adapter.addFragment(new Fragment_second(), "탭2");
        adapter.addFragment(new Fragment_Subscribe(), "Subscribe");
        adapter.addFragment(new Fragment_fourth(), "탭4");
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
                        Log.v(logTitle,"goMyChannel()");
                        break;
                    case R.id.nav_item_logout:
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

}