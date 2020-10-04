package com.pjt.andrdgambas.PRDDETAIL;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.pjt.andrdgambas.Adapter_ViewPager;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class PrdDetailActivity extends AppCompatActivity {

    String logTitle = "PrdDetailActivity";

    String userSeq;
    String prdSeq;
    String CENTIP;

    ArrayList<Bean_PrdDetail> data = null;
    String subsrcribeValidation = "1";

    Adapter_ViewPager adapter;
    Button subscribe;
    ImageView prdImage;
    TabLayout tabLayout;
    Toolbar toolbar;
    TextView close,channelName,prdName,price;
    ViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        userSeq = STATICDATA.USEQNO;
        prdSeq = STATICDATA.PRD_SEQNO;
        CENTIP = STATICDATA.CENTIP;

        setItems();
        setToolbar();
        setClickListener();
        setViewPager();
        setTabLayout();
        getPrdData(prdSeq,userSeq);
        setPrdData();

    }

    public void setItems(){
        subscribe = findViewById(R.id.btn_prdDetail_subscribe);
        prdImage = findViewById(R.id.iv_prdDetail_prd);
        tabLayout = findViewById(R.id.tabLayout_prdDetail);
        toolbar = findViewById(R.id.toolbar_prdDetail);
        close = findViewById(R.id.tv_prdDetail_close);
        channelName = findViewById(R.id.tv_prdDetail_channelName);
        prdName = findViewById(R.id.tv_prdDetail_prdName);
        price = findViewById(R.id.tv_prdDetail_price);
        viewPager = findViewById(R.id.vp_prdDetail);
    }

    public void setToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setViewPager() {
        adapter = new Adapter_ViewPager(getSupportFragmentManager());
        adapter.addFragment(new Fragment_prdDetailDetail(),"상세내역");
        adapter.addFragment(new Fragment_prdDetailList(), "리뷰");
        viewPager.setAdapter(adapter);
    }

    public void setClickListener(){
        subscribe.setOnClickListener(click);
        close.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_prdDetail_subscribe:
                    switch (subsrcribeValidation){
                        case "1":
                            undoSubscribe();
                            break;
                        case "0":
                            doSubscribe();
                            break;
                    }
                    setSubscribeBtn();
                    break;
                case R.id.tv_prdDetail_close:
                    onBackPressed();
                    break;
            }

        }
    };

    private void getPrdData(String pSeq, String uSeq) {
        String URL = "http://" + CENTIP + ":8080/gambas/prdDetail.jsp?prdSeqno="+pSeq+"&uSeqno="+uSeq;
        try{
            NetworkTask__PrdDetail networkTask = new NetworkTask__PrdDetail( PrdDetailActivity.this, URL);
            Log.v(logTitle,URL);
            Object obj = networkTask.execute( ).get( );
            data = (ArrayList<Bean_PrdDetail>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setPrdData(){
        channelName.setText(data.get(0).getChName());
        prdName.setText(data.get(0).getPrdTitle());
        price.setText(data.get(0).getPrdPrice());
        setFBimage(data.get(0).getPrdImage(),"prdImage",prdImage);
        subsrcribeValidation = data.get(0).getSubsValidation();
        setSubscribeBtn();
    }

    private void setFBimage(String imgName, String imgloc, ImageView imageView){
        String imgURL = "https://firebasestorage.googleapis.com/v0/b/gambas-174df.appspot.com/o/";
        String suffix = "?alt=media";
        Glide.with(this)
                .load(imgURL+imgloc+"%2F"+imgName+suffix)
                .circleCrop()
                .placeholder(R.drawable.gambaslogo)
                .into(imageView);
    }

    private void setSubscribeBtn(){
        switch (subsrcribeValidation){
            case "1":
                subscribe.setText("구독취소");
                break;
            case "0":
                subscribe.setText("구독하기");
                break;
        }
    }

    private void doSubscribe(){
        subsrcribeValidation = "1";
        String URL = "http://"+CENTIP+":8080/gambas/doSubscribe.jsp?prdSeqno="+prdSeq+"&uSeqno="+userSeq;
        doNetworkTask(URL);
    }

    private void undoSubscribe(){
        subsrcribeValidation = "0";
        String URL = "http://"+CENTIP+":8080/gambas/undoSubscribe.jsp?prdSeqno="+prdSeq+"&uSeqno="+userSeq;
        doNetworkTask(URL);
    }

    private void doNetworkTask(String URL){
        try {
            Log.v(logTitle,URL);
            NetworkTask_Subscription networkTask = new NetworkTask_Subscription(this, URL);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
