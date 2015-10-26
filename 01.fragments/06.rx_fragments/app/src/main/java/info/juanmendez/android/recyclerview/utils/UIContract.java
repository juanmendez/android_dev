package info.juanmendez.android.recyclerview.utils;

/**
 * Created by Juan on 10/25/2015.
 */
public class UIContract
{
    public static <T> T getAsView( Object object, Class<T> t ){

        try{
            return (T) object;
        }catch( ClassCastException e ){

            throw new IllegalStateException( object.getClass().getSimpleName() +
                    t.getSimpleName() +  " does not implement contract interface" , e );
        }
    }

}
