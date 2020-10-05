package com.pjt.andrdgambas.MYCHANNEL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pjt.andrdgambas.HOME.HomeData;
import com.pjt.andrdgambas.LOGIN.SJ_InsNetworkTask;
import com.pjt.andrdgambas.LOGIN.SignUpActivity;
import com.pjt.andrdgambas.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class addProductActivity extends AppCompatActivity {
    Intent intent;
    EditText ed_title, ed_price, ed_context;
    ImageView imageView;
    CheckBox writing, drawing, video, music, etc, sun, mon, tues, wed, thurs, fri, sat;
    RadioButton daily, weekly;
    Button insert, insert_img;
    TextView select_img;
    String title, price, context, term, dayResult, image, filename;
    int categoryResult = 0;
    String urlAddr;
    String centIP = HomeData.CENIP;
    String chSeqno;
    private Uri filePath;
    private static final String TAG = "FirebaseUploadActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Log.v("addProductActivity","addProductActivity");
        ed_title = findViewById(R.id.ed_prdAdd_title);
        ed_price = findViewById(R.id.ed_prdAdd_price);
        ed_context = findViewById(R.id.ed_prdAdd_context);
        writing = findViewById(R.id.chk_prdAdd_writing);
        drawing = findViewById(R.id.chk_prdAdd_drawing);
        video = findViewById(R.id.chk_prdAdd_video);
        music = findViewById(R.id.chk_prdAdd_music);
        etc = findViewById(R.id.chk_prdAdd_etc);
        mon = findViewById(R.id.chk_prdAdd_mon);
        tues = findViewById(R.id.chk_prdAdd_tues);
        wed = findViewById(R.id.chk_prdAdd_wed);
        thurs = findViewById(R.id.chk_prdAdd_thurs);
        fri = findViewById(R.id.chk_prdAdd_fri);
        sat = findViewById(R.id.chk_prdAdd_sat);
        sun = findViewById(R.id.chk_prdAdd_sun);
        daily = findViewById(R.id.rb_prdAdd_daily);
        weekly = findViewById(R.id.rb_prdAdd_weekly);
        imageView = findViewById(R.id.iv_prdAdd_img);
        insert = findViewById(R.id.btn_prdAdd_insert);
        insert_img = findViewById(R.id.btn_prdAdd_img);
        select_img = findViewById(R.id.tv_prdAdd_select_file);

        Intent intent = getIntent();
        chSeqno = intent.getExtras().getString("chSeqno");


        insert.setOnClickListener(onClickListener);
        insert_img.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_prdAdd_img:
                    //이미지를 선택
                    new androidx.appcompat.app.AlertDialog.Builder(addProductActivity.this)
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
                case R.id.btn_prdAdd_insert:
                    title = ed_title.getText().toString().trim();
                    price = ed_price.getText().toString().trim();
                    context = ed_context.getText().toString().trim().replaceAll("\n","");
                    image = select_img.getText().toString().trim();
                    categoryChk();
                    blankChk();

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
    private void categoryChk() {
        Boolean[] dayChked = {sun.isChecked(), mon.isChecked(), tues.isChecked(), wed.isChecked(), thurs.isChecked(), fri.isChecked(), sat.isChecked()};
        int [] category = {1,2,3,4,5};
        String[] days = {"일", "월", "화", "수", "목", "금", "토"};
        if(writing.isChecked()){
            categoryResult = 1;
        }if(drawing.isChecked()){
            categoryResult = 2;
        }if(video.isChecked()){
            categoryResult = 3;
        }if(music.isChecked()){
            categoryResult = 4;
        }if(etc.isChecked()){
            categoryResult = 5;
        }

        if (daily.isChecked()) {
            term = "매일";
        } else if (weekly.isChecked()) {
            term = "매주";
        }
        int count = 0;
        for (int i = 0; i < dayChked.length; i++) {
            if (dayChked[i] == true && count == 0) {
                dayResult = days[i];
                count = 1;
            } else if (dayChked[i] == true && count == 1) {
                dayResult = dayResult + "," + category[i];
            }
        }

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
                select_img.setText(getFileName());
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

            //storage 주소와 폴더 파일명을 지정해 준다.
            // gs://gambas-b06ab.appspot.com/gambasFiles
            StorageReference storageRef = storage.getReference();
            // 승희가 보내준부분
            StorageReference mountainsRef = storageRef.child(filename);
            StorageReference mountainImagesRef = storageRef.child("prdImage/" + filename);
            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
            // 수빈이부분
            // StorageReference storageRef = storage.getReferenceFromUrl("gs://gambas-174df.appspot.com").child("uImage/" + filename);
            Log.v(TAG, String.valueOf(storageRef));
            // upload
            storageRef.putFile(filePath)
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
        if (title.length() == 0 || price.length() == 0 || context.length() == 0 || categoryResult == 0 || dayResult.length() == 0) {
            // --------------- 대화상자 띄우기 ---------------------------------------------------------
            new AlertDialog.Builder(addProductActivity.this)
                    .setTitle("빈칸을 입력해주세요.")
                    .setCancelable(false)
                    .setNegativeButton("확인", null)
                    .show();

        } else {
            urlAddr = "http://" + centIP + ":8080/gambas/MyProductInsert.jsp?"; // centIP 는 항상 위에
            urlAddr = urlAddr + "productTerm=" + term + "&productDay=" + dayResult + "&productName=" + title + "&productPrice=" + price + "&productContent=" + context + "&productImage=" + image + "&channelSeqno=" + chSeqno + "&productCategory=" + categoryResult;
            Log.v("URL", urlAddr);
            connectionData();
        }
    }

    private void connectionData() {
        try {
            SJ_InsNetworkTask networkTask = new SJ_InsNetworkTask(addProductActivity.this, urlAddr);
            networkTask.execute().get();
            Intent intent = new Intent(addProductActivity.this, Activity_Mychannel.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

