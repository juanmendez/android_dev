package info.juanmendez.myawareness.service;

import android.content.Context;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

/**
 * Created by Juan Mendez on 1/26/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class AwarenessClient {

    Context context;
    GoogleApiClient client;

    @Inject
    public AwarenessClient( Context context  ){
        this.context = context;
    }

    public void connect(){

        client = new GoogleApiClient.Builder(this.context)
                .addApi(Awareness.API)
                .build();

        client.connect();
    }

    public void disconnect(){
        client.disconnect();
    }
}
