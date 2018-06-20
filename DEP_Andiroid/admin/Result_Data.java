package com.example.posin.myapplication.admin;

/**
 * Created by choigwanggyu on 2016. 9. 28..
 */
public class Result_Data {
    private String Test_Year;
    private String Test_Month;
    private String Test_Score;
    private String Test_Set;
    private String Test_Max;

    public Result_Data(String _Year, String _Month, String _Avg, String _Set, String _Max) {
        this.Test_Year = _Year;
        this.Test_Month = _Month;
        this.Test_Score = _Avg;
        this.Test_Set = _Set;
        this.Test_Max = _Max;
    }

    public String getTest_Year() {
        return Test_Year;
    }

    public String getTest_Month() {
        return Test_Month;
    }

    public String getTest_Score() {
        return Test_Score;
    }

    public String getTest_Set() {
        return Test_Set;
    }

    public String getTest_Max() {
        return Test_Max;
    }


}
