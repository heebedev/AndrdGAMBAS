package com.pjt.andrdgambas.SUBSCRIBE;

import android.annotation.SuppressLint;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    // 댓글
    ArrayList<Bean_Subscribe> commentlist; // 댓글리스트
    String urlAddrContentsCommentList;
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


    // 콘텐츠 디테일 불러오기
    private void ConnectGetContentsDetails() {
        urlAddrDetailsContents = "http://" + STATICDATA.CENTIP + ":8080/gambas/contentsViewQuery.jsp?";
        urlAddrDetailsContents = urlAddrDetailsContents + "ctSeqno=" + STATICDATA.CT_SEQNO + "&uSeqno=" + STATICDATA.USEQNO ; //  ctSeqno, uSeqno 넘겨줌
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


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 콘텐츠 디테일 불러오기
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



    @Override // 뒤로가기
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {

        ConnectGetContentsDetails(); // 내용 불러오기
        ConnectGetCommentList(); // 댓글 리스트 

        super.onStart();
    }



}//--------
