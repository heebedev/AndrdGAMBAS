package com.pjt.andrdgambas.SUBSCRIBE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_Subscribe_ContentsList_Select extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    //내부적으로필요
    ArrayList<Bean_Subscribe> contentslist;


    // Constructor 생성
    public NetworkTask_Subscribe_ContentsList_Select(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;

        this.contentslist = new ArrayList<Bean_Subscribe>();
    }

    /////////////// Start ProgressDialog ///////////////
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate( values );
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
    /////////////// End ProgressDialog /////////////////

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL( mAddr );
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout( 5000 );

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                Log.v( "Current", "HTTP_OK()" );
                inputStream = httpURLConnection.getInputStream(); // 한줄씩 가져오는걸
                inputStreamReader = new InputStreamReader( inputStream ); // inputStreamReader 가 모아서 포장하고
                bufferedReader = new BufferedReader( inputStreamReader ); // 버퍼드리더가 메모에 넣음

                while(true) {
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append( strline + "\n" );
                }

                //끝나면 JSON분해하기 파싱 메소드
                Parser(stringBuffer.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return contentslist;  // 어레이리스트 넘겨주기 !!
    }


    //////////JSON에 맞게 가져올수있게 쓰기/////////////////
    protected void Parser(String str) {
//        Log.v( "Current", "Parser()" );
        try {
            Log.v( "Parser", str );
            //JSONObject jsonObject = new JSONObject(str);
            //JSONArray jsonArray = new JSONArray(jsonObject.getString("scheduledetailscomments_info"));// 안드로이드껄로 쓸때 JSONArray 로 불러오고
            JSONArray jsonArray = new JSONArray(str);// JSONArray 바로 불러와보기 iOS jsp 와 동일하게 사용 가능
            contentslist.clear();

            //Log.v("몇개 가져옴?", String.valueOf(jsonArray.length()));
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject sublist_Object = (JSONObject) jsonArray.get(i);
                String ctSeqno = sublist_Object.getString("ctSeqno");
                String ctTitle = sublist_Object.getString("ctTitle");
                String ctContext = sublist_Object.getString("ctContext");
                String ctfile = sublist_Object.getString("ctfile");
                String ctRegistDate = sublist_Object.getString( "ctRegistDate" );
                String ctValidation = sublist_Object.getString("ctValidation");
                String prdSeqno = sublist_Object.getString("prdSeqno");
                String ctReleaseDate = sublist_Object.getString( "ctReleaseDate" );


                contentslist.add(new Bean_Subscribe(ctSeqno, ctTitle, ctContext, ctfile,
                        ctRegistDate, ctValidation, prdSeqno, ctReleaseDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}//------
