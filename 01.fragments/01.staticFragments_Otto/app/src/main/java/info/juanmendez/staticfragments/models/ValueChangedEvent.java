package info.juanmendez.staticfragments.models;

import android.app.Fragment;

/**
 * Created by Juan on 3/28/2015.
 */
public class ValueChangedEvent {

    private int value = 0;
    private Fragment target;

    public ValueChangedEvent(int v, Fragment t )
    {
        target = t;
        value = v;
    }

    public Fragment getTarget()
    {
        return target;
    }

    public int getValue() {
        return value;
    }

    @Override public String toString()
    {
        return "ValueChangedEvent("+value + ")";
    }
}
