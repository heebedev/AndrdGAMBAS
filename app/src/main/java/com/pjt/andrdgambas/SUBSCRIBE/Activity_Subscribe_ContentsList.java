package com.pjt.andrdgambas.SUBSCRIBE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class Activity_Subscribe_ContentsList extends AppCompatActivity {

    final static String TAG = "Activity_ContentsList";

    ImageView back;
    String urlAddrSelectContentsList;
    ArrayList<Bean_Subscribe> contentslist;
    Adapter_Subscribe_ContentsList adapter_contentslist = null;
    RecyclerView rv_contents = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_activity_subscribe_contents_list);

        back = findViewById(R.id.iv_back_contentslist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); //
            }
        });
    }

    @Override // 뒤로가기
    public void onBackPressed() {
        finish();//
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        //공지사항
        contentslist = new ArrayList<Bean_Subscribe>();
        urlAddrSelectContentsList = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsListQuery.jsp?";
        urlAddrSelectContentsList = urlAddrSelectContentsList + "prdSeqno=" + STATICDATA.PRD_SEQNO ; //  uSeqno 넘겨줌
        Log.v(TAG, " 콘텐츠리스트 url" +urlAddrSelectContentsList);

        try {
            contentslist = new ArrayList<Bean_Subscribe>();
            NetworkTask_Subscribe_ContentsList_Select NetworkTask = new NetworkTask_Subscribe_ContentsList_Select(Activity_Subscribe_ContentsList.this, urlAddrSelectContentsList); //
            contentslist = (ArrayList<Bean_Subscribe>) NetworkTask.execute().get();

            rv_contents = findViewById(R.id.rv_contentsList);// Recyclerview연결
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            rv_contents.setLayoutManager(layoutManager);
            adapter_contentslist = new Adapter_Subscribe_ContentsList(contentslist, Activity_Subscribe_ContentsList.this);
            rv_contents.setAdapter(adapter_contentslist);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }




}//------