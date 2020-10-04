package com.pjt.andrdgambas.SUBSCRIBE;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class Adapter_Subscribe_SubsList extends RecyclerView.Adapter<Adapter_Subscribe_SubsList.CustomViewHolder>  {

    //Field
    private ArrayList<Bean_Subscribe> subslist;

    //Constructor
    public Adapter_Subscribe_SubsList(ArrayList<Bean_Subscribe> arrayList) {
        this.subslist = arrayList;
    }



    @NonNull
    @Override  //리스트뷰 처음 생성될 떄 생명주기 뜻함
    public Adapter_Subscribe_SubsList.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab3_subscribe_recycle_subslist, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override  //실제 리스트뷰 추가될 때
    public void onBindViewHolder(@NonNull final Adapter_Subscribe_SubsList.CustomViewHolder holder, final int position) {

        //Glide gradle 추가 : 이미지뷰 url(String)로 가져오기
        Glide.with(holder.itemView.getContext())
                .load("http://"+STATICDATA.CENTIP+":8080/ftp/" + subslist.get(position).getPrdImage()) // 이미지 url 바꿔야함
                .placeholder(R.drawable.ic_launcher_foreground) // 이미지 없을때 대체  // 교체 해야함
                .error(R.drawable.ic_launcher_foreground) // 이미지 없을때 대체
                .into(holder.iv_prdImage);

        holder.tv_prdTitle.setText(subslist.get(position).getPrdTitle());
        holder.tv_chNickname.setText(subslist.get(position).getChNickname());
        holder.tv_term.setText(subslist.get(position).getTerm());
        holder.tv_releaseDay.setText(subslist.get(position).getReleaseDay());
        holder.tv_cgName.setText(subslist.get(position).getCgName());


        //클릭
        holder.layout_subsPrd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                STATICDATA.PRD_SEQNO = subslist.get(position).getPrdSeqno();
                Log.v("PrdSeqno", String.valueOf(STATICDATA.PRD_SEQNO));

                Intent intent = new Intent(view.getContext(), Activity_Subscribe_ContentsList.class); // ContentsList로이
                view.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != subslist ? subslist.size() : 0);

    }


    //사용할것 선언
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_subsPrd;
        ImageView iv_prdImage;
        TextView tv_prdTitle;
        TextView tv_chNickname;
        TextView tv_term;
        TextView tv_releaseDay;
        TextView tv_cgName;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout_subsPrd = itemView.findViewById(R.id.Ll_subsPrd);
            this.iv_prdImage = itemView.findViewById(R.id.iv_image);
            this.tv_prdTitle = itemView.findViewById(R.id.tv_prdTitle);
            this.tv_chNickname = itemView.findViewById(R.id.tv_chNickname);
            this.tv_term = itemView.findViewById(R.id.tv_term);
            this.tv_releaseDay = itemView.findViewById(R.id.tv_releaseDay);
            this.tv_cgName = itemView.findViewById(R.id.tv_cgName);


        }
    }




}//---------
