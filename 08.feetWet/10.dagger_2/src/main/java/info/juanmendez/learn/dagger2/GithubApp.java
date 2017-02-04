package info.juanmendez.learn.dagger2;

/**
 * Created by musta on 2/2/2017.
 */
public class GithubApp {

   /* @Inject
    UserService userService;*/

    public static void main( String[] args ){
        new GithubApp();
    }

    public GithubApp() {
       /*GithubComponent component = DaggerGithubComponent.builder().githubModule(new GithubModule()).build();
       component.inject(this);

       userService.getUser("juanmendez");*/
    }
}