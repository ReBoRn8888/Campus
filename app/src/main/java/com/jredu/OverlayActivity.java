package com.jredu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class OverlayActivity extends Activity implements OnClickListener {

	private MapView mMapView = null;
	private BaiduMap mBaiduMap;
	private LocationClient mLocClient,mLocationClient;
	private MyLocationListener myListener = new MyLocationListener();
	private LocationMode mCurrentMode;
	private BitmapDescriptor mCurrentMarker,bdA;
	private Bundle b;
	private LatLng llA;
	private TextView toolbar_title;
	private Toolbar boolBar;
	private ImageButton back;

	// UI相关
	private OnCheckedChangeListener radioButtonListener;
	private Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.map);

		back = (ImageButton)findViewById(R.id.back);
		boolBar = (Toolbar)findViewById(R.id.toolbar);
		toolbar_title = (TextView) boolBar.findViewById(R.id.toolbar_title);
		toolbar_title.setText("商家位置");
		back.setOnClickListener(this);

		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		b = getIntent().getExtras();
		switch(b.getString("title")){
			case "品客一佳":
				llA = new LatLng(31.041746, 121.455791);
				break;
			case "一品干锅":
				llA = new LatLng(31.041839, 121.455746);
				break;
			case "马记麻辣烫":
				llA = new LatLng(31.041792, 121.455764);
				break;
			case "河西食堂":
				llA = new LatLng(31.041003, 121.455863);
				break;
			case "稻香冒菜":
				llA = new LatLng(31.041885, 121.466247);
				break;
			case "华师大羽毛球馆":
				llA = new LatLng(31.040361, 121.460741);
				break;
			case "上海时代影城":
				llA = new LatLng(31.044662, 121.452091);
				break;
			default:
				llA = new LatLng(31.041885, 121.466247);
				break;
		}
		bdA = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		//添加覆盖物
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
				.zIndex(9).draggable(true);
		mBaiduMap.addOverlay(ooA);

		// 定位相关
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		//设定中心点坐标
		LatLng cenpt = new LatLng(31.041831, 121.455746);
		//定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder()
				.target(cenpt)
				.zoom(18)
				.build();
		//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		//改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);

		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				switch (mCurrentMode) {
					case NORMAL:
						requestLocButton.setText("跟随");
						mCurrentMode = LocationMode.FOLLOWING;
						mBaiduMap
								.setMyLocationConfigeration(new MyLocationConfiguration(
										mCurrentMode, true, mCurrentMarker));
						break;
					case COMPASS:
						requestLocButton.setText("普通");
						mCurrentMode = LocationMode.NORMAL;
						mBaiduMap
								.setMyLocationConfigeration(new MyLocationConfiguration(
										mCurrentMode, true, mCurrentMarker));
						break;
					case FOLLOWING:
						requestLocButton.setText("罗盘");
						mCurrentMode = LocationMode.COMPASS;
						mBaiduMap
								.setMyLocationConfigeration(new MyLocationConfiguration(
										mCurrentMode, true, mCurrentMarker));
						break;
				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);

		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
		radioButtonListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.defaulticon) {
					// 传入null则，恢复默认图标
					mCurrentMarker = null;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, null));
				}
				if (checkedId == R.id.customicon) {
					// 修改为自定义marker
					mCurrentMarker = BitmapDescriptorFactory
							.fromResource(R.drawable.icon_geo);
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
				}
			}
		};
		group.setOnCheckedChangeListener(radioButtonListener);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.back:
				finish();
				break;
		}
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}
		public void onReceivePoi(BDLocation poiLocation) {
		}
	}


	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
		// 回收 bitmap 资源
		bdA.recycle();
	}
}