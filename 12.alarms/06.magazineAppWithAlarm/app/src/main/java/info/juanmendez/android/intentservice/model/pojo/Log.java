package info.juanmendez.android.intentservice.model.pojo;

import java.util.Date;

/**
 * Created by Juan on 8/24/2015.
 */
public class Log
{
    private Date lastMagazineCall;

    public Date getLastMagazineCall() {
        return lastMagazineCall;
    }

    public void tickLastMagazineCall() {
        this.lastMagazineCall = new Date();
    }

}
