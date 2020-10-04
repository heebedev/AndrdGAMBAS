package com.pjt.andrdgambas.LOGIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseUploadActivity";

    String urlAddr;
    String email, pw, pwChk, name, birth, phone, img;
    String centIp = HomeData.CENIP;
    String categoryResult = "";
    String filename = "";
    CheckBox chk1,chk2, chk3, chk4;

    TextView tv_file;
    EditText edit_email, edit_pw, edit_pwChk, edit_name, edit_birth, edit_phone;
    Button btn_chkEmail, btn_signUp, btn_img;
    ImageView imageView;
    Intent intent;
    private Uri filePath;

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
        tv_file = findViewById(R.id.tv_select_file);
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
                    img = tv_file.getText().toString().trim();
                    categoryChk();
                    blankChk();
                    //업로드
                    uploadFile();
                case R.id.btn_chkEmail:
                    email = edit_email.getText().toString().trim();
                    urlAddrDivider("idDoubleChk", email, pw, name, birth, phone, img, categoryResult); // 아이디 중복 체크.
                    break;
                case R.id.signUp_btn_img:
                    //이미지를 선택
                    new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("파일 업로드")
                            .setCancelable(false)
                            // 하나씩 클릭해야하니깐 OnClickListener 가 여기에 들어옵니다.
                            .setItems(R.array.uploadtype, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String[] file = getResources().getStringArray(R.array.uploadtype);
                                            uploadFileType(file[i]);
                                        }
                                    }
                            )
                            .setNegativeButton("취소", null)
                            .show();
                    break;
            }
        }
    };


    private void uploadFileType(String type) {
        Intent intent = new Intent();

        switch (type) {
            case "이미지":
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
            case "동영상":
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
            case "음악":
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
            case "파일":
                intent.setType("application/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
        }

        startActivityForResult(Intent.createChooser(intent, "업로드할 파일을 선택하세요."), 0);

    }
    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            // 파일 타입
            Log.v(TAG, getContentResolver().getType(filePath));
            // 파일 주소
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                tv_file.setText(getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName() {
        // 사진 이름의 형식 현재 시간 + 파일 이름
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmss");
        Date now = new Date();

        // Uri에서 파일 이름 가져오기
        String result = null;
        if (filePath.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(filePath, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    filename = formatter.format(now) + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (filename == null) {
            filename = formatter.format(now) + filePath.getLastPathSegment();
        }
        Log.v(TAG, filename);
        return filename;
    }


    //upload the file
    private void uploadFile() {

        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            //storage 저장소 오픈
            StorageReference storageRef = storage.getReference();
            // 저장 루트 지정
            StorageReference folderRef = storageRef.child("uImage");
            StorageReference imageRef = folderRef.child(filename);

            // upload
            imageRef.putFile(filePath)
                    // 성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Log.v(TAG, "업로드 완료");
                        }
                    })
                    // 실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.v(TAG, "업로드 실패");
                        }
                    })
                    // 진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            // dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
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
                Log.v("이메일체크",Integer.toString(cntId));
                Toast.makeText(SignUpActivity.this, "사용할 수 없는 이메일입니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SignUpActivity.this, "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
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
                urlAddr = "http://" + centIp + ":8080/gambas/Userinfo_Insert_ios.jsp?";
                urlAddr = urlAddr + "Email=" + email + "&Password=" + pw + "&Name=" + name + "&Birth=" + birth + "&Phone=" + phone + "&Image=" + img + "&interestCategory=" + categoryResult;
                Log.v("URL",urlAddr);
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
        Boolean [] categoryChked = {chk1.isChecked(),chk2.isChecked(),chk3.isChecked(),chk4.isChecked()};
        Log.v("카테고리체크", Arrays.toString(categoryChked));
        String [] category = {"글","그림","영상","음악"};
        int count = 0;

        for(int i = 0; i < categoryChked.length; i++){
            if(categoryChked[i] == true && count == 0){
                categoryResult = category[i];
                Log.v("카테고리체크",categoryResult);
                count = 1;
            }else if(categoryChked[i] == true && count == 1){
                categoryResult = categoryResult + "," + category[i];
                Log.v("카테고리체크",categoryResult);
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