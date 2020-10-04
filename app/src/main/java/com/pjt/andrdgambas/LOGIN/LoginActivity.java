package com.pjt.andrdgambas.LOGIN;

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
import android.widget.EditText;
import android.widget.TextView;
import com.kakao.auth.Session;
import com.kakao.util.helper.Utility;
import com.pjt.andrdgambas.FIREBASE.AddContentsActivity;

import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.HOME.MainActivity;
import com.pjt.andrdgambas.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_email, et_pw;
    TextView tv_signUp, tv_dialog;
    Intent intent;
    String centIP = HomeData.CENIP;
    String returnpwd = "";

    String email, pw, uSeqno, urlAddr;

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
        btn_login = findViewById(R.id.btn_login);
        tv_signUp = findViewById(R.id.tv_signUp);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);
        et_email = findViewById(R.id.et_login_loginID);
        et_pw = findViewById(R.id.et_login_loginPW);
        tv_dialog = findViewById(R.id.tv_login_loginDialog);

        //카카오 로그인 콜백 초기화
        kakaoLogin = new SessionCallback(getApplicationContext());
        Session.getCurrentSession().addCallback(kakaoLogin.sessionCallback);

        //앱 실행 시 로그인 토큰이 있으면 자동으로 로그인 수행

//        session = Session.getCurrentSession();
//        session.checkAndImplicitOpen();

        btn_login.setOnClickListener(onClickListener);
        tv_signUp.setOnClickListener(onClickListener);



        // 수빈 테스트용 추가
        Button btn_firebase = findViewById(R.id.btn_firebase);
        btn_firebase.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login:
                    email = et_email.getText().toString().trim();
                    pw = et_pw.getText().toString().trim();
                    if (email.length() == 0 || pw.length() == 0) {
                        tv_dialog.setText("이메일 또는 비밀번호를 확인해주세요.");
                        tv_dialog.setVisibility(View.VISIBLE);
                    }else{
                        urlAddr = "";
                        urlAddr = "http://" + centIP + ":8080/gambas/GAMBAS_emailLogin.jsp?"; // centIP 는 항상 위에
                        urlAddr = urlAddr + "uEmail=" + email;
                        Log.v("URL",urlAddr);
                        connectionloginData();
                    }
                    break;
                case R.id.btn_kakao_login:
                    btn_kakao_login.performClick();
                    break;

                case R.id.btn_firebase:
                    intent = new Intent(LoginActivity.this, AddContentsActivity.class);
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

    private void connectionloginData() {

        try {
            LoginNetworkTask loginNetworkTask = new LoginNetworkTask(LoginActivity.this, urlAddr);
            returnpwd = loginNetworkTask.execute().get().toString();

            Log.v("패스워드",returnpwd);
            if (returnpwd.equals("null")){ // 디비에서 가져온 패스워드값이 null 이면 이메일이 없어요
                tv_dialog.setText("이메일을 확인해주세요.");

            }else if(returnpwd.equals(pw)){ // 디비에서 가져온 비밀번호랑 입력한 비밀번호가 동일하면 로그인성공
                // 로그인 성공
                intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("uSeqno", HomeData.USERID);
                startActivity(intent);
            }
            else { // 이메일과 비밀번호가 디비에 저장되어 있는거랑 다름
                tv_dialog.setText("비밀번호를 확인해주세요.");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}