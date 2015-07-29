package info.juanmendez.android.intentservice.service.versioning;

import info.juanmendez.android.intentservice.model.Issue;
import retrofit.http.GET;
import retrofit.Callback;

/**
 * Created by Juan on 7/22/2015.
 */
public interface RetroService
{
    @GET("/development/android/latest.json")
    void getLatestIssue(Callback<Issue> cb);


    @GET("/development/android/latest.json")
    Issue getLatestIssue();
}
