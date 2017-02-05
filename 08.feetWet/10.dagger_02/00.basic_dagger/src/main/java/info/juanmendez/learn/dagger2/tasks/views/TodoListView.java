package info.juanmendez.learn.dagger2.tasks.views;

import info.juanmendez.learn.dagger2.Application;
import info.juanmendez.learn.dagger2.tasks.services.TaskService;

import javax.inject.Inject;

/**
 * Created by musta on 2/2/2017.
 */
public class TodoListView {
    @Inject
    TaskService service;

    public TodoListView() {

        Application.getApp().getComponent().inject(this);
        this.displayList();
    }

    private void displayList(){
        for (String task : service.getList()) {
            System.out.println( task );
        }
    }
}
