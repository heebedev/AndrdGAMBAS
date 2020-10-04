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

public class NetworkTask_Subscribe_Subslist_Select extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;

    //내부적으로필요
    ArrayList<Bean_Subscribe> subslist;


    // Constructor 생성
    public NetworkTask_Subscribe_Subslist_Select(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;

        this.subslist = new ArrayList<Bean_Subscribe>();
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
        return subslist;  // 어레이리스트 넘겨주기 !!
    }


    //////////JSON에 맞게 가져올수있게 쓰기/////////////////
    protected void Parser(String str) {
//        Log.v( "Current", "Parser()" );
        try {
            Log.v( "Parser", str );
            //JSONObject jsonObject = new JSONObject(str);
            //JSONArray jsonArray = new JSONArray(jsonObject.getString("scheduledetailscomments_info"));// 안드로이드껄로 쓸때 JSONArray 로 불러오고
            JSONArray jsonArray = new JSONArray(str);// JSONArray 바로 불러와보기 iOS jsp 와 동일하게 사용 가능
            subslist.clear();

            //Log.v("몇개 가져옴?", String.valueOf(jsonArray.length()));
            for (int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject sublist_Object = (JSONObject) jsonArray.get(i);
                String subsSeqno = sublist_Object.getString("subsSeqno");
                //Log.v("구독리스트몇번찍어??", subsSeqno);
                String subsRegistDate = sublist_Object.getString("subsRegistDate");
                String subsValidation = sublist_Object.getString("subsValidation");
                String uSeqno = sublist_Object.getString("uSeqno");
                String prdSeqno = sublist_Object.getString( "prdSeqno" );
                String term = sublist_Object.getString("term");
                String releaseDay = sublist_Object.getString("releaseDay");
                String prdTitle = sublist_Object.getString( "prdTitle" );
                String prdPrice = sublist_Object.getString( "prdPrice" );
                String prdImage = sublist_Object.getString( "prdImage" );
                String prdRegistDate = sublist_Object.getString( "prdRegistDate" );
                String cgSeqno = sublist_Object.getString( "cgSeqno" );
                String chSeqno = sublist_Object.getString( "chSeqno" );
                String chContext = sublist_Object.getString( "chContext" );
                String chNickname = sublist_Object.getString( "chNickname" );
                String chImage = sublist_Object.getString( "chImage" );
                String chValidation = sublist_Object.getString( "chValidation" );
                String createrUSeqno = sublist_Object.getString( "createrUSeqno" );
                String cgName = sublist_Object.getString( "cgName" );


                subslist.add(new Bean_Subscribe(subsSeqno, subsRegistDate, subsValidation, uSeqno,
                        prdSeqno, term, releaseDay, prdTitle, prdPrice, prdImage, prdRegistDate,
                        cgSeqno, chSeqno, chContext, chNickname, chImage, chValidation, createrUSeqno, cgName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}//------
