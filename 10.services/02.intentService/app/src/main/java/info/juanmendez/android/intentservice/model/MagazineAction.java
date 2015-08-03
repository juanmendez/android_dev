package info.juanmendez.android.intentservice.model;

/**
 * Created by Juan on 8/3/2015.
 */
public class MagazineAction
{
    public MagazineAction( String action ){
        setAction( action );
    }

    private String action = MagazineStatus.NONE;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
