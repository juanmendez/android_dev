package info.juanmendez.learn.dagger2.tasks.views;

import info.juanmendez.learn.dagger2.Application;
import info.juanmendez.learn.dagger2.github.services.UserService;
import info.juanmendez.learn.dagger2.tasks.services.TaskService;

import javax.inject.Inject;

/**
 * Created by musta on 2/2/2017.
 */
public class MainView {

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    private TodoListView todoListView;

    public MainView(){

        Application.getApp().getComponent().inject(this);
        userService.getUser( "juanmendez" );
        this.includeListView();
    }

    private void includeListView(){

        if( taskService.getList().length > 0 ){
            todoListView = new TodoListView();
        }
    }
}
