package com.pjt.andrdgambas.MYCHANNEL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MyChannelNetworkTask extends AsyncTask<Integer,String,Object> {
    Context context;
    String mAddr;
    ProgressDialog progressDialog;
    ArrayList<MyChannel> data;
    String codeType;

    public MyChannelNetworkTask(Context context, String mAddr, String codeType) {
        this.context = context;
        this.mAddr = mAddr;
        this.data = new ArrayList<MyChannel>();
        this.codeType = codeType;
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
        return data;
    }

    protected void parser(String str) {
        try {
            if(codeType.equals("getChannel")) {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("product_list"));
                data.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                    String seq = jsonObject1.getString("channelSeqno");
                    String content = jsonObject1.getString("channelContent");
                    String nickname = jsonObject1.getString("channelNickname");
                    String image = jsonObject1.getString("channelImage");
                    String date = jsonObject1.getString("channelRegisterDate");
                    String validation = jsonObject1.getString("channelValidation");

                    data.add(new MyChannel(seq, content, nickname, image, date, validation));
                }
            }else if(codeType.equals("getProduct")){
                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("product_list"));
                data.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    // JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                    String seq = jsonObject1.getString("productSeqno");
                    String term = jsonObject1.getString("productTerm");
                    String day = jsonObject1.getString("productReleaseDay");
                    String title = jsonObject1.getString("productTitle");
                    String price = jsonObject1.getString("productPrice");
                    String content = jsonObject1.getString("productContent");
                    String image = jsonObject1.getString("productImage");
                    String date = jsonObject1.getString("productRegisterDate");
                    String validation = jsonObject1.getString("productValidation");
                    String cateSeq = jsonObject1.getString("categorySeqno");

                    data.add(new MyChannel(seq, term, day, title, price, content, image, date, validation, cateSeq));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

