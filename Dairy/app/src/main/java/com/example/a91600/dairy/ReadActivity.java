package com.example.a91600.dairy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;

import java.util.List;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView date_text;
    private TextView content_text;
    private TextView delete_read;
    private TextView back_read;

    private int date;
    private int weekday;
    private String content;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initview();
        initevent();
        initdata();
        String dateshow;

        if(weekday==0||weekday==7){
            dateshow=date/10000+"."+date%10000/100+"."+date%100+"    周7";
        }else {
            dateshow=date/10000+"."+date%10000/100+"."+date%100+"    周"+weekday;
        }
        date_text.setText(dateshow);
        content_text.setText(content);

    }
    private void initview(){
        date_text = (TextView)findViewById(R.id.date_read);
        content_text=(TextView)findViewById(R.id.content_read);
        delete_read=(TextView)findViewById(R.id.delete_read);
        back_read=(TextView)findViewById(R.id.back_read);
    }

    private void initevent(){
        back_read.setOnClickListener(this);
        delete_read.setOnClickListener(this);
    }

    private void initdata(){
        Intent intent=getIntent();
        date=Integer.valueOf(intent.getStringExtra("date"));
        weekday=Integer.valueOf(intent.getStringExtra("weekday"));
        content=intent.getStringExtra("content");
        username=intent.getStringExtra("username");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_read:
                finish();
                break;
            case R.id.delete_read:
                LitePal.use(LitePalDB.fromDefault(username+".db"));
                DataSupport.deleteAll(Dairy.class,"date = ? and content = ?",date+"",content);
                finish();
                break;
        }
    }
}
