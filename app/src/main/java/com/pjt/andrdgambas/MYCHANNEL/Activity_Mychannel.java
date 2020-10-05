package com.pjt.andrdgambas.MYCHANNEL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.LOGIN.LoginActivity;
import com.pjt.andrdgambas.R;
import java.util.ArrayList;

public class Activity_Mychannel extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    ImageView imageView;
    TextView nickname, date, context, validation;
    Button add;
    Context mContext;
    String urlAddr;
    String codeType;
    String centIp = HomeData.CENIP;
    Intent intent;
    MyChannelAdapter adapter;
    ArrayList<MyChannel> list;
    String chSeqno, prdSeqno, image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychannel);

        recyclerView = findViewById(R.id.lv_mych_prdList);
        nickname = findViewById(R.id.tv_mych_nickname);
        date = findViewById(R.id.tv_mych_date);
        context = findViewById(R.id.tv_mych_context);
        validation = findViewById(R.id.tv_mych_vali);
        add = findViewById(R.id.btn_mych_add);
        imageView = findViewById(R.id.iv_mych_img);

        urlAddr = "http://" + centIp + ":8080/gambas/MyChannelSelect_android.jsp?userSeqno=2";
        codeType = "getChannel";
        Log.v("URL",urlAddr);
        connectionGetData();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference dateRef = storageRef.child("chImage/" + image); // fileName은 테스트용!!! image로 넣어야함.
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.v("Firebase URL", uri.toString());
                Glide.with(context)
                        .load(uri.toString())
                        .apply(new RequestOptions().centerCrop())
                        .into(imageView);
            }
        });
        urlAddr = "http://" + centIp + ":8080/gambas/MyProductSelect_android.jsp?uSeqno=2";
        codeType = "getProduct";
        Log.v("URL",urlAddr);
        connectionGetData();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext)) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new MyChannelAdapter(mContext,list) ;
        adapter.setOnItemClickListener(new MyChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                intent = new Intent(Activity_Mychannel.this, LoginActivity.class);
                intent.putExtra("prdSeqno",prdSeqno);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter) ;

        add.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_mych_add:
                    intent = new Intent(Activity_Mychannel.this, addProductActivity.class);
                    intent.putExtra("chSeqno",chSeqno);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        urlAddr = "http://" + centIp + ":8080/gambas/MyProductSelect_android.jsp?uSeqno=2";
        codeType = "getProduct";
        Log.v("URL",urlAddr);
        connectionGetData();
    }

    private void connectionGetData() {
        try{
            list = new ArrayList<MyChannel>();
            MyChannelNetworkTask networkTask = new MyChannelNetworkTask(mContext,urlAddr,codeType);
            if(codeType.equals("getChannel")) {
                list = (ArrayList<MyChannel>) networkTask.execute().get();
                nickname.setText(list.get(0).getChannelNickname());
                date.setText("개설일 " + list.get(0).getChannelRegisterDate());
                context.setText(list.get(0).getChannelContent());
                String vali = list.get(0).getChannelValidation();
                chSeqno = list.get(0).getChannelSeqno();
                image = list.get(0).getChannelImage();
                if (vali.equals("1")) {
                    validation.setText("운영중");
                } else {
                    validation.setText("문닫음");
                }
            }else if(codeType.equals("getProduct")){
                list = (ArrayList<MyChannel>) networkTask.execute().get();
                prdSeqno = list.get(0).getProductSeqno();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}