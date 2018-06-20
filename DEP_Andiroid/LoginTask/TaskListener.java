package com.example.posin.myapplication.LoginTask;

/**
 * Created by choigwanggyu on 2016. 8. 30..
 */

import org.json.JSONObject;

public interface TaskListener {

    public void onReceived(JSONObject jsonObject);

    public void onCanceled();
}
