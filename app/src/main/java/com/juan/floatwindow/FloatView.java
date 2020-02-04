package com.juan.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class FloatView extends FrameLayout {

    private Context mContext;
    private WindowManager.LayoutParams mParams;
    private FloatWindowManager floatWindowManager;

    private long startTime;
    private long endTime;
    private float mTouchStartX, mTouchStartY;
    private int screenWidth, screenHeight;

    public FloatView(@NonNull final Context context) {
        super(context);
        this.mContext = context;
        View mView = LayoutInflater.from(mContext).inflate(R.layout.float_view_layout, null);
        mView.findViewById(R.id.test_img).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "3333", Toast.LENGTH_SHORT).show();
            }
        });
        this.addView(mView);
        floatWindowManager = FloatWindowManager.getInstance(context);
        screenWidth = floatWindowManager.getScreenWidthPixels();
        screenHeight = floatWindowManager.getScreenHeightPixels();
    }



    public void show(){
        mParams = new WindowManager.LayoutParams();
        mParams.gravity = Gravity.START | Gravity.TOP;
        mParams.x = screenWidth;
        mParams.y = screenHeight - 200;
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        floatWindowManager.addView(this, mParams);
    }

    public void hide(){
        floatWindowManager.removeView(this);
    }

    public void update(){
        floatWindowManager.updateView(this, mParams);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标，屏幕左上角为原点
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

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
                if ((float) screenWidth / 2.0 < event.getRawX()) {
                    mParams.x = screenWidth - 100;
                } else {
                    mParams.x = 0;
                }
                update();
                endTime = System.currentTimeMillis();
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                boolean isClick = !((endTime - startTime) > 0.1 * 1000L);
                if (!isClick) {
                    // 非点击事件，直接消耗掉event
                    return true;
                }

                return super.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
