package info.juanmendez.android.intentservice.service.versioning;

import java.util.ArrayList;

import info.juanmendez.android.intentservice.model.Magazine;
import retrofit.http.GET;
import retrofit.Callback;

/**
 * Created by Juan on 7/22/2015.
 */
public interface RetroService
{
    @GET("/development/android/latest.json")
    void getLatestIssue(Callback<Magazine> cb);


    @GET("/development/android/latest.json")
    Magazine getLatestIssue();


    @GET("/development/android/list.json")
    void getIssues(Callback<ArrayList<Magazine>> cb);

    @GET("/development/android/list.json")
    ArrayList<Magazine> getIssues();
}
