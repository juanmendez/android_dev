package info.juanmendez.android.intentservice.helper;

/**
 * Created by Juan on 8/20/2015.
 */
public class MVPUtils {

     public static <T> T getView( Object object, Class<T> t ){

        try{
            return (T) object;
        }catch( ClassCastException e ){

            throw new IllegalStateException( object.getClass().getSimpleName() +
                  t.getSimpleName() +  " does not implement contract interface" , e );
        }
    }

}
