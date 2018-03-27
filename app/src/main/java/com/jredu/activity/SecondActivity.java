package com.jredu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.fragment.FavourFragment;
import com.jredu.R;
import com.jredu.util.JSONParser;
import com.jredu.util.PagerSlidingTabStrip;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 建超 on 2016/5/1.
 */
public class SecondActivity extends ActionBarActivity implements View.OnClickListener{

    private String[] title = new String[]{"头像","用户名","性别","绑定手机号","登录密码"};
    private String[] option = new String[]{"修改","修改","设置","设置","修改"};

    private int[] imgIds = new int[]{R.drawable.pinkstar,R.drawable.maji,R.drawable.yipin,R.drawable.hexi,R.drawable.daoxiang};
    private String[] content = new String[]{"品客一佳","马记麻辣烫","一品干锅","河西食堂","稻香冒菜"};

    private int[] imgIds_fun = new int[]{R.drawable.badminton,R.drawable.billiards,R.drawable.shidai_cinema,R.drawable.supermario};
    private String[] content_fun = new String[]{"华师大羽毛球馆","潘晓婷台球俱乐部","上海时代影城","超级玛丽KTV"};

    private int[] life_image = new int[]{R.drawable.wenbixuan,R.drawable.xuxian,R.drawable.shidai_cinema,R.drawable.zhenhao,R.drawable.chuanzaoxin};
    private String[] life_content = new String[]{"文笔轩","许鲜水果店","如家酒店","甄豪健身","川造型"};

    private String[] lesson = new String[]{"软件工程","应用系统课程设计","计算机辅助教育","大学语文","西方教育名著选读","中国古典小说名著解读","电影中的哲学","西方哲学入门"};
    private String[] teacher = new String[]{"钱莹","钱莹","房爱莲","孙国强","周小勇","王意如","张立立","张小勇"};

    private final String AREA_DATE[]={"全部商区","1000米","2000米","3000米"};
    private final String TYPE_DATE[] = {"全部分类","餐饮","娱乐","生活"};
    private final String TIME_DATE[] = {"好评最多","离我最近"};

