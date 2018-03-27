package com.jredu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavourFragment extends Fragment implements View.OnClickListener{

    public View view;
    public Intent intent;
    public Bundle b;
    private ListView list;
    private int[] imgIds = new int[]{R.drawable.taiwanlurou,R.drawable.xianglajitui,R.drawable.xiangguhuaji};
    private String[] content = new String[]{"台湾卤肉粒饭","香辣鸡腿饭","香菇滑鸡饭"};
    private int[] imgIds2 = new int[]{R.drawable.pinkstar,R.drawable.maji,R.drawable.yipin};
    private String[] content2 = new String[]{"品客一佳","马记麻辣烫","一品干锅"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String judge = getArguments().getString("para");

        switch (judge)
        {
            case "商品":
                view = inflater.inflate(R.layout.list, container,false);
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.nearby_business,
                        new String[]{"image","content"},
                        new int[]{R.id.listimage,R.id.listbutton});
                list = (ListView) view.findViewById(R.id.list);
                list.setAdapter(adapter);
                break;
            case "商家":
                view = inflater.inflate(R.layout.list, container,false);
                SimpleAdapter adapter2 = new SimpleAdapter(getActivity(),getData2(),R.layout.nearby_business,
                        new String[]{"image","content"},
                        new int[]{R.id.listimage,R.id.listbutton});
                list = (ListView) view.findViewById(R.id.list);
                list.setAdapter(adapter2);
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

        }
    }

    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<imgIds.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("image",imgIds[i]);
            item.put("content",content[i]);
            list.add(item);
        }
        return list;
    }

    private List<Map<String, Object>> getData2(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<imgIds2.length;i++){
            Map<String,Object>item = new HashMap<String,Object>();
            item.put("image",imgIds2[i]);
            item.put("content",content2[i]);
            list.add(item);
        }
        return list;
    }
}
