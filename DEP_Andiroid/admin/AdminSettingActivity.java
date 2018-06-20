package com.example.posin.myapplication.admin;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.posin.myapplication.LoginTask.ServerTask;
import com.example.posin.myapplication.LoginTask.TaskListener;
import com.example.posin.myapplication.R;
import com.example.posin.myapplication.imageloder.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 2016-09-13.
 */
public class AdminSettingActivity extends ListActivity implements TaskListener {
    private static final String[] ServerAddress = Constants.ServerAddress;
    private static final String[] PhP = Constants.PhP;
    Intent intent;
    JSONArray set;

    ArrayList<Setting_Data> set_data = new ArrayList<Setting_Data>();
    ArrayAdapter<Setting_Data> m_Adapter;
    TextView Title_textview;

    protected void onCreate(Bundle savedInstanceState) {
        //초기값 지정 , xml연결
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_setting_activity);
        JSONObject requestjson = new JSONObject();
        try {
            requestjson.put("server", "adminsetting");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Title_textview = (TextView) findViewById(R.id.admin_setting_text);
        Title_textview.setText("자가진단의 질문셋트 변경을 원하시면 아래의 질문 셋트중에 하나를 선택해주세요");

        ServerTask servertask = new ServerTask("http://" + ServerAddress[1] + "/" + PhP[3], requestjson, this);
        servertask.execute();
    }


    public void onListItemClick(ListView parent, View v, int pos, long id) {
        final Setting_Data ent = m_Adapter.getItem(pos);
        final String file_name = ent.getSet_name();

        JSONObject requestjson = new JSONObject();
        try {
            requestjson.put("file",file_name);
            requestjson.put("file_name", file_name + ".json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerTask servertask = new ServerTask("http://" + ServerAddress[1] + "/" + PhP[4], requestjson, this);
        servertask.execute();
    }


    private void settinglistview(JSONArray set) {

        for (int i = 0; i < set.length(); i++) {
            JSONObject data = null;
            try {
                data = this.set.getJSONObject(i);
                String title = data.getString("name");
                String exp = data.getString("exp");
                Setting_Data se = new Setting_Data(title, exp);
                set_data.add(se);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        m_Adapter = new SettingAdapter(this, R.layout.listview_admin_setting, R.id.setting_first, set_data);
        setListAdapter(m_Adapter);

    }

    private void settingquest(JSONObject jsonObject) {

        SharedPreferences conf = getSharedPreferences("configure", MODE_PRIVATE);
        SharedPreferences.Editor editor = conf.edit();
        editor.clear();
        editor.putString("queset", jsonObject.toString());
        editor.apply();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" ")        // 제목 설정
                .setMessage("질문 셋트가 변경되었습니다")        // 메세지 설정
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();


    }

    @Override
    public void onReceived(JSONObject jsonObject) {
        try {

            set = jsonObject.getJSONArray("set_list");
            settinglistview(set);
        } catch (Exception e) {

            settingquest(jsonObject);
        }
    }


    @Override
    public void onCanceled() {

    }

    public void onBackPressed() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog dialog;
                dialog = new AlertDialog.Builder(AdminSettingActivity.this).setTitle("뒤로가기")
                        // .setIcon(R.drawable.warning)
                        .setMessage("이전 화면으로 가시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                //dialog.dismiss();
                                intent = new Intent(AdminSettingActivity.this, AdminActivity.class);
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

    }

    private class SettingAdapter extends ArrayAdapter<Setting_Data> {
        private ArrayList<Setting_Data> items;
        private int rsrc;

        public SettingAdapter(Context ctx, int rsrcId, int txtId, ArrayList<Setting_Data> data) {
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
            SharedPreferences conf = getSharedPreferences("configure", MODE_PRIVATE);


            Setting_Data e = items.get(position);
            if (e != null) {
                ((TextView) v.findViewById(R.id.setting_first)).setText(e.getSet_name());
                ((TextView) v.findViewById(R.id.setting_second)).setText(e.getSet_exp());
            }
            return v;
        }
    }
}
