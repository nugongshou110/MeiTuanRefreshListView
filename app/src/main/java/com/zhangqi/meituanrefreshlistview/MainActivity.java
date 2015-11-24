package com.zhangqi.meituanrefreshlistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.zhangqi.meituanrefreshlistview.MeiTuanListView.OnMeiTuanRefreshListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity implements OnMeiTuanRefreshListener{
	private MeiTuanListView mListView;
	private List<String> mDatas;
	private ArrayAdapter<String> mAdapter;
	private final static int REFRESH_COMPLETE = 0;
	/**
	 * mInterHandler是一个私有静态内部类继承自Handler，内部持有MainActivity的弱引用，
	 * 避免内存泄露
	 */
	private InterHandler mInterHandler = new InterHandler(this);

	private static class InterHandler extends Handler{
		private WeakReference<MainActivity> mActivity;
		public InterHandler(MainActivity activity){
			mActivity = new WeakReference<MainActivity>(activity);
		}
		@Override
		public void handleMessage(Message msg) {
			MainActivity activity = mActivity.get();
			if (activity != null) {
				switch (msg.what) {
					case REFRESH_COMPLETE:
							activity.mListView.setOnRefreshComplete();
							activity.mAdapter.notifyDataSetChanged();
							activity.mListView.setSelection(0);
						break;
				}
			}else{
				super.handleMessage(msg);
			}
		}
	}

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
					mInterHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}


}
