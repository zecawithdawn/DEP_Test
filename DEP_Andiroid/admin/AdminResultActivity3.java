package com.example.posin.myapplication.admin;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.posin.myapplication.LoginTask.ServerTask;
import com.example.posin.myapplication.LoginTask.TaskListener;
import com.example.posin.myapplication.R;
import com.example.posin.myapplication.imageloder.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by user on 2016-09-13.
 */
public class AdminResultActivity3 extends ListActivity implements TaskListener {
    private static final String[] ServerAddress = Constants.ServerAddress;
    private static final String[] PhP = Constants.PhP;
    final ArrayList<Result_Data> abList = new ArrayList<Result_Data>();
    Intent intent;
    ArrayAdapter<Result_Data> m_Adapter;
    Context ctx = this;
    TextView Title_textview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_result_activity);

        getdata();

    }

    private void getdata() {
        JSONObject insertjson = new JSONObject();
        intent = getIntent();


        Title_textview = (TextView) findViewById(R.id.admin_result_text);
        Title_textview.setText(intent.getStringExtra("name") + "의 월별의 진단 결과 입니다");

        try {
            insertjson.put("ID", intent.getStringExtra("ID"));
            insertjson.put("set", intent.getStringExtra("set"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerTask servertask = new ServerTask("http://"+ServerAddress[0]+"/"+PhP[7], insertjson, this);
        servertask.execute();
    }

    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(AdminResultActivity3.this).setTitle("뒤로가기")
                            // .setIcon(R.drawable.warning)
                            .setMessage("이전 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
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

    @Override
    public void onReceived(JSONObject jsonObject) {
        try {
            JSONArray Result_All = jsonObject.getJSONArray("sql_result");
            for (int i = 0; i < Result_All.length(); i++) {
                JSONObject data = Result_All.getJSONObject(i);
                Result_Data qs = new Result_Data(data.getString("year"), data.getString("month"), data.getString("score"), data.getString("queset"),  data.getString("max"));
                abList.add(qs);
            }

            m_Adapter = new AdminResultActivity3.ResultAdapter(this, R.layout.listview_admin_result, R.id.result_first, abList);
            setListAdapter(m_Adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCanceled() {

    }

    private class ResultAdapter extends ArrayAdapter<Result_Data> {
        private ArrayList<Result_Data> items;
        private int rsrc;

        public ResultAdapter(Context ctx, int rsrcId, int txtId, ArrayList<Result_Data> data) {
            super(ctx, rsrcId, txtId, data);
            this.items = data;
            this.rsrc = rsrcId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(rsrc, null);
            }
            Result_Data e = items.get(position);
            if (e != null) {

                ((TextView) v.findViewById(R.id.result_first)).setText(e.getTest_Year() + "년" + e.getTest_Month() + "월");
                ProgressBar progress = (ProgressBar) v.findViewById(R.id.result_second);
                progress.setMax(Integer.parseInt(e.getTest_Max()));
                if (Integer.parseInt(e.getTest_Score()) > Integer.parseInt(e.getTest_Max())/3 * 2) {
                    Drawable draw = getDrawable(R.drawable.progressbar_r);
                    progress.setProgressDrawable(draw);
                } else if (Integer.parseInt(e.getTest_Score()) > Integer.parseInt(e.getTest_Max())/3 * 1) {
                    Drawable draw = getDrawable(R.drawable.progressbar_y);
                    progress.setProgressDrawable(draw);
                } else {
                    Drawable draw = getDrawable(R.drawable.progressbar_g);
                    progress.setProgressDrawable(draw);
                }
                progress.setProgress(Integer.parseInt(e.getTest_Score()));
                ((TextView) v.findViewById(R.id.result_third)).setText(e.getTest_Set());

            }
            return v;
        }
    }
}