    private AutoCompleteTextView mauto;
    private Spinner area , type , time;
    private ImageButton back,search;
    private TextView toolbar_title,content2,address;
    private Toolbar boolBar;
    private ListView list,result;
    private ArrayAdapter  areaAdapter,typeAdapter,timeAdapter;
    private ArrayAdapter<String> autoadapter;
    private SimpleAdapter adapter;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private Button login,logout,register,submit;
    private EditText name,password,contents,contacts;
    private InputStream inputStream;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private JSONParser jParser = new JSONParser();
    private ArrayList<HashMap<String, Object>> CommentsList = new ArrayList<HashMap<String, Object>>();
    private List<String> merchantslist = new ArrayList<String>();
    private JSONArray comments = null;
    private String url_my_comments = "http://192.168.1.105:8080/campus/show_my_comments.php";
    private String url_login = "http://192.168.1.105:8080/campus/login.php";
    private String url_new_comment = "http://192.168.1.105:8080/campus/create_new_comment.php";
    private String url_create_feedback = "http://192.168.1.105:8080/campus/create_feedback.php";
    private String url_load_merchants = "http://192.168.1.105:8080/campus/load_all_merchants.php";
    private String url_search = "http://192.168.1.105:8080/campus/search.php";
    private String url_ranklist = "http://192.168.1.105:8080/campus/ranklist.php";
    private String url_load_food = "http://192.168.1.105:8080/campus/load_food.php";
    private String url_load_fun = "http://192.168.1.105:8080/campus/load_fun.php";
    private String url_load_life = "http://192.168.1.105:8080/campus/load_life.php";
    private String url_load_lesson = "http://192.168.1.105:8080/campus/load_lesson.php";
    private String url_load_good = "http://192.168.1.105:8080/campus/good_reputation.php";
    private String url_load_activity = "http://192.168.1.105:8080/campus/activity.php";
    private String url_load_favour = "http://192.168.1.105:8080/campus/load_favour.php";
    private String url_load_detail = "http://192.168.1.105:8080/campus/load_detail.php";
    private final String TAG_SUCCESS = "success";
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;
    int judge,imageid,cnt=0;
    Boolean isFirstIn = Boolean.TRUE;
    String username,judge2,reputation2,type2;
    String spin_distance,spin_type,spin_time;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = getIntent().getExtras();
        judge2 = b.getString("judge");
        switch(judge2){
            case "校车时刻表":
                setContentView(R.layout.information);
                inputStream=getResources().openRawResource(R.raw.schoolbus);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                content2 = (TextView) findViewById(R.id.content);

                content2.setText(getStringFromInputStream(inputStream));
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                break;
            case "快递取货":
                setContentView(R.layout.information);
                inputStream=getResources().openRawResource(R.raw.express);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                content2 = (TextView) findViewById(R.id.content);

                content2.setText(getStringFromInputStream(inputStream));
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                break;
            case "搜索":
                setContentView(R.layout.search);
                mauto = (AutoCompleteTextView) findViewById(R.id.mAuto);
                result = (ListView) findViewById(R.id.result);
                search = (ImageButton) findViewById(R.id.search);

                new LoadMerchantsTask().execute();

                search.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // creating new product in background thread
                        String input=mauto.getText().toString();
                        if(input==null||"".equals(input.trim())){
                            AlertDialog.Builder builder=new Builder(SecondActivity.this);
                            builder.setTitle("警告提示");
                            builder.setMessage("请输入搜索关键字");
                            builder.create().show();
                        }
                        else
                        {
                            new SearchTask().execute();
                        }
                    }
                });
                break;
            case "点评":
                setContentView(R.layout.comment);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                submit = (Button)findViewById(R.id.submit);
                contents = (EditText)findViewById(R.id.content);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                submit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // creating new product in background thread
                        new NewCommentTask().execute();
                    }
                });
                break;
            case "生活":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadLifeTask().execute();

                break;
            case "娱乐":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadFunTask().execute();

                break;
            case "课程":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadLessonTask().execute();

                break;
            case "餐饮":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadFoodTask().execute();

                break;
            case "为您推荐":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadActivityTask().execute();

                break;
            case "好评榜":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadGoodTask().execute();

                break;
            case "排行榜":
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
                area.setSelection(0,false);
                type.setSelection(0,false);
                time.setSelection(0,false);

                spin_distance = "99999";
                spin_type = "全部分类";
                    area.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                            if(cnt == 0)
                                cnt = 1;
                            else
                            {
                                switch (area.getSelectedItem().toString()) {
                                    case "1000米":
                                        spin_distance = "1000";
                                        break;
                                    case "2000米":
                                        spin_distance = "2000";
                                        break;
                                    case "3000米":
                                        spin_distance = "3000";
                                        break;
                                    default:
                                        spin_distance = "99999";
                                        break;
                                }
                                Log.d("距离", spin_distance);
                                new SpinnerTask().execute();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(cnt == 1)
                                cnt = 2;
                            else
                            {
                                spin_type = type.getSelectedItem().toString();
                                Log.d("类型", spin_type);
                                new SpinnerTask().execute();
                                                     }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    time.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(cnt == 0)
                                cnt = 1;
                            else
                            {
                                switch (time.getSelectedItem().toString()) {
                                    case "好评最多":
                                        spin_time = "reputation";
                                        break;
                                    case "离我最近":
                                        spin_time = "distance";
                                        break;
                                }
                                Log.d("距离", spin_distance);
                                Log.d("类型", spin_type);
                                Log.d("分类", spin_time);
                                new SpinnerTask().execute();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//                }

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                break;
            case "登录":
                setContentView(R.layout.login);

                login = (Button) findViewById(R.id.login);
                register = (Button) findViewById(R.id.register);
                name = (EditText) findViewById(R.id.name);
                password = (EditText) findViewById(R.id.password);
                back = (ImageButton)findViewById(R.id.back);

                login.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        new LoginTask().execute();
                    }
                });

                back.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        finish();
                    }
                });
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                toolbar_title.setText(judge2);
                register.setOnClickListener(this);
                break;
            case "我的点评":
                setContentView(R.layout.mycom);
                back = (ImageButton)findViewById(R.id.back);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                list = (ListView) findViewById(R.id.setting_list);

                new LoadMyCommentsTask().execute();
                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                break;
            case "关于我们":
                setContentView(R.layout.about);
                back = (ImageButton)findViewById(R.id.back);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                break;
            case "用户反馈":
                setContentView(R.layout.feedback);
                submit = (Button)findViewById(R.id.submit);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                contents = (EditText)findViewById(R.id.content);
                contacts = (EditText)findViewById(R.id.contact);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);
                submit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // creating new product in background thread
                        new FeedbackTask().execute();
                    }
                });
                break;
            case "用户设置":
                setContentView(R.layout.options);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                list = (ListView) findViewById(R.id.setting_list);
                back = (ImageButton)findViewById(R.id.back);
                logout = (Button)findViewById(R.id.logout);

                toolbar_title.setText(judge2);
                adapter = new SimpleAdapter(this,getData(),R.layout.options_list,
                        new String[]{"title","option"},
                        new int[]{R.id.title,R.id.option});
                back.setOnClickListener(this);
                logout.setOnClickListener(this);
                list.setAdapter(adapter);

                break;
            case "我的收藏":
                setContentView(R.layout.toolbar_list);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                list = (ListView) findViewById(R.id.ranking_list);

                toolbar_title.setText(judge2);
                back.setOnClickListener(this);

                new LoadFavourTask().execute();

                break;
        }

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.logout:
                i = new Intent(SecondActivity.this,MainActivity.class);
                i.putExtra("name","请登录~");
                setResult(101, i);
                Toast.makeText(SecondActivity.this, "退出登录", Toast.LENGTH_LONG).show();
                mySharedPreferences= getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                editor = mySharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                break;
            case R.id.register:
                Bundle b = new Bundle();
                b.putString("judge", "新用户注册");
                Toast.makeText(SecondActivity.this, "新用户注册", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }

    public String getStringFromInputStream(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int hasRead = 0;// 记录读取的字节个数
        StringBuilder result = new StringBuilder("");
        try {
            while ((hasRead = inputStream.read(buffer)) != -1) {
                // 根据读取的字节构建字符串并添加到已有字符串后面
                result.append(new String(buffer, 0, hasRead, "UTF-8"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.toString();
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

    private List<Map<String, Object>> getLifeData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<life_image.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("image",life_image[i]);
            item.put("content",life_content[i]);
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

    private List<Map<String, Object>> getCommentData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<imgIds.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("image",imgIds[i]);
            item.put("title",content[i]);
            list.add(item);
        }
        return list;
    }

    public void OnItemEditClick(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button ib=(Button)layout.findViewById(R.id.listbutton);
        TextView address = (TextView)layout.findViewById(R.id.address);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) ib.getText());//传递内容文件ID
        bundle.putString("address",(String) address.getText());//传递内容文件ID
        bundle.putString("judge","商家详情");//传递内容文件ID
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void OnItemEditClick_lesson_list(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        TextView ib=(TextView)layout.findViewById(R.id.title);
        TextView introduction=(TextView)layout.findViewById(R.id.introduction);
        TextView reputation=(TextView)layout.findViewById(R.id.reputation);
        Button teach=(Button)layout.findViewById(R.id.teacher);
        mySharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        editor.putString("title",(String) ib.getText());
        editor.commit();
        type2 = "课程";
        new LoadDetailTask().execute();
        delay(200);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) ib.getText());//传递内容文件ID
        bundle.putString("judge","课程详情");//传递内容文件ID
        bundle.putString("teach",(String) teach.getText());//传递内容文件ID
        bundle.putString("introduction",(String) introduction.getText());//传递内容文件ID
        bundle.putString("reputation",reputation2);//传递内容文件ID
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void OnItemEditClick_fun(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button title=(Button)layout.findViewById(R.id.listbutton);
        TextView address = (TextView)layout.findViewById(R.id.address);
        TextView expense = (TextView)layout.findViewById(R.id.expense);
        TextView starttime = (TextView)layout.findViewById(R.id.starttime);
        TextView endtime = (TextView)layout.findViewById(R.id.endtime);
        TextView reputation = (TextView)layout.findViewById(R.id.reputation);
        mySharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        editor.putString("title",(String) title.getText());
        editor.commit();
        type2 = "娱乐";
        new LoadDetailTask().execute();
        delay(200);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) title.getText());//传递内容文件ID
        bundle.putString("address",(String) address.getText());//传递内容文件ID
        bundle.putString("expense",(String) expense.getText());//传递内容文件ID
        bundle.putString("starttime",(String) starttime.getText());//传递内容文件ID
        bundle.putString("endtime",(String) endtime.getText());//传递内容文件ID
        bundle.putString("reputation",reputation2);//传递内容文件ID
        bundle.putString("judge","娱乐详情");//传递内容文件ID
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void OnItemEditClick_life(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button title=(Button)layout.findViewById(R.id.listbutton);
        TextView address = (TextView)layout.findViewById(R.id.address);
        TextView expense = (TextView)layout.findViewById(R.id.expense);
        TextView starttime = (TextView)layout.findViewById(R.id.starttime);
        TextView endtime = (TextView)layout.findViewById(R.id.endtime);
        TextView reputation = (TextView)layout.findViewById(R.id.reputation);
        TextView type = (TextView)layout.findViewById(R.id.type);
        mySharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        editor.putString("title",(String) title.getText());
        editor.commit();
        type2 = "生活";
        new LoadDetailTask().execute();
        delay(200);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) title.getText());//传递内容文件ID
        bundle.putString("address",(String) address.getText());//传递内容文件ID
        bundle.putString("expense",(String) expense.getText());//传递内容文件ID
        bundle.putString("starttime",(String) starttime.getText());//传递内容文件ID
        bundle.putString("endtime",(String) endtime.getText());//传递内容文件ID
        bundle.putString("reputation",reputation2);//传递内容文件ID
        bundle.putString("type",(String) type.getText());//传递内容文件ID
        bundle.putString("judge","生活详情");//传递内容文件ID
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void OnItemEditClick_food(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button title=(Button)layout.findViewById(R.id.listbutton);
        TextView address = (TextView)layout.findViewById(R.id.address);
        TextView expense = (TextView)layout.findViewById(R.id.expense);
        TextView starttime = (TextView)layout.findViewById(R.id.starttime);
        TextView endtime = (TextView)layout.findViewById(R.id.endtime);
        mySharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        editor.putString("title",(String) title.getText());
        editor.commit();
        type2 = "餐饮";
        new LoadDetailTask().execute();
        delay(200);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) title.getText());//传递内容文件ID
        bundle.putString("address",(String) address.getText());//传递内容文件ID
        bundle.putString("expense",(String) expense.getText());//传递内容文件ID
        bundle.putString("starttime",(String) starttime.getText());//传递内容文件ID
        bundle.putString("endtime",(String) endtime.getText());//传递内容文件ID
        bundle.putString("reputation",reputation2);//传递内容文件ID
        bundle.putString("judge","餐饮详情");//传递内容文件ID
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void option_click(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        TextView title=(TextView)layout.findViewById(R.id.title);
        Bundle bundle = new Bundle();//创建Bundle对象，用于传递数据
        bundle.putString("title",(String) title.getText());//传递内容文件ID
        bundle.putString("judge","用户设置");//传递内容文件ID
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void search_click(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        Button title=(Button)layout.findViewById(R.id.name);
        TextView type = (TextView)layout.findViewById(R.id.type);
        TextView teacher = (TextView)layout.findViewById(R.id.teacher);
        TextView address = (TextView)layout.findViewById(R.id.address);
        TextView expense = (TextView)layout.findViewById(R.id.expense);
        TextView starttime = (TextView)layout.findViewById(R.id.starttime);
        TextView endtime = (TextView)layout.findViewById(R.id.endtime);
        TextView reputation = (TextView)layout.findViewById(R.id.reputation);
        Bundle bundle = new Bundle();
        bundle.putString("title",(String) title.getText());

        mySharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        editor.putString("title",(String) title.getText());
        editor.commit();
        type2 = (String)type.getText();
        new LoadDetailTask().execute();
        delay(200);
        switch((String)type.getText())
        {
            case "课程":
                bundle.putString("judge","课程详情");
                bundle.putString("teach",(String)teacher.getText());
                bundle.putString("reputation",reputation2);//传递内容文件ID
                bundle.putString("introduction",(String) address.getText());//传递内容文件ID
                break;
            case "餐饮":
                bundle.putString("judge","餐饮详情");
                bundle.putString("title",(String) title.getText());//传递内容文件ID
                bundle.putString("address",(String) address.getText());//传递内容文件ID
                bundle.putString("expense",(String) expense.getText());//传递内容文件ID
                bundle.putString("starttime",(String) starttime.getText());//传递内容文件ID
                bundle.putString("endtime",(String) endtime.getText());//传递内容文件ID
                bundle.putString("reputation",reputation2);//传递内容文件ID
                break;
            case "娱乐":
                bundle.putString("judge","娱乐详情");
                bundle.putString("title",(String) title.getText());//传递内容文件ID
                bundle.putString("address",(String) address.getText());//传递内容文件ID
                bundle.putString("expense",(String) expense.getText());//传递内容文件ID
                bundle.putString("starttime",(String) starttime.getText());//传递内容文件ID
                bundle.putString("endtime",(String) endtime.getText());//传递内容文件ID
                bundle.putString("reputation",reputation2);//传递内容文件ID
                break;
            case "生活":
                bundle.putString("judge","生活详情");
                bundle.putString("title",(String) title.getText());//传递内容文件ID
                bundle.putString("address",(String) address.getText());//传递内容文件ID
                bundle.putString("expense",(String) expense.getText());//传递内容文件ID
                bundle.putString("starttime",(String) starttime.getText());//传递内容文件ID
                bundle.putString("endtime",(String) endtime.getText());//传递内容文件ID
                bundle.putString("reputation",reputation2);//传递内容文件ID
                break;
        }
        Toast.makeText(SecondActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class LoginTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        String nme,pwd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nme = name.getText().toString();
            pwd = password.getText().toString();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Logining..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", nme));
            params.add(new BasicNameValuePair("password", pwd));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_login,
                    "GET", params);

            // check log cat fro response
            Log.d("users", json.toString());


            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent i = new Intent(SecondActivity.this,MainActivity.class);
                    i.putExtra("name",nme);
                    setResult(100, i);
                    judge = 1;
                    finish();
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if(judge==1)
            {
                Toast.makeText(SecondActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                mySharedPreferences= getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                editor = mySharedPreferences.edit();
                editor.putString("name", nme);
                editor.commit();
            }
            else
                Toast.makeText(SecondActivity.this, "登录失败", Toast.LENGTH_LONG).show();
        }

    }

    class LoadMyCommentsTask extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mySharedPreferences= getSharedPreferences("test",
                    Activity.MODE_PRIVATE);
            editor = mySharedPreferences.edit();
            username = mySharedPreferences.getString("name", "");
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading comments. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", username));
            // getting JSON string from URL

            JSONObject json = jParser.makeHttpRequest(url_my_comments, "GET", params);
            // Check your log cat for JSON reponse
            Log.d("All Comments: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    comments = json.getJSONArray("comments");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String comment = c.getString("comment");

//                        imageid = SetImageId(title);

                        // creating new HashMap
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        // adding each child node to HashMap key => value
//                        map.put("image", imageid);
                        map.put("title", title);
                        map.put("comment", comment);

                        // adding HashList to ArrayList
                        CommentsList.add(map);
                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    adapter = new SimpleAdapter(
                            SecondActivity.this,
                            CommentsList,
                            R.layout.mycom_detail,
                            new String[]{"title","comment"/*,"image"*/},
                            new int[]{R.id.title,R.id.comment/*,R.id.image*/});
                    list.setAdapter(adapter);
                }
            });
        }
    }

    class NewCommentTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        String name,content,user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mySharedPreferences= getSharedPreferences("test",
                    Activity.MODE_PRIVATE);
            name = b.getString("title");
            content = contents.getText().toString();
            user = mySharedPreferences.getString("name","");
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Making comment..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("title", name));
            params.add(new BasicNameValuePair("comment", content));
            params.add(new BasicNameValuePair("user", user));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_new_comment,
                    "GET", params);

            // check log cat fro response
            Log.d("comments", json.toString());


            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    judge = 1;
                    finish();
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if(judge==1)
                Toast.makeText(SecondActivity.this, "点评成功", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(SecondActivity.this, "点评失败", Toast.LENGTH_LONG).show();
        }

    }

    class FeedbackTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        String content,user,contact;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mySharedPreferences= getSharedPreferences("test",
                    Activity.MODE_PRIVATE);
            contact = contacts.getText().toString();
            content = contents.getText().toString();
            user = mySharedPreferences.getString("name","");
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Making comment..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("contact", contact));
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("user", user));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_feedback,
                    "GET", params);

            // check log cat fro response
            Log.d("Feedbacks", json.toString());


            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    judge = 1;
                    finish();
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if(judge==1)
            {
                Toast.makeText(SecondActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(SecondActivity.this, "提交失败", Toast.LENGTH_LONG).show();
        }

    }

    class LoadMerchantsTask extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_load_merchants, "GET", params);
            // Check your log cat for JSON reponse
            Log.d("All Merchants: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        String title = c.getString("title");
                        merchantslist.add(title);
                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("title",merchantslist.get(4));
                    autoadapter = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1,merchantslist);
                    mauto.setAdapter(autoadapter);
                }
            });
        }
    }

    class SearchTask extends AsyncTask<String, String, String> {

        String key;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CommentsList.removeAll(CommentsList);
            adapter = new SimpleAdapter(SecondActivity.this,CommentsList,R.layout.search_list,new String[]{"title","type","teacher"},new int[]{R.id.name,R.id.type,R.id.teacher});
            result.setAdapter(adapter);
            key = mauto.getText().toString();
            Log.d("key",key);
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Searching....Please wait.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", key));

            JSONObject json = jsonParser.makeHttpRequest(url_search,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String type = c.getString("type");
                        String teacher = c.getString("teacher");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("type",type);
                        map.put("teacher",teacher);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);

                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(
                                SecondActivity.this,
                                CommentsList,
                                R.layout.search_list,
                                new String[]{"title","type","teacher","address","expense","starttime","endtime","reputation"},
                                new int[]{R.id.name,R.id.type,R.id.teacher,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation});
                        result.setAdapter(adapter);

                    }
                });
                Toast.makeText(SecondActivity.this, "搜索成功", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(SecondActivity.this, "搜索不到结果", Toast.LENGTH_LONG).show();

        }

    }

    class SpinnerTask extends AsyncTask<String, String, String> {

        String distance,type,time,type2;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            distance = spin_distance;
            type = spin_type;
            time = spin_time;
            runOnUiThread(new Runnable() {
                public void run() {
                    CommentsList.removeAll(CommentsList);
                    adapter = new SimpleAdapter(SecondActivity.this,
                            CommentsList,
                            R.layout.fun_list,
                            new String[]{"title","image"},
                            new int[]{R.id.listbutton,R.id.listimage});
                    list.setAdapter(adapter);

                }
            });
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("distance", distance));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("time", time));

            JSONObject json = jsonParser.makeHttpRequest(url_ranklist,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("merchant");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");
                        type2 = c.getString("type");
//                        imageid = SetImageId(title);

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);
                        map.put("type2", type2);
