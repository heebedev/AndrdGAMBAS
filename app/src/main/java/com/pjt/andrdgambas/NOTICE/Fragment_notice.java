package com.pjt.andrdgambas.NOTICE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class Fragment_notice extends Fragment {

    private String urlAddr, centIP;
    private TextView tv_noticeList, iv_noticeList;

    //노티스 리스트
    private ArrayList<NoticeList> noticeListData;
    private NoticeListAdapter adapter;
    private RecyclerView lv_noticeList;

    public Fragment_notice() {
        // Required empty public constructor
    }

    private void init(View v) {
        //리스트 뷰 연결
        lv_noticeList = v.findViewById(R.id.rv_noticeFragment);

        //네트워크 셋팅
        centIP = HomeData.CENIP;
        urlAddr = "http://" + centIP + ":8080/gambas/NoticeList_query_adrd.jsp?uSeqno=" + STATICDATA.USEQNO;
        Log.e("Fragment_notice", urlAddr);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_notice, container, false);


        init(view);

        connectGetNoticeData();

        return view;
    }

    private void connectGetNoticeData() {
        try {
            NetworkTask_NoticeList networkTask_noticeList = new NetworkTask_NoticeList(getActivity(), urlAddr);
            noticeListData = (ArrayList<NoticeList>) networkTask_noticeList.execute().get();
            setAdapter();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData

    private void setAdapter() {
        if (noticeListData.size() == 0) {
        } else {
            adapter = new NoticeListAdapter(getActivity(), noticeListData);
            lv_noticeList.setAdapter(adapter);
        }
    }

}
