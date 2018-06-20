package com.example.posin.myapplication.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.posin.myapplication.LoginTask.ServerTask;
import com.example.posin.myapplication.LoginTask.TaskListener;
import com.example.posin.myapplication.R;
import com.example.posin.myapplication.imageloder.Constants;
import com.example.posin.myapplication.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.InputMismatchException;

/**
 * Created by choigwanggyu on 2016. 8. 22..
 */
public class AdminLoginActivity extends Activity implements TaskListener {
    private static final String[] ServerAddress = Constants.ServerAddress;
    private static final String[] PhP = Constants.PhP;
    Intent intent;
    Button aloginBtn;
    EditText aidtext;
    EditText apwtext;

    protected void onCreate(Bundle savedInstanceState) {
        //초기값 지정 , xml연결
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_login_activity);

        aloginBtn = (Button) findViewById(R.id.Alogin_btn);
        aidtext = (EditText) findViewById(R.id.Alogin_id);
        apwtext = (EditText) findViewById(R.id.Alogin_password);

        aloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    beforeLoginProcess();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void beforeLoginProcess() throws JSONException {
        if (check_internetstate() && !aidtext.getText().toString().equals("") && !apwtext.getText().toString().equals("")) {
            JSONObject requestjson = new JSONObject();
            requestjson.put("ID", aidtext.getText().toString());
            requestjson.put("password", apwtext.getText().toString());

            ServerTask servertask = new ServerTask("http://"+ServerAddress[0]+"/"+PhP[2], requestjson, this);
            servertask.execute();

        }
    }

    private boolean check_internetstate() {
        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()) {
            return true;
        } else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_LONG).show();
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
                    dialog = new AlertDialog.Builder(AdminLoginActivity.this).setTitle("종료확인")
                            // .setIcon(R.drawable.warning)
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
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
    public void onReceived(JSONObject jsonObject) {
        String jres = null;
        try {
            jres = jsonObject.getString("sever_responce");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jres.equals("1")) {
            intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();

        } else if (jres.equals("2")) {
            intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else if (jres.equals("3")) {
            Toast.makeText(this, "관리자가 아닙니다.", Toast.LENGTH_LONG).show();
            intent = new Intent(AdminLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (jres.equals("4")) {
            Toast.makeText(this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "서버에러 입니다.", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onCanceled() {

    }
}
