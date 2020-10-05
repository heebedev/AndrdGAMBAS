package com.pjt.andrdgambas.MYCHANNEL;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;

import java.util.ArrayList;


public class MyChannelAdapter extends RecyclerView.Adapter<MyChannelAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MyChannel> mData = null ;
    private RequestManager manager;
    public String fileName = "test.png";

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MyChannelAdapter(Context context, ArrayList<MyChannel> list) {
        this.context = context;
        this.mData = list ;
    }
    //ClickEvent 이름을 정하고 인터페이스로 선언
    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }
    //만든 인터페이스를 전역변수로 선언
    private OnItemClickListener mListener = null;
    //OnItemClickListener 를 객체 생성할때 값을 넣어주기 위해서 선언
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title ,tv_price, tv_term, tv_day;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos =getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION){
                        // 선택한 position 값 구하기 (번호)
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (mListener != null) {
                                mListener.onItemClickListener(view, position);
                            }
                        }
                    }
                }
            });



            // 뷰 객체에 대한 참조. (hold strong reference)
            tv_title = itemView.findViewById(R.id.tv_mych_title) ;
            tv_price = itemView.findViewById(R.id.tv_mych_price);
            tv_term = itemView.findViewById(R.id.tv_mych_term);
            tv_day = itemView.findViewById(R.id.tv_mych_day);
            imageView = itemView.findViewById(R.id.iv_mych_prdimg);





        }

    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MyChannelAdapter(ArrayList<MyChannel> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MyChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.productlistview, parent, false) ;
        MyChannelAdapter.ViewHolder vh = new MyChannelAdapter.ViewHolder(view) ;



        return vh ;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final MyChannelAdapter.ViewHolder holder, int position) {
        String title = mData.get(position).getProductTitle();
        String seqno =mData.get(position).getProductSeqno();
        String price = mData.get(position).getProductPrice();
        String term = mData.get(position).getProductTerm();
        String day = mData.get(position).getProductReleaseDay();
        String image = mData.get(position).getProductImage();

        holder.tv_title.setText(title) ;
        holder.tv_price.setText(price + "원");
        holder.tv_term.setText(term);
        holder.tv_day.setText(day);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference dateRef = storageRef.child("uImage/" + fileName); // fileName은 테스트용!!! image로 넣어야함.
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.v("Firebase URL", uri.toString());
                Glide.with(context)
                        .load(uri.toString())
                        .apply(new RequestOptions().centerCrop())
                        .into(holder.imageView);
            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }



}
