package com.juan.floatwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FloatView floatView = new FloatView(this);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), MyService.class);
//                intent.putExtra(MyService.ACTION, MyService.SHOW);
//                startService(intent);
                floatView.show();
            }
        });

        findViewById(R.id.hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), MyService.class);
//                intent.putExtra(MyService.ACTION, MyService.HIDe);
//                startService(intent);
                floatView.hide();
            }
        });
    }
}
