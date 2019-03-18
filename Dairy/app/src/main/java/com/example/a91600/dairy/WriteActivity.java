package com.example.a91600.dairy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;

import java.util.List;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;
    private ImageView duigou;
    private TextView quxiao;

    private String username;
    private int date;
    private int weekday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initview();//初始化组件
        initevent();//初始化点击事件

        Intent intent = getIntent();
        username=intent.getStringExtra("username");
        date=Integer.valueOf(intent.getStringExtra("date"));
        weekday=Integer.valueOf(intent.getStringExtra("weekday"));
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏

    }

    private void initevent() {
        quxiao.setOnClickListener(this);
        duigou.setOnClickListener(this);
    }

    private void initview(){
        editText=(EditText)findViewById(R.id.write_edittext);
        duigou=(ImageView)findViewById(R.id.duigou_imageview);
        quxiao=(TextView)findViewById(R.id.quxiao_textview);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.quxiao_textview:
                Toast.makeText(WriteActivity.this,"已销毁",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.duigou_imageview:

                LitePalDB litePalDB = new LitePalDB(username, 1);

                litePalDB.addClassName(Dairy.class.getName());
                LitePal.use(litePalDB);

                String content = editText.getText().toString();

                Dairy dairy = new Dairy(content,date,weekday);
                dairy.save();

                Toast.makeText(WriteActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
