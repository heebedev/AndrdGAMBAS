package com.pjt.andrdgambas.FIREBASE;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pjt.andrdgambas.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
FireBase를 이용해 제작자 컨텐츠 업로드 기능을 구현 하는 Activity
 */


public class AddContentsActivity extends Activity {

    Button btn;
    private static final String TAG = "MainActivity";

    private Button btChoose;
    private Button btUpload;
    private ImageView ivPreview;
    private TextView tvFile;
    private String filename = "";

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contents);

        btn = findViewById(R.id.btn_test);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(AddContentsActivity.this, TestContentsActivity.class);
                startActivity(intent);
            }
        });



        btChoose = findViewById(R.id.bt_choose);
        btUpload = findViewById(R.id.bt_upload);
        ivPreview = findViewById(R.id.iv_preview);
        tvFile = findViewById(R.id.tv_select_file);

        //버튼 클릭 이벤트
        // image/*, video/*, audio/*, application/*
        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지를 선택
                new AlertDialog.Builder(AddContentsActivity.this)
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

            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //업로드
                uploadFile();
            }
        });

    }


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
                ivPreview.setImageBitmap(bitmap);
                tvFile.setText(getFileName());
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

            // *********
            //storage 주소 변경 해줘야 함
            StorageReference storageRef = storage.getReferenceFromUrl("gs://gambas-174df.appspot.com").child("uImage/" + filename);
            Log.v(TAG, String.valueOf(storageRef));
            //올라가거라...
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
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}