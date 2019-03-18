package com.example.a91600.dairy;

import com.example.a91600.dairy.DairyAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private user user;
    private String username;
    /**
     * 日记记录集
     */
    private List<Dairy> dairyList = new ArrayList<>();
    /**
     * 滑动页面
     */
    private TextView mTextMessage;
    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;
    /**
     * 第一界面
     */
    private TextView back;
    private TextView write;
    private TextView nodairy;
    /**
     * 第二界面
     */
    private GridView record_gridView;//定义gridView
    private DateAdapter dateAdapter;//定义adapter
    private ImageView record_left;//左箭头
    private ImageView record_right;//右箭头
    private TextView record_title;//标题
    private int year;
    private int month;
    private String title;
    private int[][] days = new int[6][7];
    /**
     * 第三界面
     */
    private TextView nickname;
    private TextView usersign;
    private TextView zhanghao;
    private TextView nicheng;
    private TextView zhenshixingming;
    private TextView xingbie;
    private TextView shengri;
    private TextView gexingqianming;
    private TextView nianling;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    vpager_one.setCurrentItem(0);
                    initdairy();
                    initView1();
                    if(dairyList.size()!=0)
                    {
                        nodairy.setVisibility(View.GONE);
                    }
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    final DairyAdapter adapter = new DairyAdapter(dairyList);
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
                    back.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    vpager_one.setCurrentItem(1);
                    initData();//初始化日期数据
                    initView();//初始化组件
                    setTile();//设置标题
                    initEvent();//组件点击监听事件
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    vpager_one.setCurrentItem(2);
                    initView2();
                    inituser(user);
                    setusermsg();//设置用户信息
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onRestart(){
        super.onRestart();
        if(vpager_one.getCurrentItem()==2){
            user.setUsername(username);
            inituser(user);
            setusermsg();//设置用户信息
        }else if(vpager_one.getCurrentItem()==0){
            initdairy();
            initView1();
            if(dairyList.size()!=0)
            {
                nodairy.setVisibility(View.GONE);
            }
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            final DairyAdapter adapter = new DairyAdapter(dairyList);
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

            initView1();
            back.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        vpager_one = (ViewPager) findViewById(R.id.viewpager);

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.layout1,null,false));
        aList.add(li.inflate(R.layout.layout2,null,false));
        aList.add(li.inflate(R.layout.layout3,null,false));
        mAdapter = new MyPagerAdapter(aList);
        vpager_one.setAdapter(mAdapter);
        vpager_one.setCurrentItem(0);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  //透明导航栏

        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        user=new user();
        user.inituser();
        user.setUsername(username);
        inituser(user);


        //创建属于用户的日记数据库
        LitePalDB litePalDB = new LitePalDB(username, 1);
        litePalDB.addClassName(Dairy.class.getName());
        LitePal.use(litePalDB);
        LitePal.getDatabase();
    }

    private void initData() {
        year = DateUtils.getYear();
        month = DateUtils.getMonth();
    }

    private void initEvent() {
        record_left.setOnClickListener(this);
        record_right.setOnClickListener(this);
        record_gridView.setOnItemClickListener(this);
    }

    private void initView() {
        /**
         * 以下是初始化GridView
         */
        record_gridView = (GridView) findViewById(R.id.record_gridView);
        days = DateUtils.getDayOfMonthFormat(year, month);
        dateAdapter = new DateAdapter(this, days, year, month);//传入当前月的年
        record_gridView.setAdapter(dateAdapter);
        record_gridView.setVerticalSpacing(60);
        record_gridView.setEnabled(true);
        /**
         * 以下是初始化第二界面的表头组件
         */
        record_left = (ImageView) findViewById(R.id.record_left);
        record_right = (ImageView) findViewById(R.id.record_right);
        record_title = (TextView) findViewById(R.id.record_title);
    }
    private void initView2() {
        /**
         * 以下是初始化第三界面的信息展示组件
         */
        nickname=(TextView)findViewById(R.id.nickname_text);
        usersign=(TextView)findViewById(R.id.usersign_text);
        zhanghao=(TextView)findViewById(R.id.zhanghao_text);
        nicheng=(TextView)findViewById(R.id.nicheng_text);
        zhenshixingming=(TextView)findViewById(R.id.zhenshixingming_text);
        xingbie=(TextView)findViewById(R.id.xingbie_text);
        shengri=(TextView)findViewById(R.id.shengri_text);
        gexingqianming=(TextView)findViewById(R.id.gexingqianming_text);
        nianling=(TextView)findViewById(R.id.nianling_text);
    }
    private void initView1(){
        back=(TextView)findViewById(R.id.back_layout1);
        write=(TextView)findViewById(R.id.write_text);
        nodairy=(TextView)findViewById(R.id.nodairy);
    }

    /**
     * 下一个月
     *
     * @return
     */
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 上一个月
     *
     * @return
     */
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 设置标题
     */
    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_left:
                days = prevMonth();
                dateAdapter = new DateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
            case R.id.record_right:
                days = nextMonth();
                dateAdapter = new DateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //DateAdapter.ViewHolder viewHolder=(DateAdapter.ViewHolder) view.getTag();
        Intent intent = new Intent(MainActivity.this,EverydaydairyActivity.class);
        int day=Integer.valueOf((String) ((DateAdapter.ViewHolder) view.getTag()).date_item.getText());
        int weekday=position%7;
        intent.putExtra("weekday",""+weekday);
        intent.putExtra("date",""+(year*10000+month*100+day));
        intent.putExtra("username",username);
        startActivity(intent);
    }

    private void initdairy(){
        dairyList=new ArrayList<>();
        LitePal.use(LitePalDB.fromDefault(username+".db"));
        List<Dairy> dairys= DataSupport.findAll(Dairy.class);
        for(Dairy dairy:dairys){
            dairyList.add(dairy);
        }
    }

    private void inituser(user user){
        LitePal.use(LitePalDB.fromDefault("USERMSG.db"));
        List<user> userList= DataSupport.findAll(user.class);
        for(user muser:userList){
            if(muser.getUsername().equals(user.getUsername())){
                user.setUsername(muser.getUsername());
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

    private void setusermsg(){
        zhanghao.setText(user.getUsername());
        if(user.getNickname()==null||user.getNickname().equals("")){
            nicheng.setText("暂未设置");
        }else{
            nicheng.setText(user.getNickname());
        }
        if(user.getTruthname()==null||user.getTruthname().equals("")){
            zhenshixingming.setText("暂未设置");
        }else{
            zhenshixingming.setText(user.getTruthname());
        }
        if(user.getAge()==0){
            nianling.setText("暂未设置");
        }else{
            nianling.setText(""+user.getAge());
        }
        if(user.getBirthday()==0){
            shengri.setText("暂未设置");
        }else{
            shengri.setText(""+user.getBirthday());
        }
        if(user.getUsersign()==null||user.getUsersign().equals("")){
            gexingqianming.setText("暂未设置");
        }else{
            gexingqianming.setText(user.getUsersign());
        }
        if(user.getSex()==null||user.getSex().equals("")){
            xingbie.setText("暂未设置");
        }else{
            xingbie.setText(user.getSex());
        }

        if(user.getNickname()==null||user.getNickname().equals("")){
            nickname.setText("暂未设置");
        }else{
            nickname.setText(user.getNickname());
        }
        if(user.getUsersign()==null||user.getUsersign().equals("")){
            usersign.setText("暂未设置");
        }else{
            usersign.setText(user.getUsersign());
        }
    }

    public void steptomodifylayout(View view){
        String username = user.getUsername();
        Intent intent = new Intent(MainActivity.this,ModifyUserMsgActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void steptowriteactivity(View view){
        String username = user.getUsername();
        Intent intent = new Intent(MainActivity.this,WriteActivity.class);
        intent.putExtra("date",""+(DateUtils.getYear()*10000+DateUtils.getMonth()*100+DateUtils.getCurrentDayOfMonth()));
        intent.putExtra("weekday",""+(DateUtils.getCurrentDayOfWeek()-1));
        intent.putExtra("username",username);
        Log.i("jjfujj", ""+DateUtils.getCurrentDayOfWeek());
        startActivity(intent);
    }

}
