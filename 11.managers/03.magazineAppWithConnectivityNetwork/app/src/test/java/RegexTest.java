import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.regex.Pattern;

import info.juanmendez.android.intentservice.BuildConfig;

/**
 * Created by Juan on 7/24/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21 )
public class RegexTest {

    @Test
    public void testRegex(){
        String fileName = "zippy/hello.html";
        String pattern = "zippy\\/(.*)";

       Log.print( fileName.replaceAll( pattern, "$1") );
    }
}
