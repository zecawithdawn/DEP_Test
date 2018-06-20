package com.example.posin.myapplication.examine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.posin.myapplication.LoginTask.ServerTask;
import com.example.posin.myapplication.LoginTask.TaskListener;
import com.example.posin.myapplication.R;
import com.example.posin.myapplication.imageloder.Constants;
import com.example.posin.myapplication.main.MainActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by choigwanggyu on 2016. 8. 15..
 */
public class ExamineLoginActivity extends Activity implements TaskListener {
    private static final String[] ServerAddress = Constants.ServerAddress;
    private static final String[] PhP = Constants.PhP;

    Intent intent;
    Button loginBtn;
    Button registerBtn;
    EditText idtext;
    EditText pwtext;

    EditText Edit_Id;
    EditText Edit_Name;
    EditText Edit_Pw;
    EditText Edit_Rpw;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //초기값 지정 , xml연결
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.examine_login_activity);

        loginBtn = (Button) findViewById(R.id.login_btn);
        idtext = (EditText) findViewById(R.id.login_id);
        pwtext = (EditText) findViewById(R.id.login_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeLoginProcess();

            }
        });
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProcess();

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void registerProcess() {
        final Context mContext = ExamineLoginActivity.this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.custom_dialog));


        Edit_Id = (EditText) layout.findViewById(R.id.custom_dialog_id);
        Edit_Name = (EditText) layout.findViewById(R.id.custom_dialog_name);
        Edit_Pw = (EditText) layout.findViewById(R.id.custom_dialog_pw);
        Edit_Rpw = (EditText) layout.findViewById(R.id.custom_dialog_rpw);

        builder.setTitle("회원가입");
        builder.setView(layout);
        builder.setPositiveButton("회원가입",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = false;
                if (Edit_Id.getText().toString().equals("") || Edit_Name.getText().toString().equals("") || Edit_Pw.getText().toString().equals("") || Edit_Rpw.getText().toString().equals("")){
                    Toast.makeText(mContext,"입력되지 않은 칸이 존재합니다",Toast.LENGTH_SHORT).show();
                }else if(!Edit_Pw.getText().toString().equals(Edit_Rpw.getText().toString())){
                    Toast.makeText(mContext,"동일한 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                    CharSequence inputStr = Edit_Id.getText();

                    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(inputStr);
                    if (matcher.matches()) {
                        JSONObject requestjson = new JSONObject();
                        try {
                            requestjson.put("ID", Edit_Id.getText().toString());
                            requestjson.put("PW", Edit_Pw.getText().toString());
                            requestjson.put("name", Edit_Name.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ServerTask servertask = new ServerTask("http://" + ServerAddress[0] + "/Examine_register.php", requestjson, new TaskListener() {
                            @Override
                            public void onReceived(JSONObject jsonObject) {
                                try {
                                    if(jsonObject.getString("server_responce").equals("1")){
                                        Toast.makeText(mContext,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(mContext,"서버에러 관리자에게 문의하세요",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext,"서버의 응답이 없습니다. 관리자에게 문의하세요",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCanceled() {

                            }
                        });
                        servertask.execute();
                    }else {
                        Toast.makeText(mContext,"이메일 형태의 아이디가 아닙니다.",Toast.LENGTH_SHORT).show();
                    }
                }
                if(wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });

    }


    private void beforeLoginProcess() {
        if (check_internetstate() && !idtext.getText().toString().equals("") && !pwtext.getText().toString().equals("")) {
            JSONObject requestjson = new JSONObject();
            try {
                requestjson.put("ID", idtext.getText().toString());
                requestjson.put("password", pwtext.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ServerTask servertask = new ServerTask("http://" + ServerAddress[0] + "/" + PhP[1], requestjson, this);
            servertask.execute();
        } else {
            Toast.makeText(this, "ID PASSWORD를 제대로 입력해주세요", Toast.LENGTH_LONG).show();
        }
    }

    private boolean check_internetstate() {
        ConnectivityManager cManager;
        NetworkInfo mobile;
        NetworkInfo wifi;

        cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobile.isConnected() || wifi.isConnected()) {
            return true;
        } else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //loginprocess  1.정상전송 - 로그인성공
    //              2.정상전송 - 로그인실패 - 비밀번호틀림
    //              3.정상전송 - 로그인실패 - 아이디없음
    @Override
    public void onReceived(JSONObject jsonresponce) {
        String jres = null;
        try {
            jres = jsonresponce.getString("sever_responce");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Toast.makeText(this, "서버에러 입니다.", Toast.LENGTH_LONG).show();
        }
        assert jres != null;
        if (jres.equals("")) {
            Log.d("서버가 응답하지 않습니다", "서버가 응답하지 않습니다");
        } else if (jres.equals("1")) {
            SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = user_info.edit();
            editor.clear();
            editor.putString("ID",idtext.getText().toString());
            editor.apply();
            intent = new Intent(ExamineLoginActivity.this, ExamineActivity.class);
            startActivity(intent);
            System.gc();
            overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
            finish();

        } else if (jres.equals("2")) {
            Toast.makeText(this, "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
        } else if (jres.equals("3")) {
            Toast.makeText(this, "존재하지 않는 아이디 입니다.", Toast.LENGTH_LONG).show();
            idtext.setText("");
            pwtext.setText("");
        } else {
            Toast.makeText(this, "서버에러 입니다.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void onBackPressed() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(ExamineLoginActivity.this).setTitle("종료확인")
                            // .setIcon(R.drawable.warning)
                            .setMessage("메인 화면으로 가시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    //dialog.dismiss();
                                    Intent intent = new Intent(ExamineLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
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
            });
        } catch (InputMismatchException e) {
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ExamineLogin Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
