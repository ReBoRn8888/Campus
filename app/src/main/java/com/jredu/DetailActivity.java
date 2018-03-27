package com.jredu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity{
	
	private TextView title;
	private ImageButton back;
	private Button map;
	private TextView toolbar_title,teacher;
	private Toolbar boolBar;
	Bundle bundle;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
		switch(bundle.getString("judge"))
		{
			case "商家详情":
				setContentView(R.layout.businesse_information);
				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
				back = (ImageButton)findViewById(R.id.back);
				map = (Button)findViewById(R.id.map);
				title = (TextView)findViewById(R.id.title);

                toolbar_title.setText(bundle.getString("judge"));
				title.setText(bundle.getString("title"));

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
						Toast.makeText(DetailActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
						Intent intent = new Intent(DetailActivity.this, OverlayActivity.class);
						intent.putExtras(b);
						startActivity(intent);
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

                toolbar_title.setText(bundle.getString("judge"));
                title.setText(bundle.getString("title"));
				teacher.setText(bundle.getString("teach"));

				back.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				break;
			case "娱乐详情":
				setContentView(R.layout.fun_detail);
				boolBar = (Toolbar)findViewById(R.id.toolbar);
				toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
				back = (ImageButton)findViewById(R.id.back);
				map = (Button)findViewById(R.id.map);
				title = (TextView)findViewById(R.id.title);

				toolbar_title.setText(bundle.getString("judge"));
				title.setText(bundle.getString("title"));

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
						Toast.makeText(DetailActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
						Intent intent = new Intent(DetailActivity.this, OverlayActivity.class);
						intent.putExtras(b);
						startActivity(intent);
					}
				});
				break;
		}
	}
}
