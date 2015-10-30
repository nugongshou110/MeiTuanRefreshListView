package com.zhangqi.meituanrefreshlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MeiTuanRefreshFirstStepView extends View{

	private Bitmap initialBitmap;
	private int measuredWidth;
	private int measuredHeight;
	private Bitmap endBitmap;
	private float mCurrentProgress;
	private Bitmap scaledBitmap;

	public MeiTuanRefreshFirstStepView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MeiTuanRefreshFirstStepView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MeiTuanRefreshFirstStepView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		initialBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_image));
		endBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_end_image_frame_05));
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),measureWidth(widthMeasureSpec)*endBitmap.getHeight()/endBitmap.getWidth());
	}
	
	private int measureWidth(int widMeasureSpec){
        int result = 0;
        int size = MeasureSpec.getSize(widMeasureSpec);
        int mode = MeasureSpec.getMode(widMeasureSpec);
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = endBitmap.getWidth();
            if (mode == MeasureSpec.AT_MOST){
                result = Math.min(result,size);
            }
        }
		return result;
        }
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		measuredWidth = getMeasuredWidth();
		measuredHeight = getMeasuredHeight();
		scaledBitmap = Bitmap.createScaledBitmap(initialBitmap, measuredWidth,measuredWidth*initialBitmap.getHeight()/initialBitmap.getWidth(), true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.scale(mCurrentProgress, mCurrentProgress, measuredWidth/2, measuredHeight/2);
		canvas.drawBitmap(scaledBitmap,0,measuredHeight/4,null);
		
	}
	
	public void setCurrentProgress(float currentProgress){
		mCurrentProgress = currentProgress;
	}
	
	
}
