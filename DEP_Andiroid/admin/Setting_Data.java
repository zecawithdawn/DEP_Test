package com.example.posin.myapplication.admin;

/**
 * Created by choigwanggyu on 2016. 10. 11..
 */
public class Setting_Data {

    private String Set_name;
    private String Set_exp;

    public Setting_Data(String _Name, String _Explication) {
        this.Set_name = _Name;
        this.Set_exp = _Explication;
    }

    public String getSet_name() {
        return Set_name;
    }

    public String getSet_exp() {
        return Set_exp;
    }


}

