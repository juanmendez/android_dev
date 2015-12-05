package info.juanmendez.android.hellorx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;


/**
 * Created by Juan on 9/18/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21)
public class AdvancedLambdaRequired {
    static{
        ShadowLog.stream = System.out;
    }

    @Test
    public void testFirstClassCitizen(){

        System.out.println( concatTranform("hello", "world", (s) -> {
            return s.toUpperCase();
        }));


        Function<String> transformToLower = (s)->{
            return s.toLowerCase();
        };

        System.out.println( concatTranform("hello", "world", transformToLower));
    }

    @Test
    public void hightOrderFunctions(){
        Supplier<String> supplier =  createCombineTransform( "hello", "world", (s) -> {
            return s.toUpperCase();
        });


        System.out.println( supplier.get() );
    }

    public static String concatTranform( String a, String b, Function<String> stringTransform ){

        if(stringTransform != null ){
            a = stringTransform.apply(a);
            b = stringTransform.apply(b);
        }

        return a + " " + b;
    }

    public static Supplier<String> createCombineTransform( final String a, final String b, final Function<String> transformer ){

        return ()->{
            String aa = a;
            String bb = b;

            if( transformer != null ){

                aa = transformer.apply(a);
                bb = transformer.apply(b);
            }

            return aa + " " + bb;
        };
    }


    public interface Supplier<T>{
        T get();
    }

    public interface Function<T> {
        T apply( T a );
    }
}