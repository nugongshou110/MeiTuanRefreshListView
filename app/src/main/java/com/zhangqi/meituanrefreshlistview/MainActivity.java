package com.zhangqi.meituanrefreshlistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.zhangqi.meituanrefreshlistview.MeiTuanListView.OnMeiTuanRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity implements OnMeiTuanRefreshListener{
	private MeiTuanListView mListView;
	private List<String> mDatas;
	private ArrayAdapter<String> mAdapter;
	private final static int REFRESH_COMPLETE = 0;

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mListView.setOnRefreshComplete();
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (MeiTuanListView) findViewById(R.id.listview);
		String[] data = new String[]{"hello world","hello world","hello world","hello world",
				"hello world","hello world","hello world","hello world","hello world",
				"hello world","hello world","hello world","hello world","hello world",};
		mDatas = new ArrayList<String>(Arrays.asList(data));
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mDatas);
		mListView.setAdapter(mAdapter);
		mListView.setOnMeiTuanRefreshListener(this);
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					mDatas.add(0, "new data");
					mHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}


}
