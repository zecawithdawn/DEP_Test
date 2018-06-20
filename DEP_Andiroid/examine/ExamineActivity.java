package com.example.posin.myapplication.examine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.posin.myapplication.LoginTask.ServerTask;
import com.example.posin.myapplication.LoginTask.TaskListener;
import com.example.posin.myapplication.R;
import com.example.posin.myapplication.backpress.BackPressCloseHandler;
import com.example.posin.myapplication.imageloder.Constants;
import com.example.posin.myapplication.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by choigwanggyu on 2016. 10. 3..
 */
public class ExamineActivity extends Activity implements TaskListener {
    private static final String[] ServerAddress = Constants.ServerAddress;
    private static final String[] PhP = Constants.PhP;
    final ArrayList<Question_Set> Testset = new ArrayList<Question_Set>();
    String TestJSON;
    int result_score; // 설문 결과용 변수
    Intent intent;
    int setindex;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getjson_from_sh();
        try {
            JSONObject jsonResponce = new JSONObject(TestJSON);
            JSONArray set = jsonResponce.getJSONArray("set");
            for (int i = 0; i < set.length(); i++) {
                JSONObject data = set.getJSONObject(i);
                Question_Set qs = new Question_Set(data.getString("number"), data.getString("type"), data.getString("value"));
                Testset.add(qs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent = getIntent();

        try {

            setindex = Integer.parseInt(intent.getStringExtra("index"));
        } catch (Exception e) {
            setindex = 0;
        }

        try {
            result_score = Integer.parseInt(intent.getStringExtra("score"));
        } catch (Exception e) {
            result_score = 0;
        }

        if (setindex < Testset.size()) {
            Question_Set e = Testset.get(setindex);
            //이름만 임시로 이렇게
            if (e.get_Type().equals("video")) {
                //video
                Examine_video(e.get_Value());

            } else if (e.get_Type().equals("question")) {
                //question
                Examine_question(e.get_Value());
            }
            //스코어 합산 및 서버 전송
        } else if (setindex == Testset.size()) {
            Examine_Score_Upload();
        } else {

        }

    }

    private void Examine_Finish() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("설문이 종료되었습니다.")        // 제목 설정
                .setMessage("수고하셨습니다 메뉴 선택화면으로 돌아갑니다")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        intent = new Intent(ExamineActivity.this, MainActivity.class);
                        startActivity(intent);
                        System.gc();
                        overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
                        finish();

                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();
    }

    private void getjson_from_sh() {
        SharedPreferences conf = getSharedPreferences("configure", MODE_PRIVATE);
        TestJSON = conf.getString("queset", null);
    }

    //스코어 합산 및 결과 확인후 전송
    private void Examine_Score_Upload() {
        JSONObject insertjson = new JSONObject();
        int max_score = 0;
        SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);

        try {


            insertjson.put("ID", user_info.getString("ID", ""));
            insertjson.put("score", result_score);

            JSONObject jsonResponce = new JSONObject(TestJSON);

            JSONArray conf = jsonResponce.getJSONArray("conf");
            JSONObject temp = conf.getJSONObject(0);
            insertjson.put("queset", temp.getString("name"));

            JSONArray set = jsonResponce.getJSONArray("set");

            for (int i = 0; i < set.length(); i++) {
                JSONObject data = set.getJSONObject(i);
                if(data.getString("type").equals("question")){
                    max_score++;
                }
            }
            insertjson.put("maxscore",String.valueOf(max_score*3));

            ServerTask servertask = new ServerTask("http://"+ServerAddress[0]+"/"+PhP[0], insertjson, this);
            servertask.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void Examine_question(String question) {
        intent = new Intent(ExamineActivity.this, ExamineQuestionActivity.class);
        intent.putExtra("index", String.valueOf(setindex));
        intent.putExtra("question", question);
        intent.putExtra("score", String.valueOf(result_score));
        startActivity(intent);
        System.gc();
        overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
        finish();

    }

    private void Examine_video(String videourl) {
        intent = new Intent(ExamineActivity.this, ExamineVideoActivity.class);
        intent.putExtra("index", String.valueOf(setindex));
        intent.putExtra("score", String.valueOf(result_score));
        intent.putExtra("path", videourl);
        startActivity(intent);
        System.gc();
        overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
        finish();
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
                        intent = new Intent(ExamineActivity.this, MainActivity.class);
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


    @Override
    public void onReceived(JSONObject jsonObject) {
            Examine_Finish();
    }

    @Override
    public void onCanceled() {

    }
}
