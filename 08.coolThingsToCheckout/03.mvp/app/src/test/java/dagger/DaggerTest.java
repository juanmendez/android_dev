package dagger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.ui.MagazineApp;

/**
 * Created by Juan on 7/30/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21 )
public class DaggerTest {

    MagazineApp app;

    @Before
    public void runActivity(){
        app = (MagazineApp) RuntimeEnvironment.application;
    }

    @Test
    public void testListMagazine(){

    }
}
