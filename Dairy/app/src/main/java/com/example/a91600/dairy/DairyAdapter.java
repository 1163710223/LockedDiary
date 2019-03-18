package com.example.a91600.dairy;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

import java.util.List;

/**
 * Created by 91600 on 2018/3/31.
 */

public class DairyAdapter extends RecyclerView.Adapter<DairyAdapter.ViewHolder> implements View.OnClickListener{

    private List<Dairy> mDairy;

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //define interface
    public static interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View dairyView;
        public TextView dairydateyearandmonth;
        public TextView dairyweekday;
        public TextView dairycontent;
        public TextView dairydateday;

        public ViewHolder(View itemView) {
            super(itemView);
            dairyView = itemView;
            dairycontent=(TextView) itemView.findViewById(R.id.dairy_content);
            dairydateday=(TextView)itemView.findViewById(R.id.dairy_date_day);
            dairydateyearandmonth=(TextView)itemView.findViewById(R.id.dairy_date_yearandmonth);
            dairyweekday=(TextView)itemView.findViewById(R.id.dairy_date_weekday);
        }
    }

    public DairyAdapter(List<Dairy> dairyList){
        mDairy=dairyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dairy_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        /*holder.dairyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Dairy dairy = mDairy.get(position);
                Toast.makeText(v.getContext(),"啧啧啧",Toast.LENGTH_LONG).show();
                Intent intent  = new Intent(v.getContext(),ReadActivity.class);
                String conent = dairy.getContent();
                String dateday = (dairy.getDate()%100)+"";
                String dateyearandmonth = dairy.getDate()/10000+(dairy.getDate()%10000)/100+"";
                String weekday = dairy.getWeekday()+"";
                intent.putExtra("content",conent);
                intent.putExtra("dateday",dateday);
                intent.putExtra("dateyeatandmonth",dateyearandmonth);
                intent.putExtra("weekday",weekday);
                intent.putExtra("username","");
                v.getContext().startActivity(intent);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Dairy dairy = mDairy.get(position);
        if(dairy.getContent()==null||dairy.getContent().equals("")){
            holder.dairycontent.setText("");
        }else{
            holder.dairycontent.setText(dairy.getContent());
        }
        if(dairy.getDate()==0){
            holder.dairydateday.setText("1111111");
            holder.dairydateyearandmonth.setText("111111");
        }else{
            holder.dairydateday.setText(dairy.getDate()%100+"");
            holder.dairydateyearandmonth.setText(dairy.getDate()/10000+"."+(dairy.getDate()%10000)/100);
        }
        if(dairy.getWeekday()==0){
            holder.dairyweekday.setText("周7");
        }else{
            holder.dairyweekday.setText("周"+dairy.getWeekday());
        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDairy.size();
    }


}
