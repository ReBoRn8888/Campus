package com.jredu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.util.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private TextView toolbar_title;
	private Toolbar boolBar;
	private LinearLayout about,feedback,settings,location,favour,commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		about = (LinearLayout)findViewById(R.id.about);
		feedback = (LinearLayout)findViewById(R.id.feedback);
		settings = (LinearLayout)findViewById(R.id.settings);
		location = (LinearLayout)findViewById(R.id.location);
		favour = (LinearLayout)findViewById(R.id.favour);
		commit = (LinearLayout)findViewById(R.id.commit);
		about.setOnClickListener(this);
		feedback.setOnClickListener(this);
		settings.setOnClickListener(this);
		location.setOnClickListener(this);
		favour.setOnClickListener(this);
		commit.setOnClickListener(this);
	}

	public void init(){
		boolBar = (Toolbar)findViewById(R.id.toolbar);
		toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
		toolbar_title.setText("Campus");
		setSupportActionBar(boolBar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, boolBar,R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
		setTabValue();
	}

	public void setTabValue(){
		tabs.setSelectedTextColor(getResources().getColor(R.color.tab_text_color_selected));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.list) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		Bundle b;
		switch(view.getId())
		{
			case R.id.about:
				b = new Bundle();
				b.putString("judge", "关于我们");
				Toast.makeText(MainActivity.this, "关于我们", Toast.LENGTH_LONG).show();
				intent = new Intent(MainActivity.this, VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.feedback:
				b = new Bundle();
				b.putString("judge", "用户反馈");
				Toast.makeText(MainActivity.this, "用户反馈", Toast.LENGTH_LONG).show();
				intent = new Intent(MainActivity.this, VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.settings:
				b = new Bundle();
				b.putString("judge", "用户设置");
				Toast.makeText(MainActivity.this, "用户设置", Toast.LENGTH_LONG).show();
				intent = new Intent(MainActivity.this, VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.location:
				b = new Bundle();
				b.putString("judge", "我的位置");
				Toast.makeText(MainActivity.this, "我的位置", Toast.LENGTH_LONG).show();
				intent = new Intent(MainActivity.this, MapActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.favour:
				b = new Bundle();
				b.putString("judge", "我的收藏");
				Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_LONG).show();
				intent = new Intent(MainActivity.this, VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
		}
	}


	public class MyPagerAdapter extends FragmentPagerAdapter {
		private final String[] TITLES = { "首页", "周边", "更多"};
		List<MyFragment> fragments = new ArrayList<MyFragment>();
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			for(String title:TITLES){
				MyFragment fragment = new MyFragment();
				Bundle args =new Bundle();
				args.putString("param", title);
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
}
