package com.pjt.andrdgambas.PRDDETAIL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask__PrdDetailDetail_chData extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;
    ProgressDialog progressDialog;

    ArrayList<Bean_PrdDetailDetail_ChData> chDataArrayList;

    public NetworkTask__PrdDetailDetail_chData(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;

        this.chDataArrayList = new ArrayList<Bean_PrdDetailDetail_ChData>();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog( context );
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("상품 상세 정보");
        progressDialog.setMessage("로딩 중");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Integer ... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL( mAddr );
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout( 5000 );

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.v( "Current", "HTTP_OK()" );
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader( inputStream );
                bufferedReader = new BufferedReader( inputStreamReader );

                while(true) {
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append( strline + "\n" );
                }

                parse(stringBuffer.toString());
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
        return chDataArrayList;
    }

    protected void parse(String str) {
        try {
            chDataArrayList.clear();
            JSONObject jsonObject = new JSONObject(str);

            String chNickname = jsonObject.getString("chNickname");
            String chImage = jsonObject.getString("chImage");
            String chContext = jsonObject.getString("chContext");
            String average = jsonObject.getString("average");

            chDataArrayList.add(new Bean_PrdDetailDetail_ChData(chNickname,chImage,chContext,average));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
