package com.example.posin.myapplication.examine;

/**
 * Created by choigwanggyu on 2016. 10. 5..
 */
public class Question_Set {
    private String Examine_Index;
    private String Examine_Type;
    private String Examine_Value;

    public Question_Set(String _Index,String _Type,String _Value){
        this.Examine_Index=_Index;
        this.Examine_Type = _Type;
        this.Examine_Value = _Value;
    }

    public String get_Index(){
        return Examine_Index;
    }

    public String get_Type(){
        return Examine_Type;
    }

    public String get_Value(){
        return Examine_Value;
    }
}
