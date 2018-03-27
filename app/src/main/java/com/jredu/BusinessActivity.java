package com.jredu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BusinessActivity extends Activity{
	
	private TextView title;
	private ImageButton back;
	private Button map;
	private TextView toolbar_title;
	private Toolbar boolBar;
	Bundle bundle;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.businesse_information);
        bundle = getIntent().getExtras();

		boolBar = (Toolbar)findViewById(R.id.toolbar);
		toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
		toolbar_title.setText("商家详情");
        back = (ImageButton)findViewById(R.id.back);
        map = (Button)findViewById(R.id.map);
        title = (TextView)findViewById(R.id.title);
        
        title.setText(bundle.getString("title"));
        back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putString("title", bundle.getString("title"));
				Toast.makeText(BusinessActivity.this, bundle.getString("title"), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(BusinessActivity.this, OverlayActivity.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}
}
