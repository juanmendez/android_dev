package info.juanmendez.android.intentservice.service.downloading;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.model.MagazineAction;

/**
 * Created by Juan on 8/3/2015.
 */
public class DownloadDispatcher
{
    Bus bus;

    private MagazineAction action;

    @Inject
    public DownloadDispatcher( Bus bus) {
        this.bus = bus;
    }

    public MagazineAction getAction() {
        return action;
    }

    public void setAction(MagazineAction action) {
        this.action = action;
        bus.post( action );
    }

    @Produce
    public MagazineAction setAction(){
        return action;
    }
}
