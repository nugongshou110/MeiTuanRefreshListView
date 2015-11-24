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
		//这个就是那个椭圆形图片
		initialBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_image));
		//这个是第二个状态娃娃的图片，之所以要这张图片，是因为第二个状态和第三个状态的图片的大小是一致的，而第一阶段
		//椭圆形图片的大小与第二阶段和第三阶段不一致，因此我们需要根据这张图片来决定第一张图片的宽高，来保证
		//第一阶段和第二、三阶段的View的宽高一致
		endBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_end_image_frame_05));
	}

	/**
	 * 重写onMeasure方法主要是设置wrap_content时 View的大小
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//根据设置的宽度来计算高度  设置为符合第二阶段娃娃图片的宽高比例
		setMeasuredDimension(measureWidth(widthMeasureSpec),measureWidth(widthMeasureSpec)*endBitmap.getHeight()/endBitmap.getWidth());
	}

	/**
	 * 当wrap_content的时候，宽度即为第二阶段娃娃图片的宽度
	 * @param widMeasureSpec
	 * @return
	 */
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

	/**
	 * 在onLayout里面获得测量后View的宽高
	 * @param changed
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		measuredWidth = getMeasuredWidth();
		measuredHeight = getMeasuredHeight();
		//根据第二阶段娃娃宽高  给椭圆形图片进行等比例的缩放
		scaledBitmap = Bitmap.createScaledBitmap(initialBitmap, measuredWidth,measuredWidth*initialBitmap.getHeight()/initialBitmap.getWidth(), true);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//这个方法是对画布进行缩放，从而达到椭圆形图片的缩放，第一个参数为宽度缩放比例，第二个参数为高度缩放比例，
		canvas.scale(mCurrentProgress, mCurrentProgress, measuredWidth/2, measuredHeight/2);
		//将等比例缩放后的椭圆形画在画布上面
		canvas.drawBitmap(scaledBitmap,0,measuredHeight/4,null);
		
	}

	/**
	 * 设置缩放比例，从0到1  0为最小 1为最大
	 * @param currentProgress
	 */
	public void setCurrentProgress(float currentProgress){
		mCurrentProgress = currentProgress;
	}
	
	
}
