package com.example.posin.myapplication.sound;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.imageloder.Constants;
import com.example.posin.myapplication.main.MainActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 * Created by posin on 2015. 12. 29..
 */
public class SoundMainActivity extends Activity {
    private static final String[] IMAGE_URLS = Constants.soundPic;
    ImageView soundView;
    ImageView sea; // 버튼 선언
    ImageView rain;
    ImageView shine;
    ImageView bird;
    ImageView natural;
    Intent intent; //페이지 이동 구현을 위해 선언
    private DisplayImageOptions options;
    private static final String TEST_FILE_NAME = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sound_activity);
        soundView= (ImageView) findViewById(R.id.imageView3);
        sea = (ImageView) findViewById(R.id.sound_sea);
        rain = (ImageView) findViewById(R.id.sound_rain);
        shine = (ImageView) findViewById(R.id.sound_shine);
        bird = (ImageView) findViewById(R.id.sound_bird);
        natural = (ImageView) findViewById(R.id.sound_natural);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(SoundMainActivity.this));
        File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
        if (!testImageOnSdCard.exists()) {
            copyTestImageToSdCard(testImageOnSdCard);
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(IMAGE_URLS[0],soundView,options);
        imageLoader.displayImage(IMAGE_URLS[1],sea,options);
        imageLoader.displayImage(IMAGE_URLS[2], rain, options);
        imageLoader.displayImage(IMAGE_URLS[3], shine, options);
        imageLoader.displayImage(IMAGE_URLS[4], bird, options);
        imageLoader.displayImage(IMAGE_URLS[5], natural, options);

        birdClick();
        rainClick();
        naturalClick();
        seaClick();
        shineClick();
    }
    public void birdClick()
    {
        final ImageView birdBtn = (ImageView) findViewById(R.id.sound_bird);
        birdBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    // Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                    // videoButton.startAnimation(animation);
                    //scale.xml에서 지정한 애니메이션 실행
                    birdBtn.setPadding(2,2,2,2);
                    birdBtn.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    birdBtn.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    birdBtn.setPadding(0, 0, 0, 0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(SoundMainActivity.this, SoundBirdActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    }).start();
                }
                return true;
            }
        });
    }
    public void rainClick()
    {
        final ImageView rainBtn = (ImageView) findViewById(R.id.sound_rain);
        rainBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    // Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                    // videoButton.startAnimation(animation);
                    //scale.xml에서 지정한 애니메이션 실행
                    rainBtn.setPadding(2,2,2,2);
                    rainBtn.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    rainBtn.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    rainBtn.setPadding(0, 0, 0, 0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(SoundMainActivity.this, SoundRainActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    }).start();
                }
                return true;
            }
        });
    }
    public void naturalClick()
    {
        final ImageView naturalBtn = (ImageView) findViewById(R.id.sound_natural);
        naturalBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    // Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                    // videoButton.startAnimation(animation);
                    //scale.xml에서 지정한 애니메이션 실행
                    naturalBtn.setPadding(2,2,2,2);
                    naturalBtn.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    naturalBtn.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    naturalBtn.setPadding(0, 0, 0, 0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(SoundMainActivity.this, SoundNaturalActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    }).start();
                }
                return true;
            }
        });
    }
    public void seaClick()
    {
        final ImageView seaBtn = (ImageView) findViewById(R.id.sound_sea);
        seaBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    // Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                    // videoButton.startAnimation(animation);
                    //scale.xml에서 지정한 애니메이션 실행
                    seaBtn.setPadding(2,2,2,2);
                    seaBtn.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    seaBtn.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    seaBtn.setPadding(0, 0, 0, 0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(SoundMainActivity.this, SoundSeaActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    }).start();
                }
                return true;
            }
        });
    }
    public void shineClick()
    {
        final ImageView shineBtn = (ImageView) findViewById(R.id.sound_shine);
        shineBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (MotionEvent.ACTION_DOWN == action) {
                    // Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                    // videoButton.startAnimation(animation);
                    //scale.xml에서 지정한 애니메이션 실행
                    shineBtn.setPadding(2,2,2,2);
                    shineBtn.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (MotionEvent.ACTION_UP == action) {
                    shineBtn.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                    shineBtn.setPadding(0, 0, 0, 0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(SoundMainActivity.this, SoundShineActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    }).start();
                }
                return true;
            }
        });
    }
    private void copyTestImageToSdCard(final File testImageOnSdCard) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getAssets().open(TEST_FILE_NAME);
                    FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
                    byte[] buffer = new byte[8192];
                    int read;
                    try {
                        while ((read = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, read);
                        }
                    } finally {
                        fos.flush();
                        fos.close();
                        is.close();
                    }
                } catch (IOException e) {
                    L.w("Can't copy test image onto SD card");
                }
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(SoundMainActivity.this).setTitle("종료확인")
                            // .setIcon(R.drawable.warning)
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    intent = new Intent(SoundMainActivity.this, MainActivity.class);
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

}
