package com.pjt.andrdgambas.SUBSCRIBE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class Activity_Subscribe_Contents_Detail extends AppCompatActivity {

    final static String TAG = "Activity_Contents Details";

    ImageView back, like_iv, unlike_iv;
    TextView ctTitle_tv, countlikecontents_tv, context_tv;
    EditText input_comment_et;
    Button insert_comment_btn;
    WebView webView;
    WebSettings mWebSettings; //웹뷰세팅

    // 콘텐츠 디테일
    ArrayList<Bean_Subscribe> contentsDetails; // 콘텐츠 내용
    String urlAddrDetailsContents;

    // 좋아요
    String urlAddrInsertLike;
    String urlAddrUpdateLike;

    // 댓글
    ArrayList<Bean_Subscribe> commentlist; // 댓글리스트
    String urlAddrContentsCommentList;
    String urlAddrUpdateCommentValidation; // 댓글 삭제
    String urlAddrInsertComment; // 댓글 입력
    RecyclerView rv_comments;
    Adapter_Subscribe_Contents_CommentList adapter_commentlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3_activity_subscribe_contents_detail);

        // 연결 //
        ctTitle_tv = findViewById(R.id.tv_contents_ctTitle);
        like_iv = findViewById(R.id.iv_contents_like);
        unlike_iv = findViewById(R.id.iv_contents_unlike);
        countlikecontents_tv = findViewById(R.id.tv_contents_countlikecontents);
        context_tv = findViewById(R.id.tv_contents_ctContext);
        input_comment_et = findViewById(R.id.et_contents_input_comment);
        insert_comment_btn = findViewById(R.id.btn_contents_insert_comment);
        webView = findViewById(R.id.wv_contents_ctfile);

        back = findViewById(R.id.iv_back_contentsDetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); //
            }
        });



        // 웹뷰
        webView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = webView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        //webView.loadUrl("http://" + STATICDATA.CENTIP + ":8080/ftp/testvideo.mp4");



