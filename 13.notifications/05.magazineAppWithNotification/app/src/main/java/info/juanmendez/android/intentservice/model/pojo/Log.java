package info.juanmendez.android.intentservice.model.pojo;

import com.squareup.otto.Bus;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Juan on 8/24/2015.
 */
public class Log
{
    @Inject
    Bus bus;

    /**
     * INIT means the list has never been populated.
     * DIRTY means the database has updated magazines, and requires app's list magazine to update
     * CLEAN the list magazine attached to the app has the latest items from the database.
     */
    public enum Integer{
        INIT, CLEAN, DIRTY
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private Integer state = Integer.INIT;

}
