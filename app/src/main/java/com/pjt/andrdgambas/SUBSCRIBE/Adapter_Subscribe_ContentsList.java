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

public class Adapter_Subscribe_ContentsList extends RecyclerView.Adapter<Adapter_Subscribe_ContentsList.CustomViewHolder>  {

    //Field
    private ArrayList<Bean_Subscribe> contentsList;

    //Constructor
    public Adapter_Subscribe_ContentsList(ArrayList<Bean_Subscribe> arrayList, Activity_Subscribe_ContentsList activity_subscribe_contentsList) {
        this.contentsList = arrayList;
    }


    @NonNull
    @Override  //리스트뷰 처음 생성될 떄 생명주기 뜻함
    public Adapter_Subscribe_ContentsList.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab3_subscribe_recycle_contentslist, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override  //실제 리스트뷰 추가될 때
    public void onBindViewHolder(@NonNull final Adapter_Subscribe_ContentsList.CustomViewHolder holder, final int position) {

        holder.tv_ctTitle.setText(contentsList.get(position).getCtTitle());
        holder.tv_ctReleaseDate.setText(contentsList.get(position).getCtReleaseDate());


        //클릭
        holder.layout_contents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                STATICDATA.CT_SEQNO = contentsList.get(position).getCtSeqno(); // ctSeqno 보내기 바꿔주기
                Log.v("ctSeqno", String.valueOf(STATICDATA.CT_SEQNO));

                //Intent intent = new Intent(view.getContext(), Fragment_Subscribe_ContentsList.class); // ContentsList로이
                //view.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != contentsList ? contentsList.size() : 0);

    }


    //사용할것 선언
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_contents;

        TextView tv_ctTitle;
        TextView tv_ctReleaseDate;




        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout_contents = itemView.findViewById(R.id.Ll_contents);

            this.tv_ctTitle = itemView.findViewById(R.id.tv_ctTitle);
            this.tv_ctReleaseDate = itemView.findViewById(R.id.tv_ctReleaseDate);



        }
    }




}//---------
