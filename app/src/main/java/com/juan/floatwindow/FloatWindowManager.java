package com.juan.floatwindow;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

class FloatWindowManager {
    private static final String TAG = "FloatWindowManager";

    private WindowManager mWindowManager;
    private static FloatWindowManager floatManager;
    private DisplayMetrics mMetrics = new DisplayMetrics();

    static FloatWindowManager getInstance(Context context){
        if (floatManager == null){
            synchronized (FloatWindowManager.class){
                if (floatManager == null){
                    floatManager = new FloatWindowManager(context);
                }
            }
        }
        return floatManager;
    }

    private FloatWindowManager(Context context){
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mWindowManager != null){
            mWindowManager.getDefaultDisplay().getMetrics(mMetrics);
        }
    }

    public void addView(View view, WindowManager.LayoutParams params){
        try {
            mWindowManager.addView(view, params);
        }catch (Exception e){
            Log.i(TAG, "add view error:" + e.getMessage());
        }
    }


    public void removeView(View view){
        try {
            mWindowManager.removeView(view);
        }catch (Exception e){
            Log.i(TAG, "remove view error:" + e.getMessage());
        }
    }


    public void updateView(View view, WindowManager.LayoutParams params){
        try {
            mWindowManager.updateViewLayout(view, params);
        }catch (Exception e){
            Log.i(TAG, "update view error:" + e.getMessage());
        }
    }

    public int getScreenWidthPixels(){
       return mMetrics.widthPixels;
    }

    public int getScreenHeightPixels(){
        return mMetrics.heightPixels;
    }

}
