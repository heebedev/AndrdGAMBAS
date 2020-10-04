package com.pjt.andrdgambas.PRDDETAIL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class Fragment_prdDetailDetail extends Fragment {

    String logTitle = "Fragment_prdDetailDetail";

    String userSeq = "1";
    String prdSeq = "1";
    String serverIP = "121.136.117.110";

    ArrayList<Bean_PrdDetailDetail_PrdData> prdData = null;
    ArrayList<Bean_PrdDetailDetail_ChData> chData = null;

    ImageView channelProfile, sampleImg;
    TextView nickname, rating, description, sampleTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_prd_detail_detail, container, false);

        setItems(view);
        getSampleData(prdSeq);
        getChannelData(prdData.get(0).getChSeqno(),prdSeq);
        setData();

        return view;
    }

    public void setItems(View view){
        channelProfile = view.findViewById(R.id.iv_prdDetailDetail_channel);
        sampleImg = view.findViewById(R.id.iv_prdDetailDetail_sample);
        nickname = view.findViewById(R.id.tv_prdDetailDetail_channelName);
        rating = view.findViewById(R.id.tv_prdDetailDetail_rating);
        description = view.findViewById(R.id.tv_prdDetailDetail_prdContext);
        sampleTitle = view.findViewById(R.id.tv_prdDetailDetail_sampleInfo);
    }

    private void getSampleData(String pSeq){
        String URL = "http://" + serverIP + ":8080//gambas/prdDetailDetail_prdData.jsp?prdSeqno="+pSeq;
        try{
            NetworkTask__PrdDetailDetail_prdData networkTask = new NetworkTask__PrdDetailDetail_prdData(getActivity(), URL);
            Log.v(logTitle,URL);
            Object obj = networkTask.execute( ).get( );
            prdData = (ArrayList<Bean_PrdDetailDetail_PrdData>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getChannelData(String chSeq, String pSeq){
        String URL = "http://" + serverIP + ":8080//gambas/prdDetailDetail_chData.jsp?chSeqno="+chSeq+"&prdSeqno="+pSeq;
        try{
            NetworkTask__PrdDetailDetail_chData networkTask = new NetworkTask__PrdDetailDetail_chData(getActivity(), URL);
            Log.v(logTitle,URL);
            Object obj = networkTask.execute( ).get( );
            chData = (ArrayList<Bean_PrdDetailDetail_ChData>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setData(){
        nickname.setText(chData.get(0).getChNickname());
        description.setText(prdData.get(0).getPrdContext());
        rating.setText(chData.get(0).getAverage());
        sampleTitle.setText(prdData.get(0).getPrdTitle());
        setFBimage(chData.get(0).getChImage(),"chImage",channelProfile);
        setFBimage(prdData.get(0).getPrdImage(),"prdImage",sampleImg);
    }

    private void setFBimage(String imgName, String imgloc, ImageView imageView){
        String imgURL = "https://firebasestorage.googleapis.com/v0/b/gambas-174df.appspot.com/o/";
        String suffix = "?alt=media";
        Glide.with(this)
                .load(imgURL+imgloc+"%2F"+imgName+suffix)
                .placeholder(R.drawable.gambaslogo)
                .into(imageView);
    }

}
