package com.pjt.andrdgambas;

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

public class SJ_IntNetworkTask extends AsyncTask<Integer, String, Integer> {
    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    int intChk;

    public SJ_IntNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.intChk = intChk;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Please wait a moment..");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                Parser(stringBuffer.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return intChk; // 패스워드 리턴시키고 엑티비티에서 받음
    }
    private void Parser(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            intChk = jsonObject.getInt("ckEmail"); //
            Log.v("intChk",Integer.toString(intChk));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
