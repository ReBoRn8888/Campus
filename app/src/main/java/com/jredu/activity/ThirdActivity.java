package com.jredu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.R;
import com.jredu.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends Activity{
	
	private TextView title;
	private ImageButton back,like,good;
	private Button map,register,comment,submit;
	private TextView toolbar_title,teacher,address,expense,starttime,endtime,introduction,reputation,type;
	private Toolbar boolBar;
	private EditText name,password,password2,text;
	private CheckBox agree;
	private Bundle bundle;
	private ProgressDialog pDialog;
	private JSONParser jsonParser = new JSONParser();
	private static String url_register = "http://192.168.1.105:8080/campus/create_new_user.php";
	private static String url_option = "http://192.168.1.105:8080/campus/option_setting.php";
	private static String url_new_favour = "http://192.168.1.105:8080/campus/add_new_favour.php";
	private static String url_search_favour = "http://192.168.1.105:8080/campus/search_favour.php";
	private static String url_delete_favour = "http://192.168.1.105:8080/campus/delete_favour.php";
	private static String url_set_good = "http://192.168.1.105:8080/campus/set_good.php";
	int judge,exist,gooded;
	String good_type;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;




	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		gooded = 0;
        bundle = getIntent().getExtras();
		sharedPreferences= getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		String judge2 = bundle.getString("judge");
		switch(judge2)
		{
			case "餐饮详情":
				setContentView(R.layout.fun_detail);
				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
				back = (ImageButton)findViewById(R.id.back);
				map = (Button)findViewById(R.id.map);
				title = (TextView)findViewById(R.id.title);
				comment = (Button)findViewById(R.id.comment);
				address = (TextView) findViewById(R.id.address);
				expense = (TextView) findViewById(R.id.expense);
				starttime = (TextView) findViewById(R.id.starttime);
				endtime = (TextView) findViewById(R.id.endtime);
				reputation = (TextView) findViewById(R.id.reputation);
				like = (ImageButton) findViewById(R.id.like);
				good = (ImageButton) findViewById(R.id.good);

				toolbar_title.setText(bundle.getString("judge"));
				title.setText(bundle.getString("title"));
				address.setText(bundle.getString("address"));
				expense.setText(bundle.getString("expense"));
				starttime.setText(bundle.getString("starttime"));
				endtime.setText(bundle.getString("endtime"));
				reputation.setText(bundle.getString("reputation"));

				exist=0;
				good_type = "餐饮";

				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

				new SearchFavourTask().execute();
				like.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							if(exist == 1)
								new DeleteFavourTask().execute();
							else
								new AddNewFavourTask().execute();
						}
					}
				});

				good.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(gooded == 0)
							new SetGoodTask().execute();
						else
							Toast.makeText(ThirdActivity.this, "不可重复点赞", Toast.LENGTH_LONG).show();
					}
				});

				map.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle b = new Bundle();
						b.putString("title", bundle.getString("title"));
						Toast.makeText(ThirdActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
						Intent intent = new Intent(ThirdActivity.this, OverlayActivity.class);
						intent.putExtras(b);
						startActivity(intent);
					}
				});
				comment.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							Bundle b = new Bundle();
							b.putString("judge", "点评");
							b.putString("title", bundle.getString("title"));
							Toast.makeText(ThirdActivity.this, "点评", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
							intent.putExtras(b);
							startActivity(intent);
						}
					}
				});
				break;
			case "课程详情":
				setContentView(R.layout.lesson);
                boolBar = (Toolbar)findViewById(R.id.toolbar);
                toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
                back = (ImageButton)findViewById(R.id.back);
                title = (TextView)findViewById(R.id.title);
				teacher = (TextView)findViewById(R.id.teacher);
				introduction = (TextView)findViewById(R.id.introduction);
				reputation = (TextView)findViewById(R.id.reputation);
				comment = (Button)findViewById(R.id.comment);
				good = (ImageButton) findViewById(R.id.good);

				toolbar_title.setText(bundle.getString("judge"));
                title.setText(bundle.getString("title"));
				teacher.setText(bundle.getString("teach"));
				introduction.setText(bundle.getString("introduction"));
				reputation.setText(bundle.getString("reputation"));

				good_type = "课程";

				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

				good.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(gooded == 0)
							new SetGoodTask().execute();
						else
							Toast.makeText(ThirdActivity.this, "不可重复点赞", Toast.LENGTH_LONG).show();
					}
				});

				comment.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							Bundle b = new Bundle();
							b.putString("judge", "点评");
							b.putString("title", bundle.getString("title"));
							Toast.makeText(ThirdActivity.this, "点评", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
							intent.putExtras(b);
							startActivity(intent);
						}
					}
				});
				break;
			case "娱乐详情":
				setContentView(R.layout.fun_detail);
				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
				address = (TextView) findViewById(R.id.address);
				expense = (TextView) findViewById(R.id.expense);
				starttime = (TextView) findViewById(R.id.starttime);
				endtime = (TextView) findViewById(R.id.endtime);
				reputation = (TextView) findViewById(R.id.reputation);
				back = (ImageButton)findViewById(R.id.back);
				map = (Button)findViewById(R.id.map);
				title = (TextView)findViewById(R.id.title);
				comment = (Button)findViewById(R.id.comment);
				like = (ImageButton) findViewById(R.id.like);
				good = (ImageButton) findViewById(R.id.good);

				toolbar_title.setText(bundle.getString("judge"));
				title.setText(bundle.getString("title"));
				address.setText(bundle.getString("address"));
				expense.setText(bundle.getString("expense"));
				starttime.setText(bundle.getString("starttime"));
				endtime.setText(bundle.getString("endtime"));
				reputation.setText(bundle.getString("reputation"));

				good_type = "娱乐";

				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

				map.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle b = new Bundle();
						b.putString("title", bundle.getString("title"));
						Toast.makeText(ThirdActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
						Intent intent = new Intent(ThirdActivity.this, OverlayActivity.class);
						intent.putExtras(b);
						startActivity(intent);
					}
				});

				new SearchFavourTask().execute();
				like.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							if(exist == 1)
								new DeleteFavourTask().execute();
							else
								new AddNewFavourTask().execute();
						}
					}
				});

				good.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(gooded == 0)
							new SetGoodTask().execute();
						else
							Toast.makeText(ThirdActivity.this, "不可重复点赞", Toast.LENGTH_LONG).show();
					}
				});

				comment.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							Bundle b = new Bundle();
							b.putString("judge", "点评");
							b.putString("title", bundle.getString("title"));
							Toast.makeText(ThirdActivity.this, "点评", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
							intent.putExtras(b);
							startActivity(intent);
						}
					}
				});
				break;
			case "生活详情":
				setContentView(R.layout.fun_detail);
				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
				address = (TextView) findViewById(R.id.address);
				expense = (TextView) findViewById(R.id.expense);
				reputation = (TextView) findViewById(R.id.reputation);
				starttime = (TextView) findViewById(R.id.starttime);
				endtime = (TextView) findViewById(R.id.endtime);
				type = (TextView) findViewById(R.id.type);
				back = (ImageButton)findViewById(R.id.back);
				map = (Button)findViewById(R.id.map);
				title = (TextView)findViewById(R.id.title);
				comment = (Button)findViewById(R.id.comment);
				like = (ImageButton) findViewById(R.id.like);
				good = (ImageButton) findViewById(R.id.good);

				good_type = "生活";
				switch(bundle.getString("type"))
				{
					case "生活":
						toolbar_title.setText("生活详情");
						break;
					case "餐饮":
						toolbar_title.setText("餐饮详情");
						break;
					case "娱乐":
						toolbar_title.setText("娱乐详情");
						break;
				}
				title.setText(bundle.getString("title"));
				address.setText(bundle.getString("address"));
				expense.setText(bundle.getString("expense"));
				starttime.setText(bundle.getString("starttime"));
				endtime.setText(bundle.getString("endtime"));
				reputation.setText(bundle.getString("reputation"));

				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

				new SearchFavourTask().execute();

				like.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							if(exist == 1)
								new DeleteFavourTask().execute();
							else
								new AddNewFavourTask().execute();
						}
					}
				});

				good.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(gooded == 0)
							new SetGoodTask().execute();
						else
							Toast.makeText(ThirdActivity.this, "不可重复点赞", Toast.LENGTH_LONG).show();
					}
				});

				map.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle b = new Bundle();
						b.putString("title", bundle.getString("title"));
						Toast.makeText(ThirdActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
						Intent intent = new Intent(ThirdActivity.this, OverlayActivity.class);
						intent.putExtras(b);
						startActivity(intent);
					}
				});

				comment.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sharedPreferences.getString("name","").equals(""))
							Toast.makeText(ThirdActivity.this, "请先登录", Toast.LENGTH_LONG).show();
						else {
							Bundle b = new Bundle();
							b.putString("judge", "点评");
							b.putString("title", bundle.getString("title"));
							Toast.makeText(ThirdActivity.this, "点评", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
							intent.putExtras(b);
							startActivity(intent);
						}
					}
				});
				break;
			case "新用户注册":
				setContentView(R.layout.register);

				register = (Button) findViewById(R.id.register);
				name = (EditText) findViewById(R.id.name);
				password = (EditText) findViewById(R.id.password);
				password2 = (EditText) findViewById(R.id.password2);
				agree = (CheckBox) findViewById(R.id.agree);
				back = (ImageButton)findViewById(R.id.back);
				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);

				toolbar_title.setText(judge2);
				register.setClickable(false);

				register.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(agree.isChecked())
						{
							if(password.getText().toString().equals(password2.getText().toString()))
								new RegisterTask().execute();
							else
								Toast.makeText(ThirdActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();

						}
						else
							Toast.makeText(ThirdActivity.this, "请同意以上协议", Toast.LENGTH_LONG).show();
					}
				});
				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

				break;
			case "用户设置":
				setContentView(R.layout.option_item);

				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
				back = (ImageButton)findViewById(R.id.back);
				submit = (Button) findViewById(R.id.submit);
				text = (EditText) findViewById(R.id.text);

				toolbar_title.setText(bundle.getString("title"));
				submit.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new OptionTask().execute();
					}
				});
				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				break;
		}
	}

	class RegisterTask extends AsyncTask<String, String, String> {

		String sname,spassword;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sname = name.getText().toString();
			spassword = password.getText().toString();
			pDialog = new ProgressDialog(ThirdActivity.this);
			pDialog.setMessage("Registering....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", sname));
			params.add(new BasicNameValuePair("password", spassword));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_register,
					"GET", params);

			// check log cat fro response
			Log.d("Create user", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");

				if (success == 1) {
					judge = 1;
					finish();
				} else {
					judge = 0;
					// failed to create product
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
				Toast.makeText(ThirdActivity.this, "注册成功", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(ThirdActivity.this, "注册失败", Toast.LENGTH_LONG).show();

		}

	}

	class OptionTask extends AsyncTask<String, String, String> {

		String choose,change,user;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sharedPreferences= getSharedPreferences("test",
					Activity.MODE_PRIVATE);
			switch(bundle.getString("title"))
			{
				case "性别":
					choose = "sex";
					break;
				case "绑定手机号":
					choose = "phone";
					break;
				case "登录密码":
					choose = "password";
					break;
				case "用户名":
					choose = "name";
					break;
			}
			change = text.getText().toString();
			user = sharedPreferences.getString("name","");
			pDialog = new ProgressDialog(ThirdActivity.this);
			pDialog.setMessage("Changing options....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("choose", choose));
			params.add(new BasicNameValuePair("change", change));
			params.add(new BasicNameValuePair("user", user));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_option,
					"GET", params);

			// check log cat fro response
			Log.d("Options", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");

				if (success == 1) {
					judge = 1;
					finish();
				} else {
					judge = 0;
					// failed to create product
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
				Toast.makeText(ThirdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
				if(choose.equals("name")) {
					sharedPreferences = getSharedPreferences("test",
							Activity.MODE_PRIVATE);
					editor = sharedPreferences.edit();
					editor.putString("name", change);
					editor.commit();
				}
				finish();
			}
			else
				Toast.makeText(ThirdActivity.this, "修改失败", Toast.LENGTH_LONG).show();
		}
	}

	class AddNewFavourTask extends AsyncTask<String, String, String> {

		String user,merchant;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sharedPreferences= getSharedPreferences("test",
					Activity.MODE_PRIVATE);
			user = sharedPreferences.getString("name","");
			merchant = bundle.getString("title");
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user", user));
			params.add(new BasicNameValuePair("merchant", merchant));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_new_favour,
					"GET", params);

			// check log cat fro response
			Log.d("Create Favour", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");

				if (success == 1) {
					judge = 1;
				} else {
					judge = 0;
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			if(judge == 1)
			{
				like.setImageResource(R.drawable.like_clicked);
				exist = 1;
				Toast.makeText(ThirdActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
			}
			else
				Toast.makeText(ThirdActivity.this, "收藏失败", Toast.LENGTH_LONG).show();

		}

	}

	class SearchFavourTask extends AsyncTask<String, String, String> {

		String user,merchant;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sharedPreferences= getSharedPreferences("test",
					Activity.MODE_PRIVATE);
			user = sharedPreferences.getString("name","");
			Log.d("user",user);
			merchant = bundle.getString("title");
			Log.d("merchant",merchant);
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user", user));
			params.add(new BasicNameValuePair("merchant", merchant));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_search_favour,
					"GET", params);

			// check log cat fro response
			Log.d("Search Favour", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");

				if (success == 1) {
					exist = 1;
				} else {
					exist = 0;
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			Log.d("exist?",Integer.toString(exist));
			if(exist == 1)
			{
				Log.d("red",Integer.toString(exist));
				like.setImageResource(R.drawable.like_clicked);
			}
			else
			{
				Log.d("black",Integer.toString(exist));
				like.setImageResource(R.drawable.like);
			}
		}

	}

	class DeleteFavourTask extends AsyncTask<String, String, String> {

		String user,merchant;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			sharedPreferences= getSharedPreferences("test",
					Activity.MODE_PRIVATE);
			user = sharedPreferences.getString("name","");
			merchant = bundle.getString("title");
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user", user));
			params.add(new BasicNameValuePair("merchant", merchant));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_delete_favour,
					"GET", params);

			// check log cat fro response
			Log.d("Delete Favour", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");

				if (success == 1) {
					judge = 1;
				} else {
					judge = 0;
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			if(judge == 1)
			{
				like.setImageResource(R.drawable.like);
				exist = 0;
				Toast.makeText(ThirdActivity.this, "取消收藏", Toast.LENGTH_LONG).show();
			}
			else
				Toast.makeText(ThirdActivity.this, "取消失败", Toast.LENGTH_LONG).show();

		}

	}

	class SetGoodTask extends AsyncTask<String, String, String> {

		String merchant,type;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			merchant = bundle.getString("title");
			type = good_type;
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("merchant", merchant));
			params.add(new BasicNameValuePair("type", type));

			JSONObject json = jsonParser.makeHttpRequest(url_set_good,
					"GET", params);

			Log.d("Set good", json.toString());

			try {
				int success = json.getInt("success");

				if (success == 1) {
					judge = 1;
				} else {
					judge = 0;
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			if(judge == 1)
			{
				reputation.setText(Integer.toString(Integer.parseInt(reputation.getText().toString())+1));
				good.setBackgroundResource(R.drawable.good_clicked);
				gooded = 1;
				Toast.makeText(ThirdActivity.this, "点赞成功", Toast.LENGTH_LONG).show();
			}
			else
				Toast.makeText(ThirdActivity.this, "点赞失败", Toast.LENGTH_LONG).show();

		}

	}

}