//                        map.put("image", imageid);

                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.life_list,
                                new String[]{/*"image",*/"title","address","expense","starttime","endtime","reputation","type2"},
                                new int[]{/*R.id.listimage,*/R.id.listbutton,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation,R.id.type});
                        list.setAdapter(adapter);
                    }
                });
                Toast.makeText(SecondActivity.this, "搜索成功", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(SecondActivity.this, "搜索不到结果", Toast.LENGTH_LONG).show();

        }

    }

    class LoadFoodTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(url_load_food,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");
//                        imageid = SetImageId(title);

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);
//                        map.put("image", imageid);

                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.food_list,
                                new String[]{/*"image",*/"title","address","expense","starttime","endtime","reputation"},
                                new int[]{/*R.id.listimage,*/R.id.listbutton,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation});
                        list.setAdapter(adapter);

                    }
                });
            }
        }

    }

    class LoadFunTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(url_load_fun,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");

//                        imageid = SetImageId(title);

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);
//                        map.put("image", imageid);

                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.fun_list,
                                new String[]{/*"image",*/"title","address","expense","starttime","endtime","reputation"},
                                new int[]{/*R.id.listimage,*/R.id.listbutton,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation});
                        list.setAdapter(adapter);
                    }
                });
            }
        }

    }

    class LoadLifeTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(url_load_life,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");

//                        imageid = SetImageId(title);

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);
                        //map.put("image", imageid);

                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.life_list,
                                new String[]{/*"image",*/"title","address","expense","starttime","endtime","reputation"},
                                new int[]{/*R.id.listimage,*/R.id.listbutton,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation});
                        list.setAdapter(adapter);
                    }
                });
            }
        }

    }

    class LoadLessonTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(url_load_lesson,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("lessons");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String lesson = c.getString("lesson");
                        String teacher = c.getString("teacher");
                        String introduction = c.getString("introduction");
                        String reputation = c.getString("reputation");

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("lesson", lesson);
                        map.put("teacher", teacher);
                        map.put("introduction", introduction);
                        map.put("reputation", reputation);

                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.lesson_list,
                                new String[]{"lesson","teacher","introduction","reputation"},
                                new int[]{R.id.title,R.id.teacher,R.id.introduction,R.id.reputation});
                        list.setAdapter(adapter);
                    }
                });
            }
        }

    }

    class LoadGoodTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(url_load_good,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");
                        type2 = c.getString("type");

//                        imageid = SetImageId(title);

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);
                        map.put("type", type2);
                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.life_list,
                                new String[]{/*"image",*/"title","address","expense","starttime","endtime","reputation","type"},
                                new int[]{/*R.id.listimage,*/R.id.listbutton,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation,R.id.type});
                        list.setAdapter(adapter);
                    }
                });
            }
        }

    }

    class LoadActivityTask extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading activities. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_load_activity, "GET", params);
            // Check your log cat for JSON reponse
            Log.d("All activities: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    comments = json.getJSONArray("activities");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String content = c.getString("content");
                        String address = c.getString("address");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");

//                        imageid = SetImageId(title);

                        // creating new HashMap
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        // adding each child node to HashMap key => value
//                        map.put("image", imageid);
                        map.put("title", title);
                        map.put("content", content);
                        map.put("address", address);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);

                        // adding HashList to ArrayList
                        CommentsList.add(map);
                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    adapter = new SimpleAdapter(
                            SecondActivity.this,
                            CommentsList,
                            R.layout.activity,
                            new String[]{"title","content","address","starttime","endtime"},
                            new int[]{R.id.title,R.id.content,R.id.address,R.id.starttime,R.id.endtime});
                    list.setAdapter(adapter);
                }
            });
        }
    }

    class LoadFavourTask extends AsyncTask<String, String, String> {

        String user;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mySharedPreferences= getSharedPreferences("test",
                    Activity.MODE_PRIVATE);
            user = mySharedPreferences.getString("name","");
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("user", user));

            JSONObject json = jsonParser.makeHttpRequest(url_load_favour,
                    "GET", params);

            Log.d("Result", json.toString());

            // check for success tag
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    comments = json.getJSONArray("merchants");

                    // looping through All Products
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);

                        // Storing each json item in variable
                        String title = c.getString("title");
                        String address = c.getString("address");
                        String expense = c.getString("expense");
                        String starttime = c.getString("starttime");
                        String endtime = c.getString("endtime");
                        String reputation = c.getString("reputation");
                        String type2 = c.getString("type");

