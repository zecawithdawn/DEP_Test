package com.example.posin.myapplication.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.admin.AdminLoginActivity;
import com.example.posin.myapplication.emdr.EmdrMainActivity;
import com.example.posin.myapplication.examine.ExamineIntroActivity;
import com.example.posin.myapplication.imageloder.Constants;
import com.example.posin.myapplication.sound.SoundMainActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Timer;
import java.util.TimerTask;

/*
웹 버젼 애플리케이션 입니다.

 */
public class MainActivity extends Activity {

    //인텐드 이미지뷰 등록
    Intent intent;
    ImageView examineView;
    ImageView soundView;
    ImageView eyeView;
    ImageView adminbutton;

    //화면보호기용 변수
    private TimerTask mTask;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));

        examineView = (ImageView) findViewById(R.id.multiple_actions_video);
        soundView = (ImageView) findViewById(R.id.multiple_actions_sound);
        eyeView = (ImageView) findViewById(R.id.multiple_actions_eye);
        adminbutton = (ImageView) findViewById(R.id.admin_button);

        fabSoundAdd(); // 사운드
        fabexamineAdd(); // 비디오
        fabEmdrAdd();  //안구운동
        fabAdminADD(); //관리자 화면
        screensaver(); //화면 보호기

    }

    //다른 화면으로 이동시 타이머 종료
    @Override
    protected void onPause() {
        try {
            mTimer.cancel();
            mTimer = null;

        } catch (Exception e) {
            Log.e("error", e.toString());
            finish();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void screensaver() {

        mTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this
                        , ScreenSaver.class);
                startActivity(intent);
                finish();
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 30000);
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(this).setTitle("종료확인")
                // .setIcon(R.drawable.warning)
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //dialog.dismiss();
                        mTimer.cancel();
                        mTimer = null;
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

    //우울증 자가진단
    public void fabexamineAdd() {
        final ImageView videoButton = (ImageView) findViewById(R.id.multiple_actions_video);
        videoButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    videoButton.setPadding(2, 2, 2, 2);
                    videoButton.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    videoButton.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    videoButton.setPadding(0, 0, 0, 0);
                    intent = new Intent(MainActivity.this, ExamineIntroActivity.class);
                    startActivity(intent);
                    System.gc();
                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                    finish();
                }
                return true;
            }
        });

    }

    //안구운동
    public void fabEmdrAdd() {
        final ImageView eyesButton = (ImageView) findViewById(R.id.multiple_actions_eye);
        eyesButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    eyesButton.setPadding(2, 2, 2, 2);
                    eyesButton.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    eyesButton.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    eyesButton.setPadding(0, 0, 0, 0);
                    //실험
                    intent = new Intent(MainActivity.this, EmdrMainActivity.class);
                    startActivity(intent);
                    System.gc();
                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                    finish();
                }
                return true;
            }
        });
    }

    //사운드테라피
    public void fabSoundAdd() {
        final ImageView soundButton = (ImageView) findViewById(R.id.multiple_actions_sound);
        soundButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    soundButton.setPadding(2, 2, 2, 2);
                    soundButton.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    soundButton.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    soundButton.setPadding(0, 0, 0, 0);
                    intent = new Intent(MainActivity.this, SoundMainActivity.class);
                    startActivity(intent);
                    System.gc();
                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                    finish();
                }
                return true;
            }
        });
    }

    private void fabAdminADD() {
        final ImageView adminButton = (ImageView) findViewById(R.id.admin_button);
        adminButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    adminButton.setPadding(2, 2, 2, 2);
                    adminButton.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    adminButton.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    adminButton.setPadding(0, 0, 0, 0);
                    intent = new Intent(MainActivity.this, AdminLoginActivity.class);
                    startActivity(intent);
                    System.gc();
                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                    finish();
                }
                return true;
            }
        });
    }

}