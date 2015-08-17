package com.flexnroses.android.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;

/**
 * Created by Juan on 7/20/2015.
 */
public class HeadlessFragment extends Fragment
{
    private MyService myService;
    private Boolean bound = false;

    public MyService getMyService() {
        return myService;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Boolean getBound() {
        return bound;
    }

    public ServiceConnection getConnection() {
        return connection;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            myService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
            bound = false;
        }
    };
}
