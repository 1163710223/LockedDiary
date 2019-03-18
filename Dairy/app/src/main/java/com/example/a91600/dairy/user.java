package com.example.a91600.dairy;

import android.support.v7.app.AppCompatActivity;

import org.litepal.crud.DataSupport;

import java.util.HashMap;

/**
 * Created by 91600 on 2018/3/23.
 */

public class user extends DataSupport {
    private String username;
    private int age;
    private int userimage;
    private int dairynum;
    private String usersign;
    private String password;
    private String sex;
    private int birthday;
    private String nickname;
    private String truthname;

    public user(HashMap<String,String>map,HashMap<Integer,Boolean>date_record){
        username=map.get("username");
        age=Integer.valueOf(map.get("age"));
        userimage=Integer.valueOf(map.get("userimage"));
        dairynum=Integer.valueOf(map.get("dairynum"));
        usersign=map.get("usersign");
        password=map.get("password");
        birthday=Integer.valueOf(map.get("birthday"));
        sex=map.get("sex");
    }

    public user() {
        username="";
        age=0;
        userimage=0;
        dairynum=0;
        usersign="";
        password="";
        sex="";
        birthday=0;
        nickname="";
        truthname="";
    }

    public void inituser(){

    }

    public void setUsername(String username){
        this.username=username;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setUserimage(int userimage){
        this.userimage=userimage;
    }
    public void setDairynum(int dairynum){
        this.dairynum=dairynum;
    }
    public void setUsersign(String usersign){
        this.usersign=usersign;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public void setBirthday(int birthday){
        this.birthday=birthday;
    }
    public void setNickname(String nickname){
        this.nickname=nickname;
    }
    public void setTruthname(String truthname){
        this.truthname=truthname;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getUsersign(){
        return usersign;
    }
    public int getAge(){
        return age;
    }
    public int getUserimage(){
        return userimage;
    }
    public int getDairynum(){
        return dairynum;
    }
    public String getSex(){
        return sex;
    }
    public int getBirthday(){
        return birthday;
    }
    public String getNickname(){
        return nickname;
    }
    public String getTruthname(){
        return truthname;
    }
}
