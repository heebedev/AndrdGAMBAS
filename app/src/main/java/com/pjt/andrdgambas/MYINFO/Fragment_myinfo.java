package com.pjt.andrdgambas.MYINFO;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;
import java.util.ArrayList;

public class Fragment_myinfo extends Fragment {


    Intent intent;
    Button btn_update_myinfo, btn_register_creator;
    String urlAddr, imageName, likeUrl, reviewUrl, creatorUrl;
    String centIP = STATICDATA.CENTIP;
    TextView tv_name;
    ImageView imageView;
    ArrayList<MyLikeReview> likes, reviews;
    ListView lv_like, lv_review;
    LikeReviewAdapter likeReviewAdapter;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4, container, false);

        tv_name = view.findViewById(R.id.tv_get_user_name);
        btn_update_myinfo = view.findViewById(R.id.btn_update_myinfo);
        btn_register_creator = view.findViewById(R.id.btn_register_creator);
        btn_update_myinfo.setOnClickListener(onClickListener);
        imageView = view.findViewById(R.id.img_user);
        btn_register_creator.setOnClickListener(onClickListener);
        lv_like = view.findViewById(R.id.lv_like);
        lv_review = view.findViewById(R.id.lv_review);
        imageView = view.findViewById(R.id.img_user);

        // UCreaterSubs가 0일 때만 등록하기 버튼 보여주기
        if (STATICDATA.UCreaterSubs.equals("0")) {
            btn_register_creator.setVisibility(View.VISIBLE);
        } else if(STATICDATA.UCreaterSubs.equals("1")){
            btn_register_creator.setVisibility(View.INVISIBLE);

        }

        // 사용자 정보 불러오는 URL
        urlAddr = "http://" + centIP + ":8080/gambas/getUserInfo2_android.jsp";
        connectGetData();

        // 내 좋아요 불러오는 URL
        likeUrl = "http://" + centIP + ":8080/gambas/MyLikeList_android.jsp";
        connectLikeGetData();

        // 내 리뷰 불러오는 URL
        reviewUrl = "http://" + centIP + ":8080/gambas/MyReviewList_android.jsp";
        connectReviewGetData();

        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_update_myinfo:
                    intent = new Intent(getActivity(), UpdateMyInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_register_creator:
                    dialogCreator();
                    break;
            }
        }
    };

    private void dialogCreator() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("확인");
        builder.setMessage("크리에이터로 등록하시겠습니까?");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserWithdrawConnect();
                        STATICDATA.UCreaterSubs = "1";
                        Toast.makeText(getActivity(), "크리에이터로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        btn_register_creator.setVisibility(View.INVISIBLE);

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // 사용자 정보 불러오는 Connect
    private void connectGetData() {
        urlAddr += "?seq=" + HomeData.USERID;
        Log.v("URL", urlAddr);
        try {
            MyInfo2NetworkTask networkTask = new MyInfo2NetworkTask(getActivity(), urlAddr);
            ArrayList obj = (ArrayList) networkTask.execute().get();

            String[] myinfo = (String[]) obj.toArray(new String[obj.size()]);
            tv_name.setText(myinfo[0]);
            imageName = myinfo[1];

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference dateRef = storageRef.child("uImage/"+imageName);
            dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.v("Firebase URL", uri.toString());
                            Glide.with(getActivity())
                                    .load(uri.toString())
                                    .circleCrop()
                                    .apply(new RequestOptions().centerCrop())
                                    .into(imageView);
                        }
                    });

        } catch (Exception e) {

        }
    }

    // 내 좋아요 불러오는 Connect
    private void connectLikeGetData() {
        likeUrl += "?uSeqno=" + HomeData.USERID;
        Log.v("URL", likeUrl);
        try {
            LikeReviewTask networkTask = new LikeReviewTask(getActivity(), likeUrl);
            Object obj = networkTask.execute().get();
            likes = (ArrayList<MyLikeReview>) obj;
            likeReviewAdapter = new LikeReviewAdapter(getActivity(), R.layout.myinfo_list, likes);
            lv_like.setAdapter(likeReviewAdapter);

        } catch (Exception e) {

        }
    }

    // 내 리뷰 불러오는 Connect
    private void connectReviewGetData() {
        reviewUrl += "?uSeqno=" + HomeData.USERID;
        Log.v("URL", reviewUrl);
        try {
            LikeReviewTask networkTask = new LikeReviewTask(getActivity(), reviewUrl);
            Object obj = networkTask.execute().get();
            reviews = (ArrayList<MyLikeReview>) obj;
            likeReviewAdapter = new LikeReviewAdapter(getActivity(), R.layout.myinfo_list, reviews);
            lv_review.setAdapter(likeReviewAdapter);

        } catch (Exception e) {

        }
    }

    // 크리에이터 uCreaterSubs Update
    private void UserWithdrawConnect() {
        creatorUrl = "http://" + centIP + ":8080/gambas/CreatorRegist_ios.jsp";
        creatorUrl += "?uSeqno=" + HomeData.USERID + "&uCreaterSubs=1";
        Log.v("URL", creatorUrl);
        try {
            UserUpdateTask networkTask = new UserUpdateTask(getActivity(), creatorUrl);
            networkTask.execute().get();


        } catch (Exception e) {

        }
    }


}
