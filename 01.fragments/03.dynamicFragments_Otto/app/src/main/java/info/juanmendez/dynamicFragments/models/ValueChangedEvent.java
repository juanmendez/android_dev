package info.juanmendez.dynamicFragments.models;

import android.app.Fragment;

/**
 * Created by Juan on 3/28/2015.
 */
public class ValueChangedEvent {

    private int value = 0;
    private Object target;

    public ValueChangedEvent(int v, Object t )
    {
        target = t;
        value = v;
    }

    public Object getTarget()
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
