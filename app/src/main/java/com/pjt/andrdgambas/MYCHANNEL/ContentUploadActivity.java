package com.pjt.andrdgambas.MYCHANNEL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pjt.andrdgambas.FIREBASE.AddContentsActivity;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.MYINFO.ChangePasswordActivity;
import com.pjt.andrdgambas.MYINFO.UpdateMyInfoActivity;
import com.pjt.andrdgambas.MYINFO.UserUpdateTask;
import com.pjt.andrdgambas.R;
import com.pjt.andrdgambas.STATICDATA;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentUploadActivity extends AppCompatActivity {
    Intent intent;
    EditText et_content_title, et_content_context;
    Button btn_file_upload, btn_content_upload;
    TextView tv_filename;
    private Uri filePath;
    private String filename = "";
    private static final String TAG = "ContentUploadActivity";
    String urlAddr;
    String centIP = STATICDATA.CENTIP;
    String prdSeqno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_upload);

        et_content_title = findViewById(R.id.et_content_title);
        et_content_context = findViewById(R.id.et_content_context);
        btn_file_upload = findViewById(R.id.btn_file_upload);
        btn_content_upload = findViewById(R.id.btn_content_upload);
        tv_filename = findViewById(R.id.tv_content_filename);

        btn_file_upload.setOnClickListener(onClickListener);
        btn_content_upload.setOnClickListener(onClickListener);

        intent = getIntent();
        prdSeqno = intent.getStringExtra("prdSeqno");


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_content_upload:
                    checkEmptyText();
                    break;
                case R.id.btn_file_upload:
                    new AlertDialog.Builder(ContentUploadActivity.this)
                            .setTitle("파일 업로드")
                            .setCancelable(false)
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                tv_filename.setText(getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName() {
        // 현재 시간
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
        return filename;
    }

    private void checkEmptyText() {
        if (et_content_title.length() == 0) {
            Toast.makeText(ContentUploadActivity.this, "콘텐츠 제목을 입력해주세요.", Toast.LENGTH_SHORT).show();

        } else if (et_content_context.length() == 0) {
            Toast.makeText(ContentUploadActivity.this, "콘텐츠 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();

        } else if (filePath == null) {
            Toast.makeText(ContentUploadActivity.this, "파일을 선택하세요.", Toast.LENGTH_SHORT).show();
        } else {
            uploadFile();
            UserUpdateConnect();
        }
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

            StorageReference storageRef = storage.getReferenceFromUrl("gs://gambas-174df.appspot.com").child("contentsFolder/" + filename);
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Log.v(TAG, "업로드 완료");
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.v(TAG, "업로드 실패");
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
    }

    private void UserUpdateConnect() {
        urlAddr = "http://" + centIP + ":8080/gambas/MyContentsInsert.jsp";
        urlAddr += "?contentsTitle=" + et_content_title.getText().toString() + "&contentsContent=" + et_content_context.getText().toString()
                + "&contentsFile=" + filename
                + "&productSeqno=" + prdSeqno;

        Log.v("URL", urlAddr);
        try {
            UserUpdateTask networkTask = new UserUpdateTask(ContentUploadActivity.this, urlAddr);
            networkTask.execute().get();
            Toast.makeText(ContentUploadActivity.this, "콘텐츠가 등록되었습니다.", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {

        }
    }
}