package com.pjt.andrdgambas.HOME;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.pjt.andrdgambas.LOGIN.LoginActivity;
import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class Fragment_home extends Fragment {

    WebView webView;
    ProgressDialog pDialog;
    private static final String TAG = "Load Firebase";
    String filename = "202010115334pink.pdf";

    ArrayList<HomeData> list;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView_rec;
    private LinearLayoutManager mLayoutManager;
    TextView textView1,textView2,textView3,textView4,textView5,tv_rec;
    Context mContext;
    String urlAddr;
    String centIP = HomeData.CENIP;
    String updateUrlAddr;
    Button btn_logout;
    Intent intent;
    HomeAdapter adapter;
    String [] categoryList;
    RecyclerView [] recyclerViews;
    TextView [] textViews;

    private int MAX_ITEM_COUNT = 10;

    public Fragment_home() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext =context;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.home, container, false);
        recyclerView1 = view.findViewById(R.id.recycler_list_writing);
        recyclerView2 = view.findViewById(R.id.recycler_list_drawing);
        recyclerView3 = view.findViewById(R.id.recycler_list_video);
        recyclerView4 = view.findViewById(R.id.recycler_list_music);
        recyclerView5 = view.findViewById(R.id.recycler_list_etc);
        recyclerView_rec = view.findViewById(R.id.recycler_list_rec);
        textView1 = view.findViewById(R.id.tv_1);
        textView2 = view.findViewById(R.id.tv_2);
        textView3 = view.findViewById(R.id.tv_3);
        textView4 = view.findViewById(R.id.tv_4);
        textView5 = view.findViewById(R.id.tv_5);
        tv_rec = view.findViewById(R.id.tv_rec);
        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);
        recyclerView5.setHasFixedSize(true);
        recyclerView_rec.setHasFixedSize(true);

        // 카테고리 목록 다 안보이게 해놓기
        recyclerView1.setVisibility(recyclerView1.GONE);
        textView1.setVisibility(textView1.GONE);
        recyclerView2.setVisibility(recyclerView2.GONE);
        textView2.setVisibility(textView2.GONE);
        recyclerView3.setVisibility(recyclerView3.GONE);
        textView3.setVisibility(textView3.GONE);
        recyclerView4.setVisibility(recyclerView4.GONE);
        textView4.setVisibility(textView4.GONE);
        recyclerView5.setVisibility(recyclerView5.GONE);
        textView5.setVisibility(textView5.GONE);

        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView1.setLayoutManager(mLayoutManager) ;
        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView2.setLayoutManager(mLayoutManager) ;
        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView3.setLayoutManager(mLayoutManager) ;
        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView4.setLayoutManager(mLayoutManager) ;
        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView5.setLayoutManager(mLayoutManager) ;
        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView_rec.setLayoutManager(mLayoutManager) ;

        urlAddr = "http://" + centIP + ":8080/gambas/getUserCategoryList_android.jsp?seq=" + HomeData.USERID;
        Log.e("Status", urlAddr);
        connectionCGGetData(urlAddr);
        urlAddr = "http://" + centIP + ":8080/gambas/getRecommendData_android.jsp?category=";
        Log.e("Status", urlAddr);
        connectionCTGetData(urlAddr);
        // init Adapter
        adapter = new HomeAdapter(getActivity(), list) ;
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener1(View v, int position) {
                intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("prdSeq",list.get(position).getPrdSeq());
                startActivity(intent);
            }
        });
        recyclerView_rec.setAdapter(adapter);

        recyclerViews = new RecyclerView[]{recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5};
        textViews = new TextView[]{textView1,textView2,textView3,textView4,textView5};

        for(int i = 0; i < categoryList.length; i++) {
            int idx = 0;
            switch (categoryList[i]){
                case "글":
                    idx = 1;
                    break;
                case "그림":
                    idx = 2;
                    break;
                case "영상":
                    idx = 3;
                    break;
                case "음악":
                    idx = 4;
                    break;
                case "기타":
                    idx = 5;
                    break;
                default:
                    break;
            }
            recyclerViews[idx-1].setVisibility(recyclerViews[idx-1].VISIBLE);
            textViews[idx-1].setVisibility(textViews[idx-1].VISIBLE);
            urlAddr = "http://" + centIP + ":8080/gambas/getCategoryData_android.jsp?category=" + categoryList[i];
            connectionCTGetData(urlAddr);
            // init Adapter
            adapter = new HomeAdapter(getActivity(), list) ;
            adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener1(View v, int position) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("prdSeq",list.get(position).getPrdSeq());
                    startActivity(intent);
                }
            });
            // set Adapter
            recyclerViews[idx-1].setAdapter(adapter);
        }

        adapter = new HomeAdapter(getActivity(), list);


        btn_logout = view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        return view;
    }

    private void connectionCGGetData(String urlAddr) {
        try {
            CategoryNetworkTask networkTask = new CategoryNetworkTask(mContext,urlAddr);
            categoryList = (String[]) networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connectionCTGetData(String urlAddr) {
        try {
            ContentsNetworkTask networkTask = new ContentsNetworkTask(mContext,urlAddr);
            list = (ArrayList<HomeData>) networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
