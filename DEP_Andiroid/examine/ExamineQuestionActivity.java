package com.example.posin.myapplication.examine;

/**
 * Created by choigwanggyu on 2016. 10. 5..
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.posin.myapplication.R;
import com.example.posin.myapplication.main.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ExamineQuestionActivity extends Activity implements View.OnClickListener {

    SeekBar Que_SeekBar;
    ImageView Que_Button;
    TextView Que_text;
    int flag;
    Intent intent; // 페이지 이동을 위한 선언
    int setindex;
    int resultscore;
    int middleScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.examine_question_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ExamineQuestionActivity.this));


        setLayout();

        ViewTreeObserver vto = Que_SeekBar.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                Resources res = getResources();
                Drawable thumb = res.getDrawable(R.drawable.ex_seekbar_thumb);
                int h = (int) (Que_SeekBar.getMeasuredHeight() * 0.5); // 8 * 1.5 = 12
                int w = h;
                Bitmap bmpOrg = ((BitmapDrawable) thumb).getBitmap();
                Bitmap bmpScaled = Bitmap.createScaledBitmap(bmpOrg, w, h, true);
                Drawable newThumb = new BitmapDrawable(res, bmpScaled);
                newThumb.setBounds(0, 0, newThumb.getIntrinsicWidth(), newThumb.getIntrinsicHeight());
                Que_SeekBar.setThumb(newThumb);

                Que_SeekBar.getViewTreeObserver().removeOnPreDrawListener(this);

                return true;
            }
        });


    }


    public void setLayout() {

        Que_SeekBar = (SeekBar) findViewById(R.id.test_SeekBar);
        Que_text = (TextView) findViewById(R.id.test_Question_Text);
        Que_Button = (ImageView) findViewById(R.id.test_Next_Button);

        flag = 0;

        intent = getIntent();
        setindex = Integer.parseInt(intent.getStringExtra("index"));
        resultscore = Integer.parseInt(intent.getStringExtra("score"));
        Que_text.setText("Q. " + intent.getStringExtra("question"));
        Que_Button.setOnClickListener(this);

        Que_SeekBar.setMax(99);
        Que_SeekBar.incrementProgressBy(1);
        Que_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                middleScore = progress / 25;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                flag = 1;
            }
        });
    }

    public void onClick(final View v) {

        if (flag == 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 버튼을 눌렀을때 값 저장 , 페이지 이동

                    intent = new Intent(ExamineQuestionActivity.this, ExamineActivity.class);
                    intent.putExtra("score", String.valueOf(resultscore + middleScore));
                    intent.putExtra("index", String.valueOf(++setindex));
                    startActivity(intent);
                    System.gc();
                    overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                    ImageLoader.getInstance().clearMemoryCache();
                    ImageLoader.getInstance().clearDiskCache();
                    finish();
                }

            }).start();
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog;
        dialog = new AlertDialog.Builder(this).setTitle("종료확인")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //dialog.dismiss();
                        intent = new Intent(ExamineQuestionActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
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
}

