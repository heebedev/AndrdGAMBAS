package com.pjt.andrdgambas.LOGIN;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;


public class FindIdPwActivity extends AppCompatActivity {

    private TextView findemid, findpw, result;
    private EditText email, name, birth;
    private String urlAddr;
    private String centIP = HomeData.CENIP;
    private String findData;
    private Button btn_back;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findidpw);
        findemid = findViewById(R.id.tvbt_findemid);
        findpw = findViewById(R.id.tvbt_findpw);
        result = findViewById(R.id.tv_findidpw_result);
        email = findViewById(R.id.et_findidpw_email);
        name = findViewById(R.id.et_findidpw_name);
        birth = findViewById(R.id.et_findidpw_birth);
        btn_back = findViewById(R.id.bt_findidpw_back);


        findemid.setOnClickListener(onClickListener);
        findpw.setOnClickListener(onClickListener);
        btn_back.setOnClickListener(onClickListener);

    }


    TextView.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvbt_findemid:
                    if (name.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (birth.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddr = "http://" + centIP + ":8080/gambas/findId_query_ios.jsp?name=" + name.getText().toString().trim() + "&birth=" + birth.getText().toString().trim();
                        connectGetData();

                        if (findData == null) {
                            result.setVisibility(View.VISIBLE);
                            result.setText("회원정보가 존재하지 않습니다.");
                        } else {
                            result.setVisibility(View.VISIBLE);
                            result.setText("당신의 아이디는 " + findData + " 입니다.");
                        }
                    }
                    break;
                case R.id.tvbt_findpw:
                    if (name.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (birth.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().toString().trim().length() == 0) {
                        Toast.makeText(FindIdPwActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        urlAddr = "http://" + centIP + ":8080/gambas/findPw_query_ios.jsp?id=" + email.getText().toString().trim() + "&name=" + name.getText().toString().trim() + "&birth=" + birth.getText().toString().trim();
                        connectGetData();
                        if (findData != null) {
                            final LinearLayout linear = (LinearLayout) View.inflate(FindIdPwActivity.this, R.layout.newpass, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(FindIdPwActivity.this);
                            builder.setTitle("비밀번호 변경")
                                    .setView(linear)
                                    .setPositiveButton("확인", null)
                                    .setNegativeButton("취소", null);


                            AlertDialog dialog = builder.create();
                            dialog.show();

                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EditText newPw = linear.findViewById(R.id.newpw_pw);
                                    EditText newPwcheck = linear.findViewById(R.id.newpw_pwcheck);
                                    TextView newPwCmt = linear.findViewById(R.id.newpw_comment);

                                    String newPwStr = newPw.getText().toString().trim();
                                    String newPwCkStr = newPwcheck.getText().toString().trim();

                                    if (newPwStr.equals(newPwCkStr) && newPwStr.length() >= 6) {
                                        urlAddr = "http://" + centIP + ":8080/gambas/changePw_query_ios.jsp?uSeqno=" + findData + "&uPassword=" + newPw.getText().toString().trim();
                                        Log.v("URL",urlAddr);
                                        connectSetData();
                                        Toast.makeText(FindIdPwActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show();
                                        intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else if (newPwStr.length() < 6) {
                                        newPwCmt.setText("비밀번호는 6자리 이상입니다.");
                                        newPwCmt.setVisibility(View.VISIBLE);
                                    } else {
                                        newPwCmt.setText("비밀번호를 다시 확인해주세요.");
                                        newPwCmt.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });


                        } else {
                            result.setVisibility(View.VISIBLE);
                            result.setText("회원정보가 존재하지 않습니다.");
                        }

                    }
                    break;
                case R.id.bt_findidpw_back:
                    intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    private void connectGetData() {
        try {
            Log.v("findData","1111");
            NetworkTask_GetInfo networkTask = new NetworkTask_GetInfo(FindIdPwActivity.this, urlAddr);
            findData = networkTask.execute().get().toString();
            Log.v("findData",findData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectGetData

    private void connectSetData() {
        try {
            NetworkTask_CRUD networkTask = new NetworkTask_CRUD(FindIdPwActivity.this, urlAddr);
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }  // connectSetData


}