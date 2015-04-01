package info.juanmendez.staticfragments.models;
import android.app.Fragment;

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

    @Override
    public String toString()
    {
        return "ValueChangedEvent("+value + ")";
    }
}
