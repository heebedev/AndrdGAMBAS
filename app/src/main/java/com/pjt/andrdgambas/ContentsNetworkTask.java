package com.pjt.andrdgambas;

import android.app.ProgressDialog;
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


public class ContentsNetworkTask extends AsyncTask<Integer,String,Object> {
    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    ArrayList<HomeData> list;


    public ContentsNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.list = new ArrayList<HomeData>();
    }

    // ProgressDialog 설정 --------------------------------------------
    // 데이터를 받고있을때
    @Override
    protected void onPreExecute() {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setTitle("Dialogue()");
//        progressDialog.setMessage("down.....");
//        progressDialog.show();
    }

    // 데이터가 바뀌엇을때
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

    }

    // 데이터가 완료되었을때
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
    // ProgressDialog 설정끝 --------------------------------------------

    @Override
    protected Object doInBackground(Integer... integers) {
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
                    if (strline == null)
                        break;
                    stringBuffer.append(strline + "\n");
                }

                parser(stringBuffer.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) { bufferedReader.close(); }
                if (inputStreamReader != null) { inputStreamReader.close(); }
                if (inputStream != null) { inputStream.close(); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    protected void parser(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("contents"));
            list.clear();

            for (int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String prdSeq = jsonObject1.getString("prdSeqno");
                String title = jsonObject1.getString("prdTitle");
                String nickname = jsonObject1.getString("chNickname");
                String day = jsonObject1.getString("releaseDay");
                String term = jsonObject1.getString("term");
                String image = jsonObject1.getString("prdImage");
                String price = jsonObject1.getString("prdPrice");
                String like = jsonObject1.getString("clike");
                String subs = jsonObject1.getString("prdcount");
                Log.v("Contents",prdSeq + title + nickname + day + term + image + price + subs);

                list.add(new HomeData(prdSeq, title, nickname, day, term, image, price, like, subs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

