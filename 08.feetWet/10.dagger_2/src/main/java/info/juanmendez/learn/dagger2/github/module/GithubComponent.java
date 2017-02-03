package info.juanmendez.learn.dagger2.github.module;

import dagger.Component;
import info.juanmendez.learn.dagger2.GithubApp;

import javax.inject.Singleton;

/**
 * Created by musta on 2/3/2017.
 */

@Singleton
@Component(modules={GithubModule.class})
public interface GithubComponent {
    void inject(GithubApp githubApp);
}
