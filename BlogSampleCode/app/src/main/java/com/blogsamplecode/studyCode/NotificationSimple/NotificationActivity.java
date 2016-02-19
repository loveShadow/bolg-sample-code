package com.blogsamplecode.studyCode.NotificationSimple;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RemoteViews;

import com.blogsamplecode.R;

/**
 * Created by zhaoli on 2016/2/18.
 */
public class NotificationActivity extends Activity implements View.OnClickListener{

    private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity_layout);

        findViewById(R.id.notification_normal_btn).setOnClickListener(this);
        findViewById(R.id.notification_custom_btn).setOnClickListener(this);

        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_normal_btn:
                sendNormalNotification();
                break;
            case R.id.notification_custom_btn:
                sendCustomNotification();
                break;
        }
    }

    private void sendNormalNotification() {
        //创建Notification.Builder
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置大图标（注：小米不支持 -- 会直接不显示Notification 慎用）
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        //设置Title
        builder.setContentTitle("这是Title");
        //设置内容
        builder.setContentText("这是一个正常的Notification");
        //设置时间
        builder.setWhen(System.currentTimeMillis());
        //设置是否显示时间（API 17）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //根据手机情况（有的手机不会显示,Ps:小米测试可以）
            builder.setShowWhen(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置颜色（API 21）（没测试过--）
            builder.setColor(Color.parseColor("#FF00FF"));
            //设置类别
            builder.setCategory("推荐");
        }

        builder.setAutoCancel(true);
        builder.setContentInfo("这是Info");
        //设置震动 [延迟0ms, 震动300ms, 延迟500ms, 震动700ms]
        builder.setVibrate(new long[]{0, 300, 500, 700});
        //设置灯光(需要 FLAG_SHOW_LIGHTS 标志) [灯光颜色, 亮的时间, 暗的时间]   --  注意：不是所有的设备都支持
        builder.setLights(0xFF0000FF, 2000, 10000);
        //设置提示音(此处使用默认音) -- 可以设置自己的声音
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //设置优先级(API >= 16)
        //依次为：PRIORITY_MAX --> PRIORITY_HIGH --> PRIORITY_DEFAULT --> PRIORITY_LOW --> PRIORITY_MIN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_MIN);
        }
        //设置为一个正在进行的通知(一般用于文件下载、同步操作、播放音乐等)
        builder.setOngoing(false);
        //设置Notification后声音震动均只执行一次(就是Notification还在的情况)
        builder.setOnlyAlertOnce(false);
        //点击后是否清除此Notification
        builder.setAutoCancel(true);
        //
        builder.setTicker("这是Ticker");

        /**
         * 注意：此处要兼容API
         */
        Notification notification = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.getNotification();
        } else {
            //build调用需要 API 16
            notification = builder.build();
        }

        //灯光标记
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        //发送Notification
        nm.notify(1, notification);
    }

    private int i = 1;

    private void sendCustomNotification() {
        //创建Notification.Builder
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        //创建RemoteView
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_custom_layout);
        //设置图片
        remoteView.setImageViewResource(R.id.notification_custom_layout_image, R.drawable.notification_custom_icon);
        remoteView.setTextViewText(R.id.notification_custom_layout_text, "这是一个自定义Notification");
        builder.setContent(remoteView);

        //不设置icon将显示不出来Notification
        builder.setSmallIcon(R.mipmap.ic_launcher);
        /**
         * 注意：此处要兼容API
         */
        Notification notification = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.getNotification();
        } else {
            //build调用需要 API 16
            notification = builder.build();
        }

        //发送Notification
        nm.notify(2, notification);
    }
}
