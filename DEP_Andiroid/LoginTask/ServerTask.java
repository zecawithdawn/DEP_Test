package com.example.posin.myapplication.LoginTask;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choigwanggyu on 2016. 8. 29..
 */
//AsyncTask < 인풋 , 중간값, 결과>
public class ServerTask extends AsyncTask<Void, Void, JSONObject> {


    String urlPath;
    JSONObject json;
    TaskListener taskListener;

    public ServerTask(String urlPath, JSONObject json, TaskListener tl) {
        this.urlPath = urlPath;
        this.json = json;
        taskListener = tl;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            URL url = new URL(urlPath);
            //set headers and method

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            // is output buffer writter
            byte[] output = json.toString().getBytes("UTF-8");
            OutputStream os = urlConnection.getOutputStream();
            os.write(output);
            os.flush();
            os.close();


            //input stream
            String response;

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                is = urlConnection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);
                JSONObject responseJSON = new JSONObject(response);

                //여기서 리턴된 값은 onPostExecute의 인자값으로 이동
                return responseJSON;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    protected void onPostExecute(JSONObject json) {
        taskListener.onReceived(json);

    }
}
