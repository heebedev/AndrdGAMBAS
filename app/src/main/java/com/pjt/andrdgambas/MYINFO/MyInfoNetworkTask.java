package com.pjt.andrdgambas.MYINFO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pjt.andrdgambas.HomeData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyInfoNetworkTask extends AsyncTask<Integer, String, Object> {

    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    String name, email, phone, interest, password;
    ArrayList myinfo = new ArrayList();

    public MyInfoNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get ....");
        progressDialog.show();

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        int i = 0;
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);


                while (true) {
                    i++;
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                //json 파싱
                Parser(stringBuffer.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return myinfo;
    }


    private void Parser(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);
            name = jsonObject.getString("name");
            email = jsonObject.getString("email");
            phone = jsonObject.getString("phone");
            interest = jsonObject.getString("interest");
            password = jsonObject.getString("password");

            Log.v("MyInfoTask", name);
            Log.v("MyInfoTask", email);
            Log.v("MyInfoTask", phone);
            Log.v("MyInfoTask", interest);

            myinfo.add(name);
            myinfo.add(email);
            myinfo.add(phone);
            myinfo.add(interest);
            myinfo.add(password);
            Log.v("MyInfoTask", String.valueOf(myinfo));

        } catch (Exception e) {

        }
    }


}
