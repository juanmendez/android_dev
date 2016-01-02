package com.example.sqlbrite.todo;

import android.app.Application;

import com.example.sqlbrite.todo.db.DbModule;
import com.example.sqlbrite.todo.ui.UiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Juan on 12/26/2015.
 */

@Module(
        includes = {
                DbModule.class,
                UiModule.class
        }
)
public final class TodoModule {
    private final Application application;

    TodoModule(Application application ){
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
