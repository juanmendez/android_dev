package info.juanmendez.android.intentservice.service.downloading;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.model.Magazine;

/**
 * Created by Juan on 8/3/2015.
 */
public class MagazineDispatcher
{
    Bus bus;

    private Magazine magazine = new Magazine();

    @Inject
    public MagazineDispatcher(Bus bus) {
        this.bus = bus;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
        bus.post(magazine);
    }

    @Produce
    public Magazine getMagazine(){
        return magazine;
    }
}
