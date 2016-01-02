package com.example.sqlbrite.todo.db;
import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

import javax.inject.Singleton;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

/**
 * Created by Juan on 12/26/2015.
 */
@Module(complete = false, library = true)
public final class DbModule {

    @Provides @Singleton
    SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbOpenHelper(application);
    }

    @Provides @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create(message -> Timber.tag("Database").v(message));
    }

    @Provides @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        return sqlBrite.wrapDatabaseHelper(helper);
    }
}
