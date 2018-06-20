package com.example.posin.myapplication.examine;

import android.os.SystemClock;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by choigwanggyu on 2016. 11. 15..
 */

public class FileDownload extends Thread {
    private String fileDownloadPath;
    private JSONObject data;
    private File file;
    private boolean mRunning = false;

    FileDownload(String fileDownloadPath, JSONObject data, File file) {
        this.fileDownloadPath = fileDownloadPath;
        this.data = data;
        this.file = file;
    }

    @Override
    public void run() {
        mRunning = true;
        while (mRunning) {
            try {
                SystemClock.sleep(2000);
                URL u = new URL(fileDownloadPath + data.getString("value") + ".mp4");
                URLConnection con = u.openConnection();
                int lengthofContent = con.getContentLength();

                //받아오고
                DataInputStream DIStream = new DataInputStream(u.openStream());
                byte[] buffer = new byte[lengthofContent];
                DIStream.readFully(buffer);
                DIStream.close();

                Log.d("file path", file.toString());
                //저장하고
                DataOutputStream DOSream = new DataOutputStream(new FileOutputStream(file));
                DOSream.write(buffer);
                DOSream.flush();
                DOSream.close();
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void close() {
        mRunning = false;
    }
}
