package com.example.posin.myapplication.examine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by posin on 2015. 12. 31..
 */
public class VideoViewActivity extends VideoView {


        // 비디오 뷰 전체 화면 설정
        public VideoViewActivity(Context context, AttributeSet attrs) {
            super(context, attrs);
    }




    @Override
        protected void onMeasure ( int widthMeasureSpec, int heightMeasureSpec){
            Display dis =((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            setMeasuredDimension(dis.getWidth(), dis.getHeight() );
    }
}


