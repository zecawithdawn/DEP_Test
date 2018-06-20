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
public class SettingAdapter extends ArrayAdapter<Setting_Data> {

    private ArrayList<Setting_Data> m_list;
    private int rsrc;

    public SettingAdapter(Context ctx, int rsrcId, int txtId, ArrayList<Setting_Data> data) {
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

        Setting_Data e = m_list.get(position);
        if (e != null) {
            ((TextView) v.findViewById(R.id.result_first)).setText(e.getSet_name());
            ((TextView) v.findViewById(R.id.result_third)).setText(e.getSet_exp());

        }

        return v;


    }
}
