package com.pjt.andrdgambas.MYINFO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class LikeReviewAdapter extends BaseAdapter {
    private Context mContext;
    private int layout = 0;
    private ArrayList<MyLikeReview> data;
    private LayoutInflater inflater;

    public LikeReviewAdapter(Context mContext, int layout, ArrayList<MyLikeReview> data) {
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
        return data.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }

        TextView tv_title = convertView.findViewById(R.id.tv_title);
        TextView tv_subTitle = convertView.findViewById(R.id.tv_subTitle);

        tv_title.setText(data.get(position).getTitle());
        tv_subTitle.setText(data.get(position).getSubTitle());

        return convertView;
    }

}
