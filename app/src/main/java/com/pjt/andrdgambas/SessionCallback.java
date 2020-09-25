package com.pjt.andrdgambas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

public class SessionCallback {

    public String uEmail;
    String urlAddr;
    String centIP = "192.168.2.61";
    Intent intent;
    String uSeqno;
    private Context mContext;

    public SessionCallback(Context mContext){
        this.mContext = mContext;
    }
    // 세션 콜백 구현
    public ISessionCallback sessionCallback = new ISessionCallback() {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();
        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }


        // 사용자 정보 요청
        public void requestMe() {
            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }
                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                        }
                        @Override
                        public void onSuccess(MeV2Response result) {
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                            UserAccount kakaoAccount = result.getKakaoAccount();
                            if (kakaoAccount != null) {
                                // 이메일
                                uEmail = kakaoAccount.getEmail();
                                if (uEmail != null) {
                                    Log.i("KAKAO_API", "email: " + uEmail);
                                    if (uEmail == "null" || uEmail == null) {
                                    } else {
                                        urlAddr = "http://" + centIP + ":8080/test/GAMBAS_emailLogin.jsp?"; // centIP 는 항상 위에
                                        urlAddr = urlAddr + "uEmail=" + uEmail;
                                        Log.v("URL", urlAddr);
                                        connectionloginData(urlAddr);
                                    }

                                } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 이메일 획득 가능
                                    // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                } else {
                                    // 이메일 획득 불가
                                }

                                // 프로필
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                    Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                    Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());

                                } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                    // 동의 요청 후 프로필 정보 획득 가능

                                } else {
                                    // 프로필 획득 불가
                                }
                            }
                        }
                    });
        }
    };
    public void connectionloginData(String urlAddr) {
        try {
            LoginNetworkTask loginNetworkTask = new LoginNetworkTask(mContext, urlAddr);
            uSeqno = loginNetworkTask.execute().get().toString();
            Log.v("uSeqno",uSeqno);
            if (uSeqno.equals("null")){ // 디비에 저장되어 있지 않은 회원
                intent = new Intent(mContext, SignUpActivity.class);
                intent.putExtra("uSeqno", uSeqno);
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }else { // uSeqno 값이 있으면 기존에 회원가입 했던 회원
                intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("uSeqno", uSeqno);
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void unlink(){
        UserManagement.getInstance()
                .requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "연결 끊기 실패: " + errorResult);

                    }
                    @Override
                    public void onSuccess(Long result) {
                        Log.i("KAKAO_API", "연결 끊기 성공. id: " + result);
                        //mContext.finish();
                    }
                });
    }

}
