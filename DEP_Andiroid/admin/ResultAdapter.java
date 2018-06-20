package com.example.posin.myapplication.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.posin.myapplication.R;

import java.util.ArrayList;

/**
 * Created by choigwanggyu on 2016. 9. 25..
 */
public class ResultAdapter extends ArrayAdapter<Result_Data> {

    private ArrayList<Result_Data> m_list;
    private int rsrc;

    public ResultAdapter(Context ctx, int rsrcId, int txtId, ArrayList<Result_Data> data) {
        super(ctx, rsrcId, txtId, data);
        this.m_list = data;
        this.rsrc = rsrcId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(rsrc, null);
        }

        Result_Data e = m_list.get(position);
        if (e != null) {
            ((TextView) v.findViewById(R.id.result_first)).setText(e.getTest_Year());
            ((ProgressBar) v.findViewById(R.id.result_second)).setProgress(Integer.parseInt(e.getTest_Score()));
            ((TextView) v.findViewById(R.id.result_third)).setText(e.getTest_Set());

        }

        return v;


    }
}
