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

    String urlAddrUpdateCommentValidation; // 댓글수정

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

        //댓글작성자만 삭제 가능하도록 삭제 표시
        if(commentlist.get(position).getuSeqno().equals(STATICDATA.USEQNO)){
            holder.tv_delete_comment.setVisibility(View.VISIBLE);
        }else{
            holder.tv_delete_comment.setVisibility(View.GONE);
        }

        //삭제버튼 클릭 하면 ctSeqno 가져옴
        holder.tv_delete_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSeqCmt(holder.getAdapterPosition()); // 클릭한 댓글 seq_cmt가져오기
                Activity_Subscribe_Contents_Detail activity_subscribe_contents_detail = new Activity_Subscribe_Contents_Detail();
                activity_subscribe_contents_detail.UpdateComment();//DB에서 validation = 1(삭제)로 변경

            }

                //seq_cmt
                private void getSeqCmt(int position) {
                    try {
                        String cmSeqno = commentlist.get(position).getCmSeqno();
                        STATICDATA.CM_SEQNO = cmSeqno;
                        Log.v("Current", "코멘트번호는?" + cmSeqno);
                        commentlist.remove(position); // 어레이리스트에서 삭제
                        notifyItemRemoved(position); //새로고침

                    } catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                }

        });

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
