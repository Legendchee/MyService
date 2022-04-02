package com.example.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private IPerson mService;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IPerson.Stub.asInterface(service);

            Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
            Log.d(TAG, "onCreate: pid" + Process.myPid());

            try {
                int result = mService.plus(3, 5);
                String upperStr = mService.toUpperCase("hello world");
                Log.d("TAG", "result is " + result);
                Log.d("TAG", "upperStr is " + upperStr);
                Log.d(TAG, "onServiceConnected: ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
        Log.d(TAG, "onCreate: pid" + Process.myPid());
        Button button = (Button) findViewById(R.id.start_service);
        Button button1 = (Button) findViewById(R.id.start_service_bind);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonService.class);
                startService(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(MainActivity.this, PersonService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
            }
        });

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callMethodInService(v);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Button unbind = (Button) findViewById(R.id.unbind);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mService!=null) {
                    mService = null;
                    unbindService(connection);
                }
            }
        });

        Button destroy = (Button) findViewById(R.id.destroy);
        destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent = new Intent(MainActivity.this, PersonService.class);
                stopService(bindIntent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        Intent intent = new Intent(MainActivity.this, PersonService.class);
//        stopService(intent);
//        unbindService(connection);
        super.onDestroy();
    }


    /**
     * 调用服务里的方法
     *
     * @param view
     */
    public void callMethodInService(View view) throws RemoteException {

        Log.d(TAG, "RemoteService: puppet22222222222");
        if (mService != null) {
            int result = mService.plus(1, 2);
            Log.d("RemoteService", "调用了远程服务中的addition方法，结果为result=" + result);
        }

    }

}