package info.juanmendez.learn.dagger2.github.services;

import info.juanmendez.learn.dagger2.github.models.GitHubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

/**
 * Created by musta on 2/3/2017.
 */
public class UserService {

    GithubClientService githubClientService;

    public UserService(GithubClientService githubClientService){
        this.githubClientService = githubClientService;
    }

    public void getUser( String user ){

        GitHubClient client = githubClientService.createClient();


// Fetch a list of the Github repositories.
        Call<GitHubUser> call =
                client.profileForUser(user);

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
}
