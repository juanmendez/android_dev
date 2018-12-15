package info.juanmendez.learn.dagger2.github.services;

import info.juanmendez.learn.dagger2.github.models.GitHubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {
    @GET("/users/{user}")
    Call<GitHubUser> profileForUser(
            @Path("user") String user
    );
}