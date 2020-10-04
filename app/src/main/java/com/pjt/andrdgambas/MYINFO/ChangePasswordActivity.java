package com.pjt.andrdgambas.MYINFO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pjt.andrdgambas.HomeData;
import com.pjt.andrdgambas.R;

import org.w3c.dom.Text;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText tv_pwd, tv_new_pwd, tv_new_pwd_check;
    Button btn_change_pwd;
    Intent intent;
    String password, urlAddr;
    String centIP = HomeData.CENIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        intent = getIntent();
        password = intent.getStringExtra("password");

        tv_pwd = findViewById(R.id.tv_pwd);
        tv_new_pwd = findViewById(R.id.tv_new_pwd);
        tv_new_pwd_check = findViewById(R.id.tv_new_pwd_check);

        btn_change_pwd = findViewById(R.id.btn_change_password);
        btn_change_pwd.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_change_password:
                    emptyCheck();
                    break;

            }
        }
    };

    private void emptyCheck() {
        if(tv_pwd.length() == 0) {
            Toast.makeText(ChangePasswordActivity.this,"기존 비밀번호를 입력해주세요..",Toast.LENGTH_SHORT).show();
        } else if(tv_new_pwd.length() == 0) {
            Toast.makeText(ChangePasswordActivity.this,"신규 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
        } else if(tv_new_pwd_check.length() == 0) {
            Toast.makeText(ChangePasswordActivity.this,"신규 비밀번호 확인 입력해주세요.",Toast.LENGTH_SHORT).show();
        } else if(!tv_pwd.getText().toString().equals(password)){
            Toast.makeText(ChangePasswordActivity.this,"기존 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
        } else if(!tv_new_pwd.getText().toString().equals(tv_new_pwd_check.getText().toString())) {
            Toast.makeText(ChangePasswordActivity.this,"신규 비밀번호와 신규 비밀번호 확인이 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
        } else {
            connectInsertData();
        }


    }

    private void connectInsertData(){
        urlAddr = "http://" + centIP + ":8080/gambas/SKHmyInfoPwChange.jsp";
        urlAddr += "?uSeqno=" + HomeData.USERID + "&uPassword=" + tv_new_pwd.getText().toString();
        Log.v("TAG", urlAddr);
        try {
            UserUpdateTask task = new UserUpdateTask(ChangePasswordActivity.this, urlAddr);
            task.execute().get();
            Toast.makeText(ChangePasswordActivity.this,"비밀번호가 성공적으로 변경되었습니다.",Toast.LENGTH_SHORT).show();
//            intent = new Intent(ChangePasswordActivity.this, Fragment_fourth.class);
//            startActivity(intent);
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}