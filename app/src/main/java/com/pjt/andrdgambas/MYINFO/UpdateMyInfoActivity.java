package com.pjt.andrdgambas.MYINFO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;

import java.util.ArrayList;

public class UpdateMyInfoActivity extends AppCompatActivity {
    String urlAddr, urlAddr2;
    String centIP = HomeData.CENIP;
    TextView tv_name, tv_email, tv_phone, tv_interest;
    Button btn_update_pwd, btn_withdraw;
    String setLog = "UpdateMyInfoActivity";
    String password = "";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_info);

        tv_name = findViewById(R.id.tv_user_name);
        tv_email = findViewById(R.id.tv_user_email);
        tv_phone = findViewById(R.id.tv_user_phone);
        tv_interest = findViewById(R.id.tv_user_list);

        btn_update_pwd = findViewById(R.id.btn_update_password);
        btn_withdraw = findViewById(R.id.btn_user_withdraw);

        btn_update_pwd.setOnClickListener(onClickListener);
        btn_withdraw.setOnClickListener(onClickListener);

        // 사용자 정보 불러오는 JSP
        urlAddr = "http://" + centIP + ":8080/gambas/getUserInfo_android.jsp";
        connectGetData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
        Log.v("TAG", password);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_update_password:
                    intent = new Intent(UpdateMyInfoActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    break;
                case R.id.btn_user_withdraw:
                    alert();
                    break;
            }
        }
    };



    // 사용자 정보 불러오는 Connect
    private void connectGetData() {
        urlAddr += "?seq=" + HomeData.USERID;
        Log.v("URL", urlAddr);
        try {
            MyInfoNetworkTask networkTask = new MyInfoNetworkTask(UpdateMyInfoActivity.this, urlAddr);
            ArrayList obj = (ArrayList) networkTask.execute().get();

            String[] myinfo = (String[]) obj.toArray(new String[obj.size()]);

            for (int index = 0; index < obj.size(); index++) {
                Log.v(setLog, index+myinfo[index]);
            }

            tv_name.setText(myinfo[0]);
            tv_email.setText(myinfo[1]);
            tv_phone.setText(myinfo[2]);
            tv_interest.setText(myinfo[3]);
            password = myinfo[4];


        } catch (Exception e) {

        }
    }

    private void alert() {
        final EditText input = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정말 회원탈퇴 하시겠습니까?");
        builder.setMessage("확인을 위한 비밀번호를 입력해주세요.");
        builder.setView(input);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int which) {
                        // 입력한 pwd와 기존의 pwd가 같을 때
                        if (input.getText().toString().equals(password)) {
                            UserWithdrawConnect();
                        } else {
                            Toast.makeText(UpdateMyInfoActivity.this,"비밀번호 다시 입력해주세요.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        builder.setNegativeButton("Cencel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }

    // 회원 탈퇴를 위한 Task Connect
    private void UserWithdrawConnect() {
        urlAddr2 = "http://" + centIP + ":8080/gambas/SKHmyInfoUser_validation.jsp";
        urlAddr2 += "?uSeqno=" + HomeData.USERID;
        Log.v("URL", urlAddr);
        try {
            UserUpdateTask networkTask = new UserUpdateTask(UpdateMyInfoActivity.this, urlAddr2);
            networkTask.execute().get();

            finish();
//            intent = new Intent(UpdateMyInfoActivity.this, LoginActivity.class);
//            startActivity(intent);

        } catch (Exception e) {

        }
    }


}