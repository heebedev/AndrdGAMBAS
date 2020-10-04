package com.pjt.andrdgambas.MYCHANNEL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pjt.andrdgambas.MYINFO.MyLikeReview;
import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class ContentAdapter extends BaseAdapter {
    private Context mContext;
    private int layout = 0;
    private ArrayList<Content> data;
    private LayoutInflater inflater;

    public ContentAdapter(Context mContext, int layout, ArrayList<Content> data) {
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
        return data.get(position).getCtTitle();
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

        TextView tv_title = convertView.findViewById(R.id.ct_title);
        TextView tv_context = convertView.findViewById(R.id.ct_context);
        TextView tv_date = convertView.findViewById(R.id.ct_date);

        tv_title.setText(data.get(position).getCtTitle());
        tv_context.setText(data.get(position).getCtContext());
        tv_date.setText(data.get(position).getCtRegistDate());


        return convertView;
    }

}
