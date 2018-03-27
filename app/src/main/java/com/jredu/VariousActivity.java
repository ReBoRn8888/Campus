package com.jredu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.util.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建超 on 2016/5/1.
 */
public class VariousActivity extends ActionBarActivity implements View.OnClickListener{

    private String[] title = new String[]{"头像","用户名","收货地址","绑定手机号","登陆密码"};
    private String[] option = new String[]{"修改","修改","修改/添加","修改","修改"};

    private int[] imgIds = new int[]{R.drawable.pinkstar,R.drawable.maji,R.drawable.yipin,R.drawable.hexi,R.drawable.daoxiang};
    private String[] content = new String[]{"品客一佳","马记麻辣烫","一品干锅","河西食堂","稻香冒菜"};

    private int[] imgIds_fun = new int[]{R.drawable.badminton,R.drawable.billiards,R.drawable.shidai_cinema,R.drawable.wangyuwangka,R.drawable.supermario};
    private String[] content_fun = new String[]{"华师大羽毛球馆","潘晓婷台球俱乐部","上海时代影城","网鱼网咖","超级玛丽KTV"};

    private String[] lesson = new String[]{"大学语文","西方教育名著选读","中国古典小说名著解读","电影中的哲学","西方哲学入门"};
    private String[] teacher = new String[]{"孙国强","周小勇","王意如","张立立","张小勇"};

    private static final String AREA_DATE[]={"全部商区","1000米","2000米","3000米"};
    private static final String TYPE_DATE[] = {"全部分类","美食","娱乐","购物","生活"};
    private static final String TIME_DATE[] = {"按时间","按距离"};

    private Spinner area , type , time;
    private ImageButton back;
    private TextView toolbar_title;
    private Toolbar boolBar;
    private ListView list;
    private ArrayAdapter  areaAdapter , typeAdapter , timeAdapter;
    private SimpleAdapter adapter2;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String judge = b.getString("judge");
        switch(judge){
            case "娱乐":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.setting_list);


                toolbar_title.setText(judge);
                back.setOnClickListener(this);
                SimpleAdapter adapter_fun = new SimpleAdapter(this,getFunData(),R.layout.fun_list,
                        new String[]{"image","content"},
                        new int[]{R.id.listimage,R.id.listbutton});
                list.setAdapter(adapter_fun);
                break;
            case "课程":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.setting_list);


                toolbar_title.setText(judge);
                back.setOnClickListener(this);
                SimpleAdapter adapter_lesson = new SimpleAdapter(this,getLessonData(),R.layout.lesson_list,
                        new String[]{"lesson","teacher"},
                        new int[]{R.id.title,R.id.teacher});
                list.setAdapter(adapter_lesson);
                break;
            case "推荐":
                setContentView(R.layout.rankinglist);
                back = (ImageButton)findViewById(R.id.back);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                list = (ListView) findViewById(R.id.ranking_list);
                area = (Spinner) findViewById(R.id.area);
                type = (Spinner) findViewById(R.id.type);
                time = (Spinner) findViewById(R.id.time);
                back = (ImageButton)findViewById(R.id.back);

                areaAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text,AREA_DATE);
                typeAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text,TYPE_DATE);
                timeAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text,TIME_DATE);
                areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                area.setAdapter(areaAdapter);
                type.setAdapter(typeAdapter);
                time.setAdapter(timeAdapter);
                area.setSelection(0);
                type.setSelection(0);
                time.setSelection(0);
                adapter2 = new SimpleAdapter(this,getData2(),R.layout.nearby_business,
                        new String[]{"image","content"},
                        new int[]{R.id.listimage,R.id.listbutton});

                list.setAdapter(adapter2);
                toolbar_title.setText(judge);
                back.setOnClickListener(this);
                break;
            case "关于我们":
                setContentView(R.layout.about);
                back = (ImageButton)findViewById(R.id.back);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                toolbar_title.setText(judge);
                back.setOnClickListener(this);
                break;
            case "用户反馈":
                setContentView(R.layout.feedback);
                Button submit = (Button)findViewById(R.id.submit);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);

                toolbar_title.setText(judge);
                back.setOnClickListener(this);
                submit.setOnClickListener(this);
                break;
            case "用户设置":
                setContentView(R.layout.options);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                list = (ListView) findViewById(R.id.setting_list);
                back = (ImageButton)findViewById(R.id.back);

                toolbar_title.setText(judge);
                SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.options_list,
                        new String[]{"title","option"},
                        new int[]{R.id.title,R.id.option});
                back.setOnClickListener(this);
                list.setAdapter(adapter);
                break;
            case "我的收藏":
                setContentView(R.layout.favour);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
                pager = (ViewPager) findViewById(R.id.pager);
                MyPagerAdapter pageradapter = new MyPagerAdapter(getSupportFragmentManager());
                pager.setAdapter(pageradapter);
                tabs.setViewPager(pager);
                setTabValue();
                toolbar_title.setText(judge);
                back.setOnClickListener(this);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                Toast.makeText(VariousActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    public void setTabValue(){
        tabs.setSelectedTextColor(getResources().getColor(R.color.tab_text_color_selected));
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = { "商家", "商品"};
        List<FavourFragment> fragments = new ArrayList<FavourFragment>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            for(String title:TITLES){
                FavourFragment fragment = new FavourFragment();
                Bundle args =new Bundle();
                args.putString("para", title);
                fragment.setArguments(args);
                fragments.add(fragment);
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
        @Override
        public int getCount() {
            return TITLES.length;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    private List<Map<String, Object>> getFunData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<imgIds_fun.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("image",imgIds_fun[i]);
            item.put("content",content_fun[i]);
            list.add(item);
        }
        return list;
    }

    private List<Map<String, Object>> getLessonData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<lesson.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("lesson",lesson[i]);
            item.put("teacher",teacher[i]);
            list.add(item);
        }
        return list;
    }

    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<title.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("title",title[i]);
            item.put("option",option[i]);
            list.add(item);
        }
        return list;
    }

    private List<Map<String, Object>> getData2(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<imgIds.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("image",imgIds[i]);
            item.put("content",content[i]);
            list.add(item);
        }
        return list;
    }

    public void OnItemEditClick(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button ib=(Button)layout.findViewById(R.id.listbutton);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) ib.getText());//传递内容文件ID
        bundle.putString("judge","商家详情");//传递内容文件ID
        Toast.makeText(VariousActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(VariousActivity.this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void OnItemEditClick_lesson_list(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        TextView ib=(TextView)layout.findViewById(R.id.title);
        Button teach=(Button)layout.findViewById(R.id.teacher);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) ib.getText());//传递内容文件ID
        bundle.putString("judge","课程详情");//传递内容文件ID
        bundle.putString("teach",(String) teach.getText());//传递内容文件ID
        Toast.makeText(VariousActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(VariousActivity.this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void OnItemEditClick_fun(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button title=(Button)layout.findViewById(R.id.listbutton);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) title.getText());//传递内容文件ID
        bundle.putString("judge","娱乐详情");//传递内容文件ID
        Toast.makeText(VariousActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(VariousActivity.this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
