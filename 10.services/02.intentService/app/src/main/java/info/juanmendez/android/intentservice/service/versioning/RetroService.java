package info.juanmendez.android.intentservice.service.versioning;

import info.juanmendez.android.intentservice.model.Version;
import retrofit.http.GET;
import retrofit.Callback;

/**
 * Created by Juan on 7/22/2015.
 */
public interface RetroService
{
    @GET("/development/android/latest.json")
    void getLatestVersion( Callback<Version> cb );


    @GET("/development/android/latest.json")
    Version getLatestVersion();
}
