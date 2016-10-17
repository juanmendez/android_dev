package info.juanmendez.realminit.models;

/**
 * Created by Juan Mendez on 10/10/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class BandCom {

    public static final int READ = 0;
    public static final int DELETE = -1;
    public static final int ADD = 1;

    public static final int NONE = 0;
    public static final int NO = 1;
    public static final int YES = 2;
    public static final int COMPLETED = 3;


    private Band band;
    private int status;
    private int confirm = NONE;


    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public boolean isSameBand( Band band ){

        if( this.band == null )
            return false;

        return this.band.getId() == band.getId();
    }
}
