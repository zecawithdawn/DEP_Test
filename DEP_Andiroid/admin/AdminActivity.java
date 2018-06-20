package com.example.posin.myapplication.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.main.MainActivity;

import java.util.InputMismatchException;

/**
 * Created by choigwanggyu on 2016. 8. 22..
 */

//1.webview로 인터넷 연결 웹페이지 작업 우선
public class AdminActivity extends Activity {
    Intent intent;
    ImageView result;
    ImageView setting;

    protected void onCreate(Bundle savedInstanceState) {
        //초기값 지정 , xml연결
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_activity);

        result = (ImageView) findViewById(R.id.admin_result_buuton);
        setting = (ImageView) findViewById(R.id.admin_setting_button);

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, AdminResultActivity.class);
                System.gc();
                overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, AdminSettingActivity.class);
                startActivity(intent);
                System.gc();
                overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(AdminActivity.this).setTitle("종료확인")
                            // .setIcon(R.drawable.warning)
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(AdminActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    System.gc();
                                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
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
            e.printStackTrace();
        }
    }
}
