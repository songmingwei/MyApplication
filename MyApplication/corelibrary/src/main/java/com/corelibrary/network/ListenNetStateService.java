package com.corelibrary.network;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.corelibrary.utils.L;
import com.corelibrary.utils.NetWorkUtils;

/**
 * 监听手机网络状态
 * Created by terry-song on 2016/10/13.
 */

public class ListenNetStateService extends Service {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
                    L.e("网络状态已经改变！");
                    NetWorkUtils.initNetStatus(context);
                }
            }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver,mFilter);
        L.e("网络状态--监听开始！");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        L.e("网络状态--坚挺结束！");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
