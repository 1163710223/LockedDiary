package com.example.a91600.dairy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ModifyUserMsgActivity extends AppCompatActivity {

    private ImageView userimageImage;
    private TextView usernametext;
    private EditText passwordEdit;
    private EditText birthdayEdit;
    private EditText sexEdit;
    private EditText usersignEdit;
    private EditText truthnameEdit;
    private EditText ageEdit;
    private EditText nicknameEdit;

    private int userimage;
    private String username;
    private String password;
    private int birthday;
    private String sex;
    private String usersign;
    private String truthname;
    private int age;
    private String nickname;

    private user user;

    private int a=0;
    private int b=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_msg);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        user = new user();
        user.inituser();
        initView();
        inituser(user);
        showorigin();
    }

    private void inituser(user user){
        List<user> userList= DataSupport.findAll(user.class);
        for(user muser:userList){
            if(muser.getUsername().equals(username)){
                user.setUsername(username);
                user.setPassword(muser.getPassword());
                user.setAge(muser.getAge());
                user.setBirthday(muser.getBirthday());
                user.setDairynum(muser.getDairynum());
                user.setSex(muser.getSex());
                user.setUsersign(muser.getUsersign());
                user.setUserimage(muser.getUserimage());
                user.setNickname(muser.getNickname());
                user.setTruthname(muser.getTruthname());
            }
        }
    }

    public void showorigin(){
        usernametext.setText(user.getUsername());
        passwordEdit.setText(user.getPassword());
        if(user.getNickname()==null||user.getNickname().equals("")){
            nicknameEdit.setHint("暂未设置");
        }else{
            nicknameEdit.setText(user.getNickname());
        }
        if(user.getTruthname()==null||user.getTruthname().equals("")){
            truthnameEdit.setHint("暂未设置");
        }else{
            truthnameEdit.setText(user.getTruthname());
        }
        if(user.getAge()==0){
            ageEdit.setHint("暂未设置");
        }else{
            ageEdit.setText(""+user.getAge());
        }
        if(user.getBirthday()==0){
            birthdayEdit.setHint("暂未设置");
        }else{
            birthdayEdit.setText(""+user.getBirthday());
        }
        if(user.getUsersign()==null||user.getUsersign().equals("")){
            usersignEdit.setHint("暂未设置");
        }else{
            usersignEdit.setText(user.getUsersign());
        }
        if(user.getSex()==null||user.getSex().equals("")){
            sexEdit.setHint("暂未设置");
        }else{
            sexEdit.setText(user.getSex());
        }
    }

    private void initView(){
        usernametext=(TextView) findViewById(R.id.username_modify_textview);
        passwordEdit=(EditText)findViewById(R.id.password_edittext);
        birthdayEdit=(EditText)findViewById(R.id.birthday_edittext);
        sexEdit=(EditText)findViewById(R.id.sex_edittext);
        usersignEdit=(EditText)findViewById(R.id.usersign_edittext);
        truthnameEdit=(EditText)findViewById(R.id.truthname_edittext);
        ageEdit=(EditText)findViewById(R.id.age_edittext);
        nicknameEdit=(EditText)findViewById(R.id.nickname_edittext);
    }

    private void getmsg(){
        username=usernametext.getText().toString();
        password=passwordEdit.getText().toString();
        sex=sexEdit.getText().toString();
        usersign=usersignEdit.getText().toString();
        truthname=truthnameEdit.getText().toString();
        nickname=nicknameEdit.getText().toString();
        try{
            birthday=Integer.valueOf(birthdayEdit.getText().toString());
            a=0;
            if(birthday<10000000||birthday>99999999){
                Toast.makeText(ModifyUserMsgActivity.this,"请按年月日正确输入您的生日",Toast.LENGTH_LONG).show();
                birthday=0;
                a=1;
            }
        }catch (NumberFormatException n){
            Toast.makeText(ModifyUserMsgActivity.this,"您输入的生日格式不正确",Toast.LENGTH_LONG).show();
            a=1;
        }
        try{
            age=Integer.valueOf(ageEdit.getText().toString());
            b=0;
        }catch (NumberFormatException n){
            Toast.makeText(ModifyUserMsgActivity.this,"您输入的年龄格式不正确",Toast.LENGTH_LONG).show();
            b=1;
        }
    }

    public void commitusermsg(View view) {
        getmsg();
        List<user> userList= DataSupport.findAll(user.class);
        for(user muser:userList){
            if(muser.getUsername().equals(username)){
                user user = new user();
                user.setTruthname(truthname);
                user.setNickname(nickname);
                user.setUsersign(usersign);
                user.setSex(sex);
                user.setAge(age);
                user.setBirthday(birthday);
                user.setPassword(password);
                if(a==0&&b==0){
                    user.updateAll("username=?",username);
                    Toast.makeText(ModifyUserMsgActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{

                }
            }
        }
    }
}
