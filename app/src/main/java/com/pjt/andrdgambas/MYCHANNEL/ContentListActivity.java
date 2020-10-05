package com.pjt.andrdgambas.MYCHANNEL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.MYINFO.ChangePasswordActivity;
import com.pjt.andrdgambas.MYINFO.LikeReviewAdapter;
import com.pjt.andrdgambas.MYINFO.LikeReviewTask;
import com.pjt.andrdgambas.MYINFO.MyInfo2NetworkTask;
import com.pjt.andrdgambas.MYINFO.MyLikeReview;
import com.pjt.andrdgambas.MYINFO.UpdateMyInfoActivity;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class ContentListActivity extends AppCompatActivity {
    TextView tv_prd_state, tv_prd_title, tv_prd_price, tv_prd_day, tv_prd_context;
    Button btn_add_content;
    ImageView imgView;
    String urlAddr, urlAddr2, imageName;
    String centIP = STATICDATA.CENTIP;
    Intent intent;
    ListView lv_content_list;
    ArrayList<Content> content;
    ContentAdapter contentAdapter;
    String prdSeqno = "1"; // *** 테스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        tv_prd_context = findViewById(R.id.tv_prd_context);
        tv_prd_day = findViewById(R.id.tv_prd_day);
        tv_prd_price = findViewById(R.id.tv_prd_price);
        tv_prd_state = findViewById(R.id.tv_prd_state);
        tv_prd_title = findViewById(R.id.tv_prd_title);
        imgView = findViewById(R.id.img_prd);
        btn_add_content = findViewById(R.id.btn_add_content);
        btn_add_content.setOnClickListener(onClickListener);
        lv_content_list = findViewById(R.id.lv_content_list);

        // *** 숙전이언니 prd랑 연결 해야 됨
//        intent = getIntent();
//        prdSeqno = intent.getStringExtra("prdSeqno");

        urlAddr = "http://" + centIP + ":8080/gambas/getPrdInfo_android.jsp";
        connectGetData();

        urlAddr2 = "http://" + centIP + ":8080/gambas/ContentList_android.jsp";
        connectContentGetData();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_add_content:
                    intent = new Intent(ContentListActivity.this, ContentUploadActivity.class);
                    intent.putExtra("prdSeqno", prdSeqno);
                    startActivity(intent);
                    break;
            }
        }
    };


    // prdboard 정보를 불러오는 Connect
    private void connectGetData() {
        urlAddr += "?prdSeqno=" + prdSeqno;
        Log.v("URL", urlAddr);
        try {
            PrdInfoNetworkTask networkTask = new PrdInfoNetworkTask(ContentListActivity.this, urlAddr);
            ArrayList obj = (ArrayList) networkTask.execute().get();

            String[] prdinfo = (String[]) obj.toArray(new String[obj.size()]);

            tv_prd_title.setText(prdinfo[0]);
            tv_prd_price.setText(prdinfo[1]);
            tv_prd_day.setText(prdinfo[2]);
            tv_prd_context.setText(prdinfo[3]);
            imageName = prdinfo[4];

            if (prdinfo[5].equals("0")) {
                tv_prd_state.setText("운영종료");
            } else {
                tv_prd_state.setText("운영중");
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference dateRef = storageRef.child("prdImage/" + imageName);
            dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.v("Firebase URL", uri.toString());
                    Glide.with(ContentListActivity.this)
                            .load(uri.toString())
                            .apply(new RequestOptions().centerCrop())
                            .into(imgView);
                }
            });

        } catch (Exception e) {

        }
    }

    private void connectContentGetData() {
        urlAddr2 += "?prdSeqno=" + prdSeqno;
        Log.v("URL", urlAddr2);
        try {
            ContentTask networkTask = new ContentTask(ContentListActivity.this, urlAddr2);
            Object obj = networkTask.execute().get();
            content = (ArrayList<Content>) obj;
            contentAdapter = new ContentAdapter(ContentListActivity.this, R.layout.content_list, content);
            lv_content_list.setAdapter(contentAdapter);

        } catch (Exception e) {

        }
    }
}