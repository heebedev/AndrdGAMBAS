package com.pjt.andrdgambas.NOTICE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class Fragment_notice extends Fragment {

    private String urlAddr, centIP;
    private TextView tv_noticeList, iv_noticeList;

    //모임리스트뷰
    private ArrayList<NoticeList> noticeListData;
    private NoticeListAdapter adapter;
    private ListView moimList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_notice, container, false);
        Log.v("tab2","tab2");
        return view;
    }
    
}
