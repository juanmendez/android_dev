package info.juanmendez.learn.dagger2.tasks.modules;

import dagger.Module;
import dagger.Provides;
import info.juanmendez.learn.dagger2.Application;
import info.juanmendez.learn.dagger2.tasks.services.TaskService;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule( Application application ){
            System.out.println( "Application Module created with " + application );
            this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    public TaskService provideTaskService(){
        return new TaskService();
    }
}
