package com.jredu.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.R;
import com.jredu.activity.SecondActivity;
import com.jredu.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener{

	private View view,footView;
	private Intent intent;
	private ListView list;
	private TextView username;
	private Bundle b;
	private SimpleAdapter adapter;
	private JSONParser jParser = new JSONParser();
	private ArrayList<HashMap<String, Object>> CommentsList,CommentsList2;
	private JSONArray comments = null;
	private static String url_all_comments = "http://192.168.1.105:8080/campus/show_all_comments.php";
	private static String url_count_all_comments = "http://192.168.1.105:8080/campus/count_all_comments.php";
	private static String url_changable_count_comments = "http://192.168.1.105:8080/campus/show_changable_count_comments.php";
	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor editor;
	private int imageid,totalCount,loadedCount,nextcount;
	private Handler myHandler = new Handler();
	private int loadItemNum = 4;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String judge = getArguments().getString("param");

		switch (judge)
		{
			case "首页":
				view = inflater.inflate(R.layout.home, container,false);
				ImageButton recommend = (ImageButton)view.findViewById(R.id.recommend);
				ImageButton good = (ImageButton)view.findViewById(R.id.good);
				Button food = (Button)view.findViewById(R.id.food);
				Button fun = (Button)view.findViewById(R.id.fun);
				Button lesson = (Button)view.findViewById(R.id.lesson);
				Button life = (Button)view.findViewById(R.id.life);

				food.setOnClickListener(this);
				fun.setOnClickListener(this);
				lesson.setOnClickListener(this);
				life.setOnClickListener(this);
				recommend.setOnClickListener(this);
				good.setOnClickListener(this);
				break;
			case "周边":
				view = inflater.inflate(R.layout.more, container,false);
				list = (ListView) view.findViewById(R.id.list);
				username = (TextView) view.findViewById(R.id.user);
				new LoadAllCommentsTask().execute();

				footView = getActivity().getLayoutInflater().inflate(R.layout.load, null);
				list.addFooterView(footView);
				Button load = (Button)view.findViewById(R.id.load);
				new GetTotalCountTask().execute();
				load.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						// creating new product in background thread
						if (loadedCount + loadItemNum > totalCount)
							nextcount = totalCount - loadedCount;
						else
							nextcount = loadItemNum;
						new LoadNextCommentsTask().execute();
					}
				});

				break;
			case "更多":
				view = inflater.inflate(R.layout.nearby, container,false);
				Button schoolbus = (Button)view.findViewById(R.id.schoolbus);
				Button express = (Button)view.findViewById(R.id.express);
				Button rankinglist = (Button)view.findViewById(R.id.rankinglist);

				schoolbus.setOnClickListener(this);
				express.setOnClickListener(this);
				rankinglist.setOnClickListener(this);
				break;
		}

//		TextView title = (TextView)view.findViewById(R.id.title);
//		title.setText(getArguments().getString("param"));

		return view;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId())
		{
			case R.id.rankinglist:
				b = new Bundle();
				b.putString("judge", "排行榜");
				Toast.makeText(getActivity(), "排行榜", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.schoolbus:
				b = new Bundle();
				b.putString("judge", "校车时刻表");
				Toast.makeText(getActivity(), "校车时刻表", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.express:
				b = new Bundle();
				b.putString("judge", "快递取货");
				Toast.makeText(getActivity(), "快递取货", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.lesson:
				b = new Bundle();
				b.putString("judge", "课程");
				Toast.makeText(getActivity(), "课程", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.fun:
				b = new Bundle();
				b.putString("judge", "娱乐");
				Toast.makeText(getActivity(), "娱乐", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.food:
				b = new Bundle();
				b.putString("judge", "餐饮");
				Toast.makeText(getActivity(), "餐饮", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.life:
				b = new Bundle();
				b.putString("judge", "生活");
				Toast.makeText(getActivity(), "生活", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.recommend:
				b = new Bundle();
				b.putString("judge", "为您推荐");
				Toast.makeText(getActivity(), "为您推荐", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.good:
				b = new Bundle();
				b.putString("judge", "好评榜");
				Toast.makeText(getActivity(), "好评榜", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), SecondActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
		}
	}

	class LoadAllCommentsTask extends AsyncTask<String, String, String> {


		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mySharedPreferences= getActivity().getSharedPreferences("test",
					Activity.MODE_PRIVATE);
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			params.add(new BasicNameValuePair("num", Integer.toString(loadItemNum)));

			JSONObject json = jParser.makeHttpRequest(url_all_comments, "GET", params);
			// Check your log cat for JSON reponse
			Log.d("All Comments: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// products found
					// Getting Array of Products
					comments = json.getJSONArray("comments");
					CommentsList = new ArrayList<HashMap<String, Object>>();


					// looping through All Products
					for (int i = 0; i < comments.length(); i++) {
						JSONObject c = comments.getJSONObject(i);

						// Storing each json item in variable
						String title = c.getString("title");
						String comment = c.getString("comment");
						String user = c.getString("user");

//						imageid = SetImageId(title);

						// creating new HashMap
						HashMap<String, Object> map = new HashMap<String, Object>();

						// adding each child node to HashMap key => value
//						map.put("image", imageid);
						map.put("title", title);
						map.put("comment", comment);
						map.put("user",user);

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
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					adapter = new SimpleAdapter(
							getActivity(),
							CommentsList,
							R.layout.otherscom_detail,
							new String[]{"title","comment"/*,"image"*/,"user"},
							new int[]{R.id.title,R.id.comment/*,R.id.image*/,R.id.user});
					list.setAdapter(adapter);
					loadedCount = adapter.getCount();
				}
			});
		}
	}

	class GetTotalCountTask extends AsyncTask<String, String, String> {


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
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL

			JSONObject json = jParser.makeHttpRequest(url_count_all_comments, "GET", params);
			// Check your log cat for JSON reponse
			Log.d("Counts: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// products found
					// Getting Array of Products
					comments = json.getJSONArray("counts");
					JSONObject c = comments.getJSONObject(0);
					totalCount = c.getInt("cnt");
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

		}
	}

	class LoadNextCommentsTask extends AsyncTask<String, String, String> {


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
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			params.add(new BasicNameValuePair("start", Integer.toString(loadedCount)));
			params.add(new BasicNameValuePair("end", Integer.toString(nextcount)));

			JSONObject json = jParser.makeHttpRequest(url_changable_count_comments, "GET", params);
			// Check your log cat for JSON reponse
			Log.d("Next Comments: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// products found
					// Getting Array of Products
					comments = json.getJSONArray("comments");
					CommentsList2 = new ArrayList<HashMap<String, Object>>();


					// looping through All Products
					for (int i = 0; i < comments.length(); i++) {
						JSONObject c = comments.getJSONObject(i);

						// Storing each json item in variable
						String title = c.getString("title");
						String comment = c.getString("comment");
						String user = c.getString("user");

//						imageid = SetImageId(title);

						// creating new HashMap
						HashMap<String, Object> map = new HashMap<String, Object>();

						// adding each child node to HashMap key => value
//						map.put("image", imageid);
						map.put("title", title);
						map.put("comment", comment);
						map.put("user",user);

						// adding HashList to ArrayList
						CommentsList2.add(map);
					}
					CommentsList.addAll(CommentsList2);
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
			adapter.notifyDataSetChanged();
			loadedCount = adapter.getCount();
			if(loadedCount == totalCount)
				list.removeFooterView(footView);
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

}
