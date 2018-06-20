package com.example.posin.myapplication.backpress;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by posin on 2015. 11. 13..
 */

public class BackPressCloseHandler extends Activity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
            //뒤로 가기 클릭후 2초안으로 안누르면 취소
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
            //2초안으로 누르면 사용자가 지정한 화면으로 이동a
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
