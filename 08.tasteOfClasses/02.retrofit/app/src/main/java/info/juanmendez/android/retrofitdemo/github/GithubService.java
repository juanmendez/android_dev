package info.juanmendez.android.retrofitdemo.github;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Juan on 7/6/2015.
 */
public interface GithubService {

    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<List<Repo>> cb);

}
