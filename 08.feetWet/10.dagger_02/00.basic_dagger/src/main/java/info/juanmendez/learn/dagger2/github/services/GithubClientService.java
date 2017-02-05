package info.juanmendez.learn.dagger2.github.services;

import retrofit2.Retrofit;

import javax.inject.Inject;

/**
 * Created by musta on 2/3/2017.
 */
public class GithubClientService {

    RetrofitService retrofitService;

    public GithubClientService( RetrofitService retrofitService ){
        this.retrofitService = retrofitService;
    }

    public GitHubClient createClient(){
        return retrofitService.getRetrofit().create(GitHubClient.class);
    }
}
