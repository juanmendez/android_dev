package com.flexnroses.android.services;
import android.os.Binder;

public class MyLocalBinder extends Binder
{
        MyService service;

        public MyLocalBinder( MyService _service )
        {
            service = _service;
        }

        MyService getService(){
            return service;
        }
}
