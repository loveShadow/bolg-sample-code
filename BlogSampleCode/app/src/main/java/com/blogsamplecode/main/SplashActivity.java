package com.blogsamplecode.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zhaoli on 2016/2/18.
 */
public class SplashActivity extends Activity {

    private final static TestFlag testFlag = TestFlag.TEST_NotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, testFlag.getActivityClass()));
        finish();
    }
}
