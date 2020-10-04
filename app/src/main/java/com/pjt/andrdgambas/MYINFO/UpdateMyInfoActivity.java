package com.pjt.andrdgambas.MYINFO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.LOGIN.LoginActivity;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.util.ArrayList;

public class UpdateMyInfoActivity extends AppCompatActivity {
    Intent intent;
    String urlAddr, urlAddr2, urlUpdate;
    String centIP = STATICDATA.CENTIP;
    CheckBox cb_category1, cb_category2, cb_category3, cb_category4, cb_category5;
    EditText et_name, et_email, et_phone, et_interest;
    Button btn_update_pwd, btn_withdraw, btn_update;
    String setLog = "UpdateMyInfoActivity";
    String password = "", category = "", newCategory = "";
    String[] categoryArray;
    ArrayList<String> newCategoryArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_info);

        cb_category1 = findViewById(R.id.cb_category_1);
        cb_category2 = findViewById(R.id.cb_category_2);
        cb_category3 = findViewById(R.id.cb_category_3);
        cb_category4 = findViewById(R.id.cb_category_4);
        cb_category5 = findViewById(R.id.cb_category_5);

        et_name = findViewById(R.id.et_user_name);
        et_email = findViewById(R.id.et_user_email);
        et_phone = findViewById(R.id.et_user_phone);

        btn_update_pwd = findViewById(R.id.btn_update_password);
        btn_withdraw = findViewById(R.id.btn_user_withdraw);
        btn_update = findViewById(R.id.btn_myinfo_update);

        btn_update_pwd.setOnClickListener(onClickListener);
        btn_withdraw.setOnClickListener(onClickListener);
        btn_update.setOnClickListener(onClickListener);

        // 사용자 정보 불러오는 JSP
        urlAddr = "http://" + centIP + ":8080/gambas/getUserInfo_android.jsp";
        connectGetData();

        myInfoCategory();

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
        Log.v("TAG", password);
    }

    // ChangePasswordActivity를 finish 했을 때
    // 현재의 Acticity도 닫히게 하기 위해
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_update_password:
                    intent = new Intent(UpdateMyInfoActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("password", password);
                    startActivityForResult(intent, 0);
                    break;
                case R.id.btn_user_withdraw:
                    alert();
                    break;
                case R.id.btn_myinfo_update:
                    myinfoUpdate();
                    newCategory = "";
                    newCategoryArray.clear();
                    break;
            }
        }
    };

    private void alert() {
        final EditText input = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원탈퇴 하시겠습니까?");
        builder.setMessage("확인을 위한 비밀번호를 입력해주세요.");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 입력한 pwd와 기존의 pwd가 같을 때
                        if (input.getText().toString().equals(password)) {
                            UserWithdrawConnect();
                        } else {
                            Toast.makeText(UpdateMyInfoActivity.this, "비밀번호 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }

    // 사용자 정보 불러오는 Connect
    private void connectGetData() {
        urlAddr += "?seq=" + HomeData.USERID;
        Log.v("URL", urlAddr);
        try {
            MyInfoNetworkTask networkTask = new MyInfoNetworkTask(UpdateMyInfoActivity.this, urlAddr);
            ArrayList obj = (ArrayList) networkTask.execute().get();

            String[] myinfo = (String[]) obj.toArray(new String[obj.size()]);

            for (int index = 0; index < obj.size(); index++) {
                Log.v(setLog, index + myinfo[index]);
            }

            et_name.setText(myinfo[0]);
            et_email.setText(myinfo[1]);
            et_phone.setText(myinfo[2]);

            category = myinfo[3];
            password = myinfo[4];


        } catch (Exception e) {

        }
    }

    // 회원 탈퇴를 위한 Task Connect
    private void UserWithdrawConnect() {
        urlAddr2 = "http://" + centIP + ":8080/gambas/SKHmyInfoUser_validation.jsp";
        urlAddr2 += "?uSeqno=" + HomeData.USERID;
        Log.v("URL", urlAddr);
        try {
            UserUpdateTask networkTask = new UserUpdateTask(UpdateMyInfoActivity.this, urlAddr2);
            networkTask.execute().get();

            intent = new Intent(UpdateMyInfoActivity.this, LoginActivity.class);
            startActivity(intent);

        } catch (Exception e) {

        }
    }

    private void myInfoCategory() { // 관심 항목의 체크 박스 초기 설정
        categoryArray = category.split(",");

        for (int i = 0; i < categoryArray.length; i++) {
            switch (categoryArray[i]) {
                case "글":
                    cb_category1.setChecked(true);
                    break;
                case "그림":
                    cb_category2.setChecked(true);
                    break;
                case "영상":
                    cb_category3.setChecked(true);
                    break;
                case "음악":
                    cb_category4.setChecked(true);
                    break;
                case "기타":
                    cb_category5.setChecked(true);
                    break;
            }
        }
    }

    private void myinfoUpdate() {
        if (et_name.length() == 0) {
            Toast.makeText(UpdateMyInfoActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (et_email.length() == 0) {
            Toast.makeText(UpdateMyInfoActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (et_phone.length() == 0) {
            Toast.makeText(UpdateMyInfoActivity.this, "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (checkboxAction() == false) {
            Toast.makeText(UpdateMyInfoActivity.this, "관심 항목을 선택해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            UserUpdateConnect();
        }

    }

    private boolean checkboxAction() { // 수정된 관심 항목의 체크 박스
        if (cb_category1.isChecked()) {  //체크 박스가 체크 된 경우
            newCategoryArray.add("글");
        }
        if (cb_category2.isChecked()) {
            newCategoryArray.add("그림");
        }
        if (cb_category3.isChecked()) {
            newCategoryArray.add("영상");
        }
        if (cb_category4.isChecked()) {
            newCategoryArray.add("음악");
        }
        if (cb_category5.isChecked()) {
            newCategoryArray.add("기타");
        }

        newCategory = TextUtils.join(",", newCategoryArray);
        Log.v("CATEGORY", newCategory);

        if (newCategory.length() == 0) {
            return false;
        }

        return true;

    }

    // 회원 정보 수정을 위한 Task Connect
    private void UserUpdateConnect() {
        urlUpdate = "http://" + centIP + ":8080/gambas/myInfoUpdate_android.jsp";
        urlUpdate += "?uSeqno=" + HomeData.USERID + "&uEmail=" + et_email.getText().toString() + "&uPhone=" + et_phone.getText().toString()
                + "&interestCategory=" + newCategory + "&uName=" + et_name.getText().toString();

        Log.v("URL", urlUpdate);
        try {
            UserUpdateTask networkTask = new UserUpdateTask(UpdateMyInfoActivity.this, urlUpdate);
            networkTask.execute().get();
            Toast.makeText(UpdateMyInfoActivity.this, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {

        }
    }


}