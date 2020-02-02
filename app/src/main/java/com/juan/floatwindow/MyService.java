package com.juan.floatwindow;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {
    public static final String ACTION = "action";
    public static final String SHOW = "show";
    public static final String HIDe = "hide";

    private FloatView floatView;

    @Override
    public void onCreate() {
        super.onCreate();
        floatView = new FloatView(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra(ACTION);
        if (SHOW.equals(action)){
            floatView.show();
        }else if(HIDe.equals(action)){
            floatView.hide();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
