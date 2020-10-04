package com.pjt.andrdgambas.MYINFO;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class Fragment_fourth extends Fragment {
    Intent intent;
    Button btn_update_myinfo;
    String urlAddr, imageName;
    String centIP = HomeData.CENIP;
    TextView tv_name;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4, container, false);

        tv_name = view.findViewById(R.id.tv_get_user_name);
        btn_update_myinfo = view.findViewById(R.id.btn_update_myinfo);
        btn_update_myinfo.setOnClickListener(onClickListener);

        urlAddr = "http://" + centIP + ":8080/gambas/getUserInfo2_android.jsp";
        connectGetData();

        return view;

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(getActivity(), UpdateMyInfoActivity.class);
            startActivity(intent);
        }
    };

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

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference dateRef = storageRef.child("gambasFile/"+imageName);
            dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.v("Firebase URL", uri.toString());
                            Glide.with(getActivity()).load(uri.toString()).into(imageView);
                        }
                    }).toString();




        } catch (Exception e) {

        }
    }

    
}