//        // 이미지
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.loadUrl("http://172.30.1.43:8080/ftp/testvideo.mp4");

        // 구글뷰를 사용하여 pdf 띄우기
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        String url = "http://172.30.1.43:8080/ftp/store.png";
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
////        webView.loadUrl("https://docs.google.com/viewerng/viewer?embedded=true&url="+url);


    }

    @Override
    protected void onStart() {

        ConnectGetContentsDetails(); // 내용 불러오기
        ConnectGetCommentList(); // 댓글 리스트

        super.onStart();
    }


    // 콘텐츠 디테일 불러오기
    private void ConnectGetContentsDetails() {
        urlAddrDetailsContents = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsViewQuery.jsp?";
        urlAddrDetailsContents = urlAddrDetailsContents + "ctSeqno=" + STATICDATA.CT_SEQNO + "&uSeqno=" + HomeData.USERID ; //  ctSeqno, uSeqno 넘겨줌
        Log.v("contentsDetails", "콘텐츠리스트 url" +urlAddrDetailsContents);

        try{
            NetworkTask_Subscribe_Contents_Details_Select selectNetworkTask = new NetworkTask_Subscribe_Contents_Details_Select( Activity_Subscribe_Contents_Detail.this, urlAddrDetailsContents);
            Object obj = selectNetworkTask.execute( ).get( );
            contentsDetails = (ArrayList<Bean_Subscribe>) obj;
            Log.v("콘텐츠내용",  contentsDetails.get(0).getCtTitle()+ contentsDetails.get(0).getCountlikecontents() + contentsDetails.get(0).getCtContext());

            ctTitle_tv.setText( contentsDetails.get(0).getCtTitle());
            countlikecontents_tv.setText( contentsDetails.get(0).getCountlikecontents());
            context_tv.setText( contentsDetails.get(0).getCtContext());

            webView.loadUrl("http://" + STATICDATA.CENTIP + ":8080/ftp/"+contentsDetails.get(0).getCtfile()); // 웹

            // 내가 좋아요했는지 확인하고 이미지 숨기기 0 = unlike 보이기, 1 = liked 보이기
            if( contentsDetails.get(0).getCheckmylikecontents().equals("0")) {
                unlike_iv.setVisibility(View.VISIBLE); // 빈하트
                like_iv.setVisibility(View.INVISIBLE);
            } else {
                unlike_iv.setVisibility(View.INVISIBLE); // 있을때 빨간하트
                like_iv.setVisibility(View.VISIBLE);
            }

            unlike_iv.setOnClickListener( onClickListener );
            like_iv.setOnClickListener( onClickListener );
            insert_comment_btn.setOnClickListener( onClickListener );

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 클릭 리스너
    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.iv_contents_unlike:

                    // 좋아요 누를때 이미지 변경
                    unlike_iv.setVisibility(view.INVISIBLE);
                    like_iv.setVisibility(view.VISIBLE);

                    // 좋아요 Insert
                    String uSeqno = HomeData.USERID;
                    String ctSeqno = contentsDetails.get(0).getCtSeqno();
                    String prdSeqno = contentsDetails.get(0).getPrdSeqno();

                    urlAddrInsertLike = "";
                    urlAddrInsertLike = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsLikeInsert.jsp?"; //get방식으로 넘겨줌
                    urlAddrInsertLike = urlAddrInsertLike + "&uSeqno=" + uSeqno + "&ctSeqno=" + ctSeqno + "&prdSeqno=" + prdSeqno;
                    try {
                        // 좋아요 갯수 변경 + 1
                        countlikecontents_tv.setText(Integer.toString(Integer.parseInt(countlikecontents_tv.getText().toString()) + 1));

                        NetworkTask_Subscribe_Insert_Update networkTask_subscribe_insertUpdate
                                = new NetworkTask_Subscribe_Insert_Update(Activity_Subscribe_Contents_Detail.this, urlAddrInsertLike);
                        networkTask_subscribe_insertUpdate.execute().get();

                        //Toast.makeText(Activity_Subscribe_Contents_Detail.this, urlAddrInsertLike + "입력되었습니다.", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("좋아요  :", "등록 오류");
                    }
                    break;

                case R.id.iv_contents_like:

                    // 좋아요 해제할 떄 이미지 변경
                    unlike_iv.setVisibility(view.VISIBLE);
                    like_iv.setVisibility(view.INVISIBLE);

                    // 좋아요 Update
                    uSeqno = HomeData.USERID;
                    ctSeqno = contentsDetails.get(0).getCtSeqno();

                    urlAddrUpdateLike = "";
                    urlAddrUpdateLike = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsLikeUpdate.jsp?"; //get방식으로 넘겨줌
                    urlAddrUpdateLike = urlAddrUpdateLike + "&uSeqno=" + uSeqno + "&ctSeqno=" + ctSeqno;
                    try {
                        // 좋아요 갯수 변경 - 1
                        countlikecontents_tv.setText(Integer.toString(Integer.parseInt(countlikecontents_tv.getText().toString()) - 1));

                        NetworkTask_Subscribe_Insert_Update networkTask_subscribe_insertUpdate
                                = new NetworkTask_Subscribe_Insert_Update(Activity_Subscribe_Contents_Detail.this, urlAddrUpdateLike);
                        networkTask_subscribe_insertUpdate.execute().get();

                        //Toast.makeText(Activity_Subscribe_Contents_Detail.this, urlAddrUpdateLike + "수정완료.", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("좋아요 :", "수정 오류");
                    }
                    break;

                case R.id.btn_contents_insert_comment:
                    uSeqno = HomeData.USERID;
                    ctSeqno = contentsDetails.get(0).getCtSeqno();
                    String cmcontext = input_comment_et.getText().toString().trim();

                    //댓글 null값 등록 방지
                    if (cmcontext.length() < 5) {
                        Toast.makeText(Activity_Subscribe_Contents_Detail.this, "5자 이상 작성해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (cmcontext.length() >= 200) {
                        Toast.makeText(Activity_Subscribe_Contents_Detail.this, "200자 미만 작성해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddrInsertComment = "";
                        urlAddrInsertComment = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsCommentInsert.jsp?"; //get방식으로 넘겨줌
                        urlAddrInsertComment = urlAddrInsertComment + "&uSeqno=" + uSeqno + "&ctSeqno=" + ctSeqno + "&cmcontext=" + cmcontext;

                        try {
                            NetworkTask_Subscribe_Insert_Update networkTask_subscribe_insertUpdate
                                    = new NetworkTask_Subscribe_Insert_Update(Activity_Subscribe_Contents_Detail.this, urlAddrInsertComment);
                            networkTask_subscribe_insertUpdate.execute().get();

                            input_comment_et.setText(""); // 댓글입력창 리셋
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.v("댓글입력  :", "입력 오류");
                        }

                        //리스트 다시불러오기
                        ConnectGetCommentList();
                    }
                    break;
            }
        }
    };

    // 댓글 리스트 불러오기
    private void ConnectGetCommentList() {
        urlAddrContentsCommentList = "http://" + STATICDATA.CENTIP + ":8080/gambas/commentListQuery.jsp?";
        urlAddrContentsCommentList = urlAddrContentsCommentList + "ctSeqno=" + STATICDATA.CT_SEQNO; //  ctSeqno
        Log.v("contents Comments", "댓글리스트url" +urlAddrContentsCommentList);

        try{
            NetworkTask_Subscribe_Contents_Comments_Select NetworkTask = new NetworkTask_Subscribe_Contents_Comments_Select( Activity_Subscribe_Contents_Detail.this, urlAddrContentsCommentList);
            commentlist = (ArrayList<Bean_Subscribe>) NetworkTask.execute().get();

            rv_comments = findViewById(R.id.rv_contents_comment_recyclerview);// Recyclerview연결
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            rv_comments.setLayoutManager(layoutManager);
            adapter_commentlist = new Adapter_Subscribe_Contents_CommentList(commentlist, Activity_Subscribe_Contents_Detail.this);
            rv_comments.setAdapter(adapter_commentlist);

        } catch (Exception e) {
            e.printStackTrace();

        }
        super.onStart();
    }



    //댓글삭제
    public void UpdateComment(){
        //클릭한 seq_cmt가져옴
        String cmSeqno = STATICDATA.CM_SEQNO;

        urlAddrUpdateCommentValidation="";
        urlAddrUpdateCommentValidation = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsCommentUpdate.jsp?"; //get방식으로 넘겨줌
        urlAddrUpdateCommentValidation = urlAddrUpdateCommentValidation + "&cmSeqno=" + cmSeqno;

        try {
            NetworkTask_Subscribe_Insert_Update networkTask_subscribe_insertUpdate
                    = new NetworkTask_Subscribe_Insert_Update(Activity_Subscribe_Contents_Detail.this, urlAddrUpdateCommentValidation);
            networkTask_subscribe_insertUpdate.execute().get();
        }catch (Exception e){
            e.printStackTrace();
            Log.v("댓글삭제  :", "수정 오류");
        }
    }


    @Override // 뒤로가기
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }





}//--------
