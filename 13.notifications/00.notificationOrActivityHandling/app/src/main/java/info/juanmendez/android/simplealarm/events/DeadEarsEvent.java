package info.juanmendez.android.simplealarm.events;

/**
 * Created by Juan on 8/16/2015.
 */
public class DeadEarsEvent
{
    private Boolean deadEars = true;

    public DeadEarsEvent(Boolean deadEars) {
        this.deadEars = deadEars;
    }
    public Boolean getDeadEars() {
        return deadEars;
    }

    public void setDeadEars(Boolean deadEars) {
        this.deadEars = deadEars;
    }
}
