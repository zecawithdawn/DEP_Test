package com.example.posin.myapplication.examine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.posin.myapplication.LoginTask.ServerTask;
import com.example.posin.myapplication.LoginTask.TaskListener;
import com.example.posin.myapplication.R;
import com.example.posin.myapplication.main.MainActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.InputMismatchException;

import static com.example.posin.myapplication.imageloder.Constants.ServerAddress;

/**
 * Created by posin on 2016. 2. 2..
 */
public class ExamineIntroActivity extends Activity implements TaskListener {

    String TestJSON;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private FileDownload mThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.examine_intro_activity);

        compareTestsetName();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void compareTestsetName() {

        JSONObject insertjson = new JSONObject();
        try {
            insertjson.put("download", "video");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerTask servertask = new ServerTask("http://" + ServerAddress[0] + "/" + "Examine_compare.php", insertjson, this);
        servertask.execute();
    }


    private void filedownload() {

        final String FileDownloadPath = "http://" + ServerAddress[0] + "/video/";
        File Dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DepressionVideo");

        try {

            JSONObject jsonResponce = new JSONObject(TestJSON);
            JSONArray set = jsonResponce.getJSONArray("set");
            for (int i = 0; i < set.length(); i++) {
                final JSONObject data = set.getJSONObject(i);
                //파일다운로드 비디오중에 없는 파일만 다운로드

                if (data.getString("type").equals("video")) {
                    if (!Dir.exists()) {
                        Dir.mkdir();
                    }

                    final File file = new File(Dir, data.getString("value") + ".mp4");
                    if (!file.exists()) {
                        Toast.makeText(this, "진단 검사에 필요한 파일을 다운로드 받습니다.", Toast.LENGTH_LONG).show();
                        mThread = new FileDownload(FileDownloadPath, data, file);
                        mThread.start();

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mThread.close();

        } catch (NullPointerException e){
            e.printStackTrace();

        }
    }

    @Override
    public void onReceived(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("server_responce").equals("1")) {
                TestJSON = jsonObject.getString("testset");
                SharedPreferences conf = getSharedPreferences("configure", MODE_PRIVATE);
                SharedPreferences.Editor editor = conf.edit();
                //cleardirectory 여기서 파일 확인후 클리어 디렉토리
                editor.clear();
                editor.putString("queset", jsonObject.getString("testset"));
                editor.apply();
                if (wifi_state()) {
                    filedownload();
                    finish_activity();

                } else {
                    new AlertDialog.Builder(ExamineIntroActivity.this).setTitle("인터넷 연결상태 확인")
                            .setMessage("진단 검사에 필요한 파일을 다운로드 받습니다. WIFI상태가 아닌경우 데이터 과금이 있을 수 있습니다.\n진행하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    filedownload();
                                    finish_activity();

                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    Intent intent = new Intent(ExamineIntroActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    System.gc();
                                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                                    finish();
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            } else {
                Toast.makeText(this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ExamineIntroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void finish_activity() {
        Intent intent = new Intent(ExamineIntroActivity.this, ExamineLoginActivity.class);
        startActivity(intent);
        System.gc();
        overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
        finish();
    }

    @Override
    public void onCanceled() {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ExamineIntro Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    private boolean wifi_state() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(ExamineIntroActivity.this).setTitle("종료확인")
                            // .setIcon(R.drawable.warning)
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    Intent intent = new Intent(ExamineIntroActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    System.gc();
                                    finish();
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            });
        } catch (InputMismatchException e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}