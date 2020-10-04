package com.pjt.andrdgambas.PRDDETAIL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class Fragment_prdDetailList extends Fragment {

    String logTitle = "Fragment_prdDetailList";

    String userSeq = "1";
    String prdSeq = "1";
    String serverIP = "121.136.117.110";

    ArrayList<Bean_PrdDetailList> reviewData = null;
    Adapter_PrdDetailList_Review adapter;

    TextView newReview;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_prd_detail_list, container, false);

        setItems(view);
        getData(prdSeq);
        setRecyclerView();

        newReview.setOnClickListener(click);

        return view;
    }

    public void setItems(View view){
        newReview = view.findViewById(R.id.tv_prdDetailList_newReview);
        recyclerView = view.findViewById(R.id.rv_prdDetailList_reviews);
        linearLayoutManager = new LinearLayoutManager(getActivity());
    }

    private void getData(String pSeq){
        String URL = "http://" + serverIP + ":8080//gambas/prdDetailList.jsp?prdSeqno="+pSeq;
        try{
            NetworkTask__PrdDetailList networkTask = new NetworkTask__PrdDetailList(getActivity(), URL);
            Log.v(logTitle,URL);
            Object obj = networkTask.execute( ).get( );
            reviewData = (ArrayList<Bean_PrdDetailList>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Adapter_PrdDetailList_Review(reviewData);
        recyclerView.setAdapter(adapter);
    }

    private void goActivityNewReview(){
        Intent intent = new Intent(getActivity(),NewReviewActivity.class);
        startActivity(intent);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_prdDetailList_newReview:
                    goActivityNewReview();
                    break;
            }
        }
    };

}
