package com.example.posin.myapplication.emdr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.VideoView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.backpress.BackPressCloseHandler;
import com.example.posin.myapplication.examine.VideoViewActivity;
import com.example.posin.myapplication.main.MainActivity;

import java.util.InputMismatchException;

/**
 * Created by choigwanggyu on 2016. 8. 15..
 */
public class EmdrMainActivity extends Activity {

    Intent intent;

    private BackPressCloseHandler backPressCloseHandler; // 뒤로가기 구현
    private VideoView videoView; //비디오 보여주기 위해 선언
    private videoThread mVideoThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //초기값 지정 , xml연결
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.emdr_activity);
        backPressCloseHandler = new BackPressCloseHandler(this);
        setLayout();
        mVideoThread = new videoThread();
        mVideoThread.start();

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //여기도 다이얼로그

                AlertDialog dialog;
                   dialog = new AlertDialog.Builder(EmdrMainActivity.this).setTitle("종료확인")
                            .setMessage("안구운동을 종료하고 메뉴 선택 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(EmdrMainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    System.gc();
                                    finish();
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            })
                            .show();
                return false;
            }
        });
    }

    protected void onPause() {
        videoView.stopPlayback();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setLayout() {
        videoView = (VideoViewActivity) findViewById(R.id.emdr_video);
    }

    @Override
    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(EmdrMainActivity.this).setTitle("종료확인")
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(EmdrMainActivity.this, MainActivity.class);
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

    class videoThread extends Thread implements Runnable {
        //비디오 지정 , 영상후 애니메이션 , 이동 페이지 설정
        @Override
        public void run() {
            Uri video = Uri.parse("android.resource://" + getPackageName()
                    + "/" + R.raw.emdrvideo);

            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //다이얼로그
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(EmdrMainActivity.this).setTitle("종료확인")
                            .setMessage("안구운동을 종료되었습니다 메뉴 선택 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(EmdrMainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    System.gc();
                                    finish();
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                    videoView.start();
                                }
                            })
                            .show();

                }
            });

        }
    }
}
