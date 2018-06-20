package com.example.posin.myapplication.sound;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.flavienlaurent.notboringactionbar.AlphaForegroundColorSpan;
import com.example.posin.myapplication.flavienlaurent.notboringactionbar.KenBurnsSupportView;

/**
 * Created by posin on 2015. 12. 31..
 */
public class SoundSeaActivity extends ActionBarActivity {
    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();
    private KenBurnsSupportView mHeaderPicture;// 애니메이션 효과 구현
    Context mContext;
    LayoutInflater inflater;
    private ViewPager mViewPager;//바뀔 이미지 지정
    private PagerAdapter mPagerAdapter;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private  static MediaPlayer sea;// 치료 음악 지정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 초기값 지정
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_sea_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mContext = getApplicationContext();
        inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        setLayout();

        sea = MediaPlayer.create(this,R.raw.seasound);
        sea.setLooping(true);
        sea.start();
    }

    public void setLayout() {
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + 1680;

        mHeaderPicture = (KenBurnsSupportView) findViewById(R.id.sea_header_picture);
        mHeaderPicture.setResourceIds(R.drawable.sound_sea_bg1, R.drawable.sound_sea_bg2);
        mViewPager = (ViewPager) findViewById(R.id.sea_pager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mPagerAdapter);

        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(0xffffffff);
    }
    @Override
    public void onBackPressed() {
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(this).setTitle("종료확인")
                // .setIcon(R.drawable.warning)
                .setMessage("치료를 종료하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //dialog.dismiss();
                        Intent intent = new Intent(SoundSeaActivity.this,SoundMainActivity.class);
                        startActivity(intent);
                        sea.stop();
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

    @Override
    public void onRestart(){
        // 다시 시작 할때 중단지점 부터 재생
        super.onRestart();
        sea = MediaPlayer.create(this,R.raw.seasound);
        sea.setLooping(true);
        sea.start();
    }
    @Override
    public void onStop(){
        // 다시 시작 할때 중단지점 부터 재생
        super.onStop();
    }
    @Override
    public void onPause(){
        // 다시 시작 할때 중단지점 부터 재생
        super.onPause();
        sea.stop();
    }
}