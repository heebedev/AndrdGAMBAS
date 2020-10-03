package com.pjt.andrdgambas.NOTICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class NoticeListAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<NoticeList> data = null;
    private LayoutInflater inflater = null;

    public TextView noticeText;
    public ImageView noticeImage;

    public NoticeListAdapter(Context mContext, int layout, ArrayList<NoticeList> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).nName;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);

        }

        String uName = STATICDATA.UNAME;
        String uDBName = data.get(position).getnName();
        String uDetailName = data.get(position).getnDetailName();
        String uCode= data.get(position).getnCode();


        noticeText = convertView.findViewById(R.id.tv_noticeList);
        noticeImage = convertView.findViewById(R.id.iv_noticeList);

        //noticeText.setText(data.get(position).getMoimName());
//        if uDBName.equals(uName) {
//            switch uCode {
//                case "like":
//                    noticeImage.setImageResource(R.drawable.speaking_blue);
//                    noticeText.setText(uName + " 님이 " + uDetailName + "을 좋아합니다!");
//                    break;
//                case "subs":
//                    noticeImage.setImageResource(R.drawable.speaking_green);
//                    noticeText.setText(uName + " 님이 " + uDetailName + "을 구독하셨어요!");
//                    break;
//                case "review":
//                    noticeImage.setImageResource(R.drawable.speaking_green);
//                    noticeText.setText(uName + " 님이 " + uDetailName + "에 후기를 남겼어요!");
//                    break;
//            }
//        } else {
//            switch uCode {
//                case "like":
//                    noticeImage.setImageResource(R.drawable.speaking_blue);
//                    noticeText.setText(uDetailName + "을 좋아합니다!");
//                    break;
//                case "subs":
//                    noticeImage.setImageResource(R.drawable.speaking_green);
//                    noticeText.setText(uDetailName + "을 구독하셨어요!");
//                    break;
//                case "review":
//                    noticeImage.setImageResource(R.drawable.speaking_pink);
//                    noticeText.setText(uDetailName + "에 후기를 남겼어요!");
//                    break;
//            }
//        }

        return convertView;
    }
}
