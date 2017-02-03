package info.juanmendez.learn.dagger2.github.module;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.learn.dagger2.github.models.GitConstants;
import info.juanmendez.learn.dagger2.github.services.GithubClientService;
import info.juanmendez.learn.dagger2.github.services.RetrofitService;
import info.juanmendez.learn.dagger2.github.services.UserService;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by musta on 2/3/2017.
 */
@Module
public class GithubModule {

    @Provides
    @Singleton
    @Named(GitConstants.Injection.URI.GITHUB_API )
    public String providesGitHubApi(){
        return "https://api.github.com/";
    }

    @Provides
    @Singleton
    public RetrofitService providesRetrofitService( @Named(GitConstants.Injection.URI.GITHUB_API ) String api){
        return new RetrofitService(api);
    }

    @Provides
    public GithubClientService providesGithutClientService(RetrofitService retrofitService){
        return new GithubClientService(retrofitService);
    }

    @Provides
    public UserService providesUserService( GithubClientService githubClientService ){
        return new UserService(githubClientService);
    }
}