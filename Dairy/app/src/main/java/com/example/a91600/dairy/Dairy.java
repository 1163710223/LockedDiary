package com.example.a91600.dairy;

import org.litepal.crud.DataSupport;

import java.security.PublicKey;

/**
 * Created by 91600 on 2018/3/31.
 */

public class Dairy extends DataSupport{
    private String content;
    private int date;
    private int weekday;
    public Dairy(String content,int date,int weekday){
        this.content=content;
        this.date=date;
        this.weekday=weekday;
    }
    public void setContent(String content){
        this.content=content;
    }
    public void setDate(int date){
        this.date=date;
    }
    public void setWeekday(int weekday){
        this.weekday=weekday;
    }
    public String getContent(){
        return content;
    }
    public int getDate(){
        return date;
    }
    public int getWeekday(){return weekday;};
}
