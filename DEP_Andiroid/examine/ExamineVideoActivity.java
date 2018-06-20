package com.example.posin.myapplication.examine;

/**
 * Created by choigwanggyu on 2016. 10. 5..
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.widget.VideoView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.backpress.BackPressCloseHandler;
import com.example.posin.myapplication.main.MainActivity;

import java.util.InputMismatchException;


/**
 * Created by posin on 2015. 12. 22..
 */
public class ExamineVideoActivity extends Activity {
    Intent intent;
    String videopath;
    String tempscore;
    int setindex;
    private BackPressCloseHandler backPressCloseHandler; // 뒤로가기 구현
    private VideoView videoView; //비디오 보여주기 위해 선언
    private videoThread mVideoThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //초기값 지정 , xml연결
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.examine_video_activity);

        intent = getIntent();
        videopath = intent.getStringExtra("path");
        setindex = Integer.parseInt(intent.getStringExtra("index"));
        tempscore = intent.getStringExtra("score");

        backPressCloseHandler = new BackPressCloseHandler(this);
        setLayout();
        mVideoThread = new videoThread();
        mVideoThread.start();
    }

    public void setLayout() {
        videoView = (VideoViewActivity) findViewById(R.id.test_video1);
    }

    @Override
    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(ExamineVideoActivity.this).setTitle("종료확인")
                            // .setIcon(R.drawable.warning)
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(ExamineVideoActivity.this, MainActivity.class);
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


            String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DepressionVideo/"+videopath+".mp4";
            videoView.setVideoURI(Uri.parse(PATH));


            videoView.requestFocus();
            videoView.start();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    System.gc();
                    intent = new Intent(ExamineVideoActivity.this, ExamineActivity.class);
                    intent.putExtra("index", String.valueOf(++setindex));
                    intent.putExtra("score", tempscore);
                    startActivity(intent);
                    System.gc();
                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                    finish();
                }
            });
        }
    }

}
