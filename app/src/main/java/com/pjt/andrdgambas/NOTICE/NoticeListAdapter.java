package com.pjt.andrdgambas.NOTICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pjt.andrdgambas.R;

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
        return data.get(position).title;
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

        noticeText = convertView.findViewById(R.id.tv_noticeList);
        noticeImage = convertView.findViewById(R.id.iv_noticeList);

        //noticeText.setText(data.get(position).getMoimName());


        return convertView;
    }
}
