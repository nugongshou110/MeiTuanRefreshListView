package com.zhangqi.meituanrefreshlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MeiTuanRefreshSecondStepView extends View{

	private Bitmap endBitmap;

	public MeiTuanRefreshSecondStepView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MeiTuanRefreshSecondStepView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MeiTuanRefreshSecondStepView(Context context) {
		super(context);
		init();
	}

	private void init() {
		endBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_end_image_frame_05));
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureWidth(widthMeasureSpec)*endBitmap.getHeight()/endBitmap.getWidth());
	}
	
	private int measureWidth(int widthMeasureSpec){
		int result = 0;
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		}else {
			result = endBitmap.getWidth();
			if (mode == MeasureSpec.AT_MOST) {
				result = Math.min(result, size);
			}
		}
		return result;
	}
}
