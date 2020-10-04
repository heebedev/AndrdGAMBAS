package com.pjt.andrdgambas.PRDDETAIL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class Adapter_PrdDetailList_Review extends RecyclerView.Adapter<Adapter_PrdDetailList_Review.viewHolder> {

    private ArrayList<Bean_PrdDetailList> reviewList;

    public Adapter_PrdDetailList_Review(ArrayList<Bean_PrdDetailList> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public Adapter_PrdDetailList_Review.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_review, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_PrdDetailList_Review.viewHolder holder, int position) {

        holder.title.setText(reviewList.get(position).getRtitle());
        holder.grade.setText(reviewList.get(position).getRgrade());
        holder.context.setText(reviewList.get(position).getRcontext());

    }

    @Override
    public int getItemCount() {
        return (null != reviewList ? reviewList.size() : 0);
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView title,grade,context;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_recycleReview_writer);
            this.grade = itemView.findViewById(R.id.tv_recycleReview_rating);
            this.context = itemView.findViewById(R.id.tv_recycleReview_cmt);
        }
    }
}