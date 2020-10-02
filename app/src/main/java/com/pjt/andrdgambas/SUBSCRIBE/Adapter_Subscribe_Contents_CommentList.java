package com.pjt.andrdgambas.SUBSCRIBE;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class Adapter_Subscribe_Contents_CommentList extends RecyclerView.Adapter<Adapter_Subscribe_Contents_CommentList.CustomViewHolder>  {

    //Field
    private ArrayList<Bean_Subscribe> commentlist;

    //Constructor
    public Adapter_Subscribe_Contents_CommentList(ArrayList<Bean_Subscribe> arrayList, Activity_Subscribe_Contents_Detail activity_subscribe_contents_detail) {
        this.commentlist = arrayList;
    }


    @NonNull
    @Override  //리스트뷰 처음 생성될 떄 생명주기 뜻함
    public Adapter_Subscribe_Contents_CommentList.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab3_subscribe_recycle_commentlist, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override  //실제 리스트뷰 추가될 때
    public void onBindViewHolder(@NonNull final Adapter_Subscribe_Contents_CommentList.CustomViewHolder holder, final int position) {

        holder.tv_uName.setText(commentlist.get(position).getuName());
        holder.tv_cmRegistDate.setText(commentlist.get(position).getCmRegistDate());
        holder.tv_cmcontext.setText(commentlist.get(position).getCmcontext());


    }

    @Override
    public int getItemCount() {
        return (null != commentlist ? commentlist.size() : 0);

    }


    //사용할것 선언
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tv_uName, tv_cmRegistDate, tv_cmcontext;
        TextView tv_delete_comment;




        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_uName= itemView.findViewById(R.id.tv_contents_comment_uName);
            this.tv_cmRegistDate = itemView.findViewById(R.id.tv_contents_comment_cmRegistDate);
            this.tv_cmcontext = itemView.findViewById(R.id.tv_contents_comment_cmcontext);
            this.tv_delete_comment = itemView.findViewById(R.id.tv_contents_comment_delete);



        }
    }




}//---------
