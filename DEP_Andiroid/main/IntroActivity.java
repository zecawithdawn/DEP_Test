package com.example.posin.myapplication.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.imageloder.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by posin on 2015. 12. 21..
 */

/**
 * 나중에 이미지만 바꾸면 완성 더이상 수정 필요없음
 */
public class IntroActivity extends Activity {
    /**
     * Called when the activity is first created.
     */



    ImageView introView;
    private DisplayImageOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro_activity);
        introView = (ImageView) findViewById(R.id.introimageView);
        introView.setBackgroundResource(R.drawable.posinintro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                System.gc();
                overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);

                finish();
            }
        }, 2000);
    }

}
