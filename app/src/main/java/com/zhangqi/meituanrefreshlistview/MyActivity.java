package com.zhangqi.meituanrefreshlistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

/**
 * Created by zhangqi on 15/11/1.
 */
public class MyActivity extends Activity {
    private MeiTuanRefreshFirstStepView mFirstView;
    private SeekBar mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mFirstView = (MeiTuanRefreshFirstStepView) findViewById(R.id.first_view);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //计算出当前seekBar滑动的比例结果为0到1
                float currentProgress = (float) i / (float) seekBar.getMax();
                //给我们的view设置当前进度值
                mFirstView.setCurrentProgress(currentProgress);
                //重画
                mFirstView.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
