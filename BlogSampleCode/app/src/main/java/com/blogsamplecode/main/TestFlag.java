package com.blogsamplecode.main;

import com.blogsamplecode.studyCode.NotificationSimple.NotificationActivity;

/**
 * Created by zhaoli on 2016/2/18.
 */
public enum TestFlag {
    TEST_NotificationManager(NotificationActivity.class),        //NotificationManager测试
    TEST_AppWidgetManager(null),           //AppWidgetManager测试
    ;

    private Class activityClass = null;

    TestFlag(Class activityClass) {
        this.activityClass = activityClass;
    }

    public Class getActivityClass() {
        return activityClass;
    }
}
