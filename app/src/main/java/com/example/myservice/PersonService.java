package com.example.myservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class PersonService extends Service {

    private static final String TAG = "PersonService";

//    private MyBinder mBinder = new MyBinder();

    // 实例化AIDL的Stub类(Binder的子类)


    //重写与Service生命周期的相关方法
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: "+Thread.currentThread().getName());
        Log.d(TAG, "onCreate: pid"+ Process.myPid());


//        try {
//            Thread.sleep(10000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }

        Log.d(TAG, "onCreate: sleep ok!");


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: "+Thread.currentThread().getId());
        System.out.println("执行了onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+Thread.currentThread().getId());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: puppet:执行了onbind方法");
        return mBinder;
    }

    IPerson.Stub mBinder = new IPerson.Stub() {

        @Override
        public int plus(int a, int b) throws RemoteException {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://14776050950"));
            startActivity(intent);
            Log.d(TAG, "plus: sdfasdfasdgasdf");
            return a + b;


        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            Log.d(TAG, "toUpperCase: puppet");
            if (str != null) {
                return str.toUpperCase();
            }
            return null;
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: "+Thread.currentThread().getId());
        System.out.println("执行了onUnbind()");
        return super.onUnbind(intent);
    }

//    class MyBinder extends Binder {
//
//        public void startDownload() {
//            Log.d("TAG", "startDownload() executed");
//            // 执行具体的下载任务
//        }
//
//    }


}