package com.pjt.andrdgambas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.helper.Utility;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    Button btn_email;
    TextView tv_signUp;
    Intent intent;

    //카카오로그인
    com.kakao.usermgmt.LoginButton btn_kakao_login;
    SessionCallback kakaoLogin;
    Session session;

    // 해시키 받기
    public String getKeyHash(final Context context) {
        PackageInfo packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null) return null;
        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update((signature.toByteArray()));

                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.v("Tag", "디버그 keyHash" + signature, e);
            }
        } return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("Tag",getKeyHash(LoginActivity.this));
        btn_email = findViewById(R.id.btn_email);
        tv_signUp = findViewById(R.id.tv_signUp);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);

        //카카오 로그인 콜백 초기화
        kakaoLogin = new SessionCallback(getApplicationContext());
        Session.getCurrentSession().addCallback(kakaoLogin.sessionCallback);

        //앱 실행 시 로그인 토큰이 있으면 자동으로 로그인 수행

//        session = Session.getCurrentSession();
//        session.checkAndImplicitOpen();

        btn_email.setOnClickListener(onClickListener);
        tv_signUp.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_email:
                    intent = new Intent(LoginActivity.this, EmailLoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_kakao_login:
                    btn_kakao_login.performClick();
                    break;
                case R.id.tv_signUp:
                    intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(kakaoLogin.sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}