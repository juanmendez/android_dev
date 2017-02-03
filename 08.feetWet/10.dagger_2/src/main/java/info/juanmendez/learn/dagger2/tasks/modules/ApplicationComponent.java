package info.juanmendez.learn.dagger2.tasks.modules;

import dagger.Component;
import info.juanmendez.learn.dagger2.tasks.views.MainView;
import info.juanmendez.learn.dagger2.tasks.views.TodoListView;

import javax.inject.Singleton;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MainView mainView);
    void inject(TodoListView todoListView);
}