package info.juanmendez.realminit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by Juan Mendez on 9/7/2016.
 * www.juanmendez.info
 * contact@juanmendez.info
 */
@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="src/main/AndroidManifest.xml", sdk = 21 )
public class TestModels {

    static{
        ShadowLog.stream = System.out;
    }

    @Before
    public void prep(){
    }

    @Test
    public void songParcelable(){


    }
}
