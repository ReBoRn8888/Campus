package com.jredu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MyFragment extends Fragment implements View.OnClickListener{

	public View view;
	public Intent intent;
	public Bundle b;

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
				ImageButton news = (ImageButton)view.findViewById(R.id.news);
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
				news.setOnClickListener(this);
				break;
			case "周边":
				view = inflater.inflate(R.layout.more, container,false);
				break;
			case "更多":
				view = inflater.inflate(R.layout.nearby, container,false);
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
			case R.id.lesson:
				b = new Bundle();
				b.putString("judge", "课程");
				Toast.makeText(getActivity(), "课程", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.fun:
				b = new Bundle();
				b.putString("judge", "娱乐");
				Toast.makeText(getActivity(), "娱乐", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
			case R.id.food:
			case R.id.life:
			case R.id.recommend:
			case R.id.good:
			case R.id.news:
				b = new Bundle();
				b.putString("judge", "推荐");
				Toast.makeText(getActivity(), "推荐", Toast.LENGTH_LONG).show();
				intent = new Intent(getActivity(), VariousActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				break;
		}
	}
}
