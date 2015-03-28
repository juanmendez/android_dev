package info.juanmendez.staticfragments.helpers;
import android.app.Activity;

public class TestActivity {

    public static ParentActivity contractPattern( Activity activity )
    {
        try{
            ParentActivity p = (ParentActivity) activity;

            return p;
        }
        catch( ClassCastException e )
        {
            throw new IllegalStateException( activity.getClass().getSimpleName() + " does not implement contract interface " + ParentActivity.class.getSimpleName() );
        }
    }
}