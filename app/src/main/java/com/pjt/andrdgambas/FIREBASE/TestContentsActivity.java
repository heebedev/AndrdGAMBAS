package com.pjt.andrdgambas.FIREBASE;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pjt.andrdgambas.R;


/*
Firebase File Webview에 불러오기
 */
public class TestContentsActivity extends AppCompatActivity {
    WebView webView;
    private static final String TAG = "Load Firebase";
    // ***** 테스트용
    String fileName = "202010035144image.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_contents);

        webView = findViewById(R.id.wb_firebase);
        webView.getSettings().setJavaScriptEnabled(true); // 자바 스크립트 허용
        webView.setWebChromeClient(new WebChromeClient()); // 웹뷰에서 크롬 허용
        webView.getSettings().getAllowFileAccess();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference dateRef = storageRef.child("uImage/"+fileName);
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                webView.loadUrl(downloadUrl.toString());
            }
        });

    }
}