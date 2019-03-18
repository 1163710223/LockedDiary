package com.example.a91600.dairy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class EverydaydairyActivity extends AppCompatActivity{

    private String username;
    private int date;
    private int weekday;
    private TextView nodairy;
    private List<Dairy> dairyList = new ArrayList<>();
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        back=(TextView)findViewById(R.id.back_layout1);
        back.setVisibility(View.VISIBLE);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        date=Integer.valueOf(intent.getStringExtra("date"));
        weekday=Integer.valueOf(intent.getStringExtra("weekday"));

        initdairyadapter();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initdairyadapter();
    }

    private void initdairyadapter(){
        initdairy();
        nodairy=(TextView)findViewById(R.id.nodairy);
        if(dairyList.size()!=0)
        {
            nodairy.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(EverydaydairyActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        DairyAdapter adapter = new DairyAdapter(dairyList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DairyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Dairy dairy = dairyList.get(position);
                Intent intent  = new Intent(view.getContext(),ReadActivity.class);
                String conent = dairy.getContent();
                String date=dairy.getDate()+"";
                String weekday = dairy.getWeekday()+"";
                intent.putExtra("content",conent);
                intent.putExtra("date",date);
                intent.putExtra("weekday",weekday);
                intent.putExtra("username",username);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void initdairy(){
        LitePal.use(LitePalDB.fromDefault(username+".db"));
        List<Dairy> dairys= DataSupport.findAll(Dairy.class);
        dairyList=new ArrayList<>();
        for(Dairy dairy:dairys){
            if(dairy.getDate()==date)
            dairyList.add(dairy);
        }
    }

    public void steptowriteactivity(View view){
        Intent intent = new Intent(EverydaydairyActivity.this,WriteActivity.class);

        int datein=date;
        intent.putExtra("date",datein+"");
        intent.putExtra("weekday",weekday+"");
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void steptomainactivity(View view){
        finish();
    }
}