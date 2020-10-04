package com.pjt.andrdgambas.NOTICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;
import java.util.ArrayList;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.mViewHolder> {

    private Context mContext;
    private ArrayList<NoticeList> data;

    public NoticeListAdapter(Context mContext, ArrayList<NoticeList> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.tab2_notice_customcell, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        setHolder(holder, position);
    }

    private void setHolder(@NonNull mViewHolder holder, int position) {
        String uName = STATICDATA.UNAME;
        String uDBName = data.get(position).getnName();
        String uDetailName = data.get(position).getnDetailName();
        String uCode= data.get(position).getnCode();

        if(uDBName.equals(uName)) {
            switch(uCode) {
                case "like":
                    holder.noticeImage.setImageResource(R.drawable.speaking_blue);
                    holder.noticeText.setText(uName + " 님이 " + uDetailName + "을 좋아합니다!");
                    break;
                case "subs":
                    holder.noticeImage.setImageResource(R.drawable.speaking_green);
                    holder.noticeText.setText(uName + " 님이 " + uDetailName + "을 구독하셨어요!");
                    break;
                case "review":
                    holder.noticeImage.setImageResource(R.drawable.speaking_pink);
                    holder.noticeText.setText(uName + " 님이 " + uDetailName + "에 후기를 남겼어요!");
                    break;
            }
        } else {
            switch(uCode) {
                case "like":
                    holder.noticeImage.setImageResource(R.drawable.speaking_blue);
                    holder.noticeText.setText(uDetailName + "을 좋아합니다!");
                    break;
                case "subs":
                    holder.noticeImage.setImageResource(R.drawable.speaking_green);
                    holder.noticeText.setText(uDetailName + "을 구독하셨어요!");
                    break;
                case "review":
                    holder.noticeImage.setImageResource(R.drawable.speaking_pink);
                    holder.noticeText.setText(uDetailName + "에 후기를 남겼어요!");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class mViewHolder extends RecyclerView.ViewHolder {

        public TextView noticeText;
        public ImageView noticeImage;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            noticeImage = itemView.findViewById(R.id.iv_noticeList);
            noticeText = itemView.findViewById(R.id.tv_noticeList);

        }
    }

}
