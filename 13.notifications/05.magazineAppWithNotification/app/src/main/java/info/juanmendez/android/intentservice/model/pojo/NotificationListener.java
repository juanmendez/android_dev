package info.juanmendez.android.intentservice.model.pojo;

/**
 * Created by Juan on 9/6/2015.
 */
public class NotificationListener
{
    private Object mListener;


    public void register( Object listener ){
        mListener = listener;
    }

    public void unregister( Object activity ){
        if( mListener == activity )
        {
            mListener = null;
        }
        else
        {
            new IllegalThreadStateException( "Notification Listener cannot unregister if activity is not first registered");
        }
    }

    public boolean hasListener(){
        return mListener != null;
    }

}
