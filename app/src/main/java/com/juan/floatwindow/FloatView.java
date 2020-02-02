package com.juan.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class FloatView{

    private Context mContext;
    private WindowManager.LayoutParams mParams;
    private FloatWindowManager floatWindowManager;

    private View mView;
    private long startTime;
    private long endTime;
    private float mTouchStartX, mTouchStartY;

    private boolean isClick = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // 获取相对屏幕的坐标，即以屏幕左上角为原点
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            //下面的这些事件，跟图标的移动无关，为了区分开拖动和点击事件
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    mTouchStartX = event.getX();
                    mTouchStartY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //图标移动的逻辑在这里
                    float mMoveStartX = event.getX();
                    float mMoveStartY = event.getY();
                    // 如果移动量大于3才移动
                    if (Math.abs(mTouchStartX - mMoveStartX) > 3
                            && Math.abs(mTouchStartY - mMoveStartY) > 3) {
                        // 更新浮动窗口位置参数
                        mParams.x = (int) (x - mTouchStartX);
                        mParams.y = (int) (y - mTouchStartY);
                        update();
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    endTime = System.currentTimeMillis();
                    //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                    isClick = !((endTime - startTime) > 0.1 * 1000L);
                    if (isClick){
                        //响应点击事件
                        Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
                        mView.performClick();
                    }
                    break;
            }
            return true;
        }
    };

    public FloatView(@NonNull final Context context) {
        this.mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.float_view_layout, null);
        ImageView imageView = mView.findViewById(R.id.test_img);
        floatWindowManager = FloatWindowManager.getInstance(context);
        mView.setOnTouchListener(mTouchListener);
    }



    public void show(){
        mParams = new WindowManager.LayoutParams();
        mParams.gravity = Gravity.START | Gravity.TOP;
        mParams.x = 0;
        mParams.y = 100;
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.width = 100;
        mParams.height = 100;
        floatWindowManager.addView(mView, mParams);
    }

    public void hide(){
        floatWindowManager.removeView(mView);
    }

    public void update(){
        floatWindowManager.updateView(mView, mParams);
    }

}