//                        imageid = SetImageId(title);

                        HashMap<String, Object> map = new HashMap<String, Object>();

                        map.put("title", title);
                        map.put("address", address);
                        map.put("expense", expense);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("reputation", reputation);
                        map.put("type", type2);
                        // adding HashList to ArrayList
                        CommentsList.add(map);

                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if(judge == 1)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new SimpleAdapter(SecondActivity.this,
                                CommentsList,
                                R.layout.life_list,
                                new String[]{/*"image",*/"title","address","expense","starttime","endtime","reputation","type"},
                                new int[]{/*R.id.listimage,*/R.id.listbutton,R.id.address,R.id.expense,R.id.starttime,R.id.endtime,R.id.reputation,R.id.type});
                        list.setAdapter(adapter);
                    }
                });
            }
        }

    }

    class LoadDetailTask extends AsyncTask<String, String, String> {

        String merchant,type;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mySharedPreferences= getSharedPreferences("test",
                    Activity.MODE_PRIVATE);
            merchant = mySharedPreferences.getString("title","");
            type = type2;
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("merchant", merchant));
            params.add(new BasicNameValuePair("type", type));

            JSONObject json = jsonParser.makeHttpRequest(url_load_detail,
                    "GET", params);

            Log.d("Result", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    comments = json.getJSONArray("merchants");

                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject c = comments.getJSONObject(i);
                        reputation2 = c.getString("reputation");
                        judge = 1;
                    }
                } else {
                    judge = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

        }

    }

    public int SetImageId(String title) {
        switch(title)
        {
            case "川造型":
                imageid = R.drawable.chuanzaoxin;
                break;
            case "河东食堂":
            case "河西食堂":
                imageid = R.drawable.hexi;
                break;
            case "超级玛丽KTV":
                imageid = R.drawable.supermario;
                break;
            case "文笔轩":
                imageid = R.drawable.wenbixuan;
                break;
            case "许鲜水果店":
                imageid = R.drawable.xuxian;
                break;
            case "稻香冒菜":
                imageid = R.drawable.daoxiang;
                break;
            case "上海时代影城":
                imageid = R.drawable.shidai_cinema;
                break;
            case "品客一佳":
                imageid = R.drawable.pinkstar;
                break;
            case "饭盒子":
                imageid = R.drawable.fanhezi;
                break;
            case "潘晓婷台球俱乐部":
                imageid = R.drawable.billiards;
                break;
            case "华师大羽毛球馆":
                imageid = R.drawable.badminton;
                break;
            default:
                imageid = R.color.toumin;
                break;
        }
        return imageid;
    }

    public void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
