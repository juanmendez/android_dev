package info.juanmendez.learn.dagger2;

import info.juanmendez.learn.dagger2.tasks.modules.ApplicationComponent;
import info.juanmendez.learn.dagger2.tasks.modules.ApplicationModule;
import info.juanmendez.learn.dagger2.tasks.modules.DaggerApplicationComponent;
import info.juanmendez.learn.dagger2.tasks.views.MainView;

/**
 * Created by musta on 2/1/2017.
 */
public class Application {

    private static Application app;

    public static Application getApp() {
        return app;
    }

    ApplicationComponent applicationComponent;

    @SuppressWarnings("deprecation")
    public Application(){
        app = this;
        applicationComponent =  DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

        new MainView();
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

    public static void main(String[] args ){
        new Application();
    }
}
