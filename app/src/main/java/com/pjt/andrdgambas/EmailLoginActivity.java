package com.pjt.andrdgambas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

public class EmailLoginActivity extends AppCompatActivity {

    TextView tv_dialog2;
    Button btn_login;
    EditText edit_email, edit_pw;
    TextView tv_findId;
    Intent intent;
    ImageView iv_main;
    String email, pw, uSeqno, urlAddr;
    String centIP = HomeData.CENIP;
    String returnpwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        btn_login = findViewById(R.id.btn_login);
        tv_dialog2 = findViewById(R.id.tv_dialog2);
        edit_email = findViewById(R.id.login_email);
        edit_pw = findViewById(R.id.login_pw);
        iv_main = findViewById(R.id.login_main);
        tv_findId = findViewById(R.id.tv_findId);

        tv_findId.setOnClickListener(onClickListener);
        iv_main.setOnClickListener(onClickListener);
        btn_login.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_login:
                    email = edit_email.getText().toString().trim();
                    pw = edit_pw.getText().toString().trim();
                    if (email.length() == 0 || pw.length() == 0) {
                        tv_dialog2.setText("이메일 또는 비밀번호를 확인해주세요.");
                    }else{
                        urlAddr = "";
                        urlAddr = "http://" + centIP + ":8080/gambas/GAMBAS_emailLogin.jsp?"; // centIP 는 항상 위에
                        urlAddr = urlAddr + "uEmail=" + email;
                        Log.v("URL",urlAddr);
                        connectionloginData();
                    }
            }
        }
    };
    private void connectionloginData() {

        try {
            LoginNetworkTask loginNetworkTask = new LoginNetworkTask(EmailLoginActivity.this, urlAddr);
            returnpwd = loginNetworkTask.execute().get().toString();

            Log.v("패스워드",returnpwd);
            if (returnpwd.equals("null")){ // 디비에서 가져온 패스워드값이 null 이면 이메일이 없어요
                tv_dialog2.setText("이메일을 확인해주세요.");


            }else if(returnpwd.equals(pw)){ // 디비에서 가져온 비밀번호랑 입력한 비밀번호가 동일하면 로그인성공
                // 로그인 성공
                new AlertDialog.Builder(EmailLoginActivity.this)
                        .setTitle("로그인 성공!")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { // 확인누르면 로그인한 이메일들고 메인페이지로(아직안만들어서 첫페이지)
                                intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                                intent.putExtra("uSeqno", HomeData.USERID);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
            else { // 이메일과 비밀번호가 디비에 저장되어 있는거랑 다름
                tv_dialog2.setText("비밀번호를 확인해주세요.");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}