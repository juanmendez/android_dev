import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.helper.DownloadProxy;

/**
 * Created by Juan on 7/30/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class DaggerTest {

    @Before
    public void runActivity(){

    }

    @Test
    public void testApp(){

         ObjectGraph objectGraph = ObjectGraph.create( new DaggerModule() );
         objectGraph.injectStatics();

    }

    public static class TestActivity{

        @Inject
        static DownloadProxy receiver;

    }


    @Module( staticInjections = {TestActivity.class}, complete = false, library = true)
    public class DaggerModule {

        @Provides
        DownloadProxy receiver(){
            return new DownloadProxy();
        }

    }
}
