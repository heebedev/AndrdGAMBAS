package com.pjt.andrdgambas.FIREBASE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.R;

import java.io.File;

/*
Firebase File Webview에 불러오기
 */
public class FirebaseGetDataActivity extends AppCompatActivity {
    WebView webView;
    ProgressDialog pDialog;
    private static final String TAG = "Load Firebase";

    String filename = "202010115334pink.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_get_data);

        webView = findViewById(R.id.wb_firebase);
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.setNetworkAvailable(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        // WebView 핸드폰 화면과 크기 맞추기
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//
//// 202010114840심수빈 포트폴리오.pdf
//        String url = "";
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
//
//


        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setNetworkAvailable(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // WebView 핸드폰 화면과 크기 맞추기
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // Sets whether the DOM storage API is enabled.
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("http://www.pdf995.com/samples/pdf.pdf");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        // file 이름 DB에서 가져와서 child에 넣기
        // gambasFiles : storage의 파일 이름
        StorageReference dateRef = storageRef.child("gambasFiles/"+filename);
        String imgurl =
                dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Firebase URL", uri.toString());
                        webView.loadUrl(uri.toString());

                    }
                }).toString();

    }

}

