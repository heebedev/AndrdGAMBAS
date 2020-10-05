package com.pjt.andrdgambas.PRDDETAIL;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;
import com.pjt.andrdgambas.SUBSCRIBE.Activity_Subscribe_Contents_Detail;
import com.pjt.andrdgambas.SUBSCRIBE.NetworkTask_Subscribe_Insert_Update;

public class NewReviewActivity extends AppCompatActivity {

    String logTitle = "PrdDetailActivity";

    String userSeq;
    String prdSeq;
    String CENTIP;

    EditText title,detail;
    ImageView rate1,rate2,rate3,rate4,rate5;
    Button submit;

    String rate = "5";
    String urlAddrInsertReview;

    private void init() {
        userSeq = STATICDATA.USEQNO;
        prdSeq = STATICDATA.PRD_SEQNO;
        CENTIP = STATICDATA.CENTIP;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_submit);

        init();
        setItems();
        setClickListeners();
    }

    public void setItems(){
        title = findViewById(R.id.et_reviewSubmit_title);
        detail = findViewById(R.id.et_reviewSubmit_detail);
        rate1 = findViewById(R.id.iv_reviewSubmit_rating1);
        rate2 = findViewById(R.id.iv_reviewSubmit_rating2);
        rate3 = findViewById(R.id.iv_reviewSubmit_rating3);
        rate4 = findViewById(R.id.iv_reviewSubmit_rating4);
        rate5 = findViewById(R.id.iv_reviewSubmit_rating5);
        submit = findViewById(R.id.btn_reivewSubmit_submit);
    }

    public void setClickListeners(){
        rate1.setOnClickListener(click);
        rate2.setOnClickListener(click);
        rate3.setOnClickListener(click);
        rate4.setOnClickListener(click);
        rate5.setOnClickListener(click);
        submit.setOnClickListener(click);
    }

    public void submitReview(){
        String strTitle = title.getText().toString();
        String strDetail = detail.getText().toString();

        urlAddrInsertReview = "http://" + STATICDATA.CENTIP + ":8080/gambas/PrdReviewInsert.jsp?"; //get방식으로 넘겨줌
        urlAddrInsertReview = urlAddrInsertReview + "&rTitle=" + strTitle + "&rContent=" + strDetail + "&rGrade=" + rate
                + "&uSeqno=" + userSeq + "&prdSeqno=" + prdSeq;
        try {
            // 좋아요 갯수 변경 + 1
            NetworkTask_PrdDetail_InsertReview networkTask_PrdDetail_InsertReview
                    = new NetworkTask_PrdDetail_InsertReview(NewReviewActivity.this, urlAddrInsertReview);

            networkTask_PrdDetail_InsertReview.execute().get();

            //Toast.makeText(Activity_Subscribe_Contents_Detail.this, urlAddrInsertLike + "입력되었습니다.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("좋아요  :", "등록 오류");
        }

    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.iv_reviewSubmit_rating1:
                    rate = "1";
                    rate1.setImageResource(R.drawable.fullstar);
                    rate2.setImageResource(R.drawable.emptystar);
                    rate3.setImageResource(R.drawable.emptystar);
                    rate4.setImageResource(R.drawable.emptystar);
                    rate5.setImageResource(R.drawable.emptystar);
                    break;

                case R.id.iv_reviewSubmit_rating2:
                    rate = "2";
                    rate1.setImageResource(R.drawable.fullstar);
                    rate2.setImageResource(R.drawable.fullstar);
                    rate3.setImageResource(R.drawable.emptystar);
                    rate4.setImageResource(R.drawable.emptystar);
                    rate5.setImageResource(R.drawable.emptystar);
                    break;

                case R.id.iv_reviewSubmit_rating3:
                    rate = "3";
                    rate1.setImageResource(R.drawable.fullstar);
                    rate2.setImageResource(R.drawable.fullstar);
                    rate3.setImageResource(R.drawable.fullstar);
                    rate4.setImageResource(R.drawable.emptystar);
                    rate5.setImageResource(R.drawable.emptystar);
                    break;

                case R.id.iv_reviewSubmit_rating4:
                    rate = "4";
                    rate1.setImageResource(R.drawable.fullstar);
                    rate2.setImageResource(R.drawable.fullstar);
                    rate3.setImageResource(R.drawable.fullstar);
                    rate4.setImageResource(R.drawable.fullstar);
                    rate5.setImageResource(R.drawable.emptystar);
                    break;

                case R.id.iv_reviewSubmit_rating5:
                    rate = "5";
                    rate1.setImageResource(R.drawable.fullstar);
                    rate2.setImageResource(R.drawable.fullstar);
                    rate3.setImageResource(R.drawable.fullstar);
                    rate4.setImageResource(R.drawable.fullstar);
                    rate5.setImageResource(R.drawable.fullstar);
                    break;

                case R.id.btn_reivewSubmit_submit:
                    submitReview();
                    finish();
                    break;
            }
        }
    };


}
