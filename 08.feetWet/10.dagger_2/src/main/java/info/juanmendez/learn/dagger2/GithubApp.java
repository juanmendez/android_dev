package info.juanmendez.learn.dagger2;

import info.juanmendez.learn.dagger2.github.module.DaggerGithubComponent;
import info.juanmendez.learn.dagger2.github.module.GithubComponent;
import info.juanmendez.learn.dagger2.github.module.GithubModule;
import info.juanmendez.learn.dagger2.github.services.GitHubClient;
import info.juanmendez.learn.dagger2.github.models.GitHubUser;
import info.juanmendez.learn.dagger2.github.services.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;

/**
 * Created by musta on 2/2/2017.
 */
public class GithubApp {

    @Inject
    UserService userService;

    public static void main( String[] args ){
        new GithubApp();
    }

    public GithubApp() {
       GithubComponent component = DaggerGithubComponent.builder().githubModule(new GithubModule()).build();
       component.inject(this);

       userService.getUser();
    }

    private void testGettingTheUser(){
        GitHubClient client = getClient();


// Fetch a list of the Github repositories.
        Call<GitHubUser> call =
                client.profileForUser("juanmendez");

// Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<GitHubUser>() {

            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                System.out.println( response );
            }

            public void onFailure(Call<GitHubUser> call, Throwable t) {
                System.out.println( t.getMessage() );
            }
        });
    }

    private GitHubClient getClient(){
        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(GitHubClient.class);
    }
}
