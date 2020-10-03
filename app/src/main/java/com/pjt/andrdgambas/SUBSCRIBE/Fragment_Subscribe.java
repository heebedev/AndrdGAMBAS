package com.pjt.andrdgambas.SUBSCRIBE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pjt.andrdgambas.HomeData;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class Fragment_Subscribe extends Fragment {

    // tab3 전역 변수
    private View view;

    //구독리스트
    private String urlAddrSelectSubslist;
    private ArrayList<Bean_Subscribe> subslist;  //BEAN
    private Adapter_Subscribe_SubsList subslist_adapter; // 어댑터

    //리사이클러뷰
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // STATIC
    static String centIP = STATICDATA.CENTIP; // IP





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3, container, false); // tab3 연결
        Log.v("tab3","tab3");

        // 구독리스트 불러오기
        ConnectGetSubslist();

        // 리사이클러뷰 연결
        ConnectRecyclerView();

        return view;
    }

    //Method
    //구독게시물 리스트  가져오기
    public void ConnectGetSubslist(){
        urlAddrSelectSubslist = "http://" + centIP + ":8080/gambas/subsListQuery.jsp?";
        urlAddrSelectSubslist = urlAddrSelectSubslist + "uSeqno=" + HomeData.USERID; //  uSeqno 넘겨줌

        try{
            Log.v("url subslist; ",urlAddrSelectSubslist);
            NetworkTask_Subscribe_Subslist_Select subslistNetworkTask
                    = new NetworkTask_Subscribe_Subslist_Select( getActivity(), urlAddrSelectSubslist );
            Object obj = subslistNetworkTask.execute( ).get( );
            subslist = (ArrayList<Bean_Subscribe>) obj;


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //리사이클러뷰 연결
    public void  ConnectRecyclerView(){
        recyclerView = view.findViewById(R.id.rv_subsList); // rv 연결
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        subslist_adapter = new Adapter_Subscribe_SubsList(subslist); // 어댑터 가져와서 어레이리스트에 담아줌
        recyclerView.setAdapter(subslist_adapter);
    }


    @Override
    public void onStart() {

        //댓글목록 불러와서
        ConnectGetSubslist(); //BEAN에 넣고 보여줌

        //리사이클러뷰
        ConnectRecyclerView();
        super.onStart();
    }





}//-------

