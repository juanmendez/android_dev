package info.juanmendez.learn.dagger2.github.services;

import retrofit2.Retrofit;

/**
 * Created by musta on 2/3/2017.
 */
public class GithubClientService {

    public GitHubClient createClient(Retrofit retrofit ){
        return retrofit.create(GitHubClient.class);
    }
}
