package com.pjt.andrdgambas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class SignUpActivity extends AppCompatActivity {

    String urlAddr;
    String email, pw, pwChk, name, birth, phone, img;
    String centIp = HomeData.CENIP;
    int [] categoryChked = {0,0,0,0};
    String categoryResult = "";
    CheckBox chk1,chk2, chk3, chk4;
    EditText edit_email, edit_pw, edit_pwChk, edit_name, edit_birth, edit_phone;
    Button btn_chkEmail, btn_signUp, btn_img;
    ImageView imageView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edit_email = findViewById(R.id.signUp_email);
        edit_pw = findViewById(R.id.signUp_pw);
        edit_pwChk = findViewById(R.id.signUp_pwChk);
        edit_name = findViewById(R.id.signUp_name);
        edit_birth = findViewById(R.id.signUp_birth);
        edit_phone = findViewById(R.id.signUp_phone);

        edit_pw.setEnabled(false);
        edit_pwChk.setEnabled(false);
        edit_name.setEnabled(false);
        edit_birth.setEnabled(false);
        edit_phone.setEnabled(false);

        imageView = findViewById(R.id.signUp_img);
        btn_img = findViewById(R.id.signUp_btn_img);
        btn_chkEmail = findViewById(R.id.btn_chkEmail);
        btn_signUp = findViewById(R.id.btn_signUp);
        chk1 = findViewById(R.id.chk_1);
        chk2 = findViewById(R.id.chk_2);
        chk3 = findViewById(R.id.chk_3);
        chk4 = findViewById(R.id.chk_4);


        btn_chkEmail.setOnClickListener(onClickListener);
        btn_signUp.setOnClickListener(onClickListener);
        btn_img.setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_signUp:
                    email = edit_email.getText().toString().trim();
                    pw = edit_pw.getText().toString().trim();
                    pwChk = edit_pwChk.getText().toString().trim();
                    name = edit_name.getText().toString().trim();
                    birth = edit_birth.getText().toString().trim();
                    phone = edit_phone.getText().toString().trim();
                    img = "emptyImage.png";
                    categoryChk();
                    blankChk();
                case R.id.btn_chkEmail:
                    email = edit_email.getText().toString().trim();
                    urlAddrDivider("idDoubleChk", email, pw, name, birth, phone, img, categoryResult); // 아이디 중복 체크.
            }
        }
    };
    private void blankChk() {
        // 회원가입 이메일 포맷체크, 비밀번호 6자리체크
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("올바른 형식의 이메일 주소를 설정해주세요.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
            edit_email.setError("이메일 확인!");
            edit_email.setFocusable(true);
        } else if (pw.length() < 6) {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("비밀번호를 6자 이상으로 설정해주세요.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
            edit_pw.setError("비밀번호 확인!");
            edit_pw.setFocusable(true);
        } else if (name.length() == 0 || birth.length() == 0 || phone.length() == 0) {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("빈칸을 입력해주세요.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
        }else {
            pwOkChk(); // 패스워드 일치 확인.
        }
    }
    private void pwOkChk() {
        if (pw.equals(pwChk)) {
            urlAddrDivider("signup", email, pw, name, birth, phone, img, categoryResult); // 회원가입.
            // connectRegData(); // 회원가입 DB 연결.
        } else {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("비밀번호가 일치하지 않습니다.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();
            // -------------------------------------------------------------------------------------
        }
    }
    private void IdDoubleChk(int cntId) {
            if(cntId == 1) {
                Toast.makeText(SignUpActivity.this, "사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show();
            }else{
                edit_pw.setEnabled(true);
                edit_pwChk.setEnabled(true);
                edit_name.setEnabled(true);
                edit_birth.setEnabled(true);
                edit_phone.setEnabled(true);
            }
    }
    private void urlAddrDivider(String function, String email, String pw, String name, String birth, String phone, String img, String categoryResult) {
        switch (function) {
            case "idDoubleChk":
                urlAddr = "http://" + centIp + ":8080/gambas/EmailChk_ios.jsp?";
                urlAddr = urlAddr + "uEmail=" + email;
                connectDB("idDoubleChk");
                break;
            case "signup":
                urlAddr = "http://192.168.0.178:8080/gambas/Userinfo_Insert_ios.jsp?";
                urlAddr = urlAddr + "Email=" + email + "&Password=" + pw + "&Name=" + name + "&Birth=" + birth + "&Phone=" + phone + "&Image=" + img + "&interestCategory=" + categoryResult;
                connectDB("signup");
                break;
        }
    }
    private void connectDB(String function) {
        try {
            switch (function) {
                case "idDoubleChk":
                    SJ_IntNetworkTask idDoubleChkNetworkTask = new SJ_IntNetworkTask(SignUpActivity.this, urlAddr);
                    int cntId = idDoubleChkNetworkTask.execute().get();
                    IdDoubleChk(cntId);
                    break;
                case "signup":
                    SJ_InsNetworkTask signupNetworkTask = new SJ_InsNetworkTask(SignUpActivity.this, urlAddr);
                    signupNetworkTask.execute().get();
                    Toast.makeText(SignUpActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void categoryChk() {
        if (chk1.isSelected()) {
            categoryChked[0] = 1;
        }else if(chk2.isSelected()){
            categoryChked[1] = 1;
        }else if(chk3.isSelected()){
            categoryChked[2] = 1;
        }else if(chk4.isSelected()){
            categoryChked[3] = 1;
        }
        String [] category = {"글","그림","영상","음악"};
        int count = 0;

        for(int i = 0; i < categoryChked.length; i++){
            if(categoryChked[i] == 1 && count == 0){
                categoryResult = category[i];
                count = 1;
            }else if(categoryChked[i] == 1 && count == 1){
                categoryResult = categoryResult + "," + category[i];
            }
            Log.v("categoryResult",categoryResult);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SessionCallback sessionCallback = new SessionCallback(getApplicationContext());
        sessionCallback.unlink();
    }
}