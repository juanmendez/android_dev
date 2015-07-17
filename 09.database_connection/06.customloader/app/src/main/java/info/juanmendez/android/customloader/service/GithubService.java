package info.juanmendez.android.customloader.service;

import java.util.ArrayList;
import java.util.Map;

import info.juanmendez.android.customloader.model.GithubSearch;
import info.juanmendez.android.customloader.model.Repo;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by Juan on 7/6/2015.
 */
public interface GithubService {

    @GET("/search/repositories")
    GithubSearch searchRepo(@QueryMap Map<String, String> options);
}
