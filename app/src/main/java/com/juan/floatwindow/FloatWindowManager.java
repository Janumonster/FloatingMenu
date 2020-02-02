package com.juan.floatwindow;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

class FloatWindowManager {
    private static final String TAG = "FloatWindowManager";

    private WindowManager windowManager;
    private static FloatWindowManager manager;

    static FloatWindowManager getInstance(Context context){
        if (manager == null){
            synchronized (FloatWindowManager.class){
                if (manager == null){
                    manager = new FloatWindowManager(context);
                }
            }
        }
        return manager;
    }

    private FloatWindowManager(Context context){
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void addView(View view, WindowManager.LayoutParams params){
        try {
            windowManager.addView(view, params);
        }catch (Exception e){
            Log.i(TAG, "add view error:" + e.getMessage());
        }
    }


    public void removeView(View view){
        try {
            windowManager.removeView(view);
        }catch (Exception e){
            Log.i(TAG, "remove view error:" + e.getMessage());
        }
    }


    public void updateView(View view, WindowManager.LayoutParams params){
        try {
            windowManager.updateViewLayout(view, params);
        }catch (Exception e){
            Log.i(TAG, "update view error:" + e.getMessage());
        }
    }

}
