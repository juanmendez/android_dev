import android.content.ContentResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.ListMagazinesActivity;
import info.juanmendez.android.intentservice.MagazineApp;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class TestDownloadProxy
{
    private ContentResolver resolver;
    private ShadowContentResolver shadowResolver;
    private MagazineProvider provider;
    private MagazineApp app;

    static{
        ShadowLog.stream = System.out;
    }

    @Before
    public void prep(){

        app = (MagazineApp) RuntimeEnvironment.application;
        provider = new MagazineProvider();
        resolver = RuntimeEnvironment.application.getContentResolver();
        shadowResolver = shadowOf(resolver);
        provider.onCreate();
        ShadowContentResolver.registerProvider(MagazineProvider.AUTHORITY, provider);
    }

    @Test
    public void testProxyMock(){

        ActivityController controller = Robolectric.buildActivity(ListMagazinesActivity.class).create().start();
        ListMagazinesActivity activity = (ListMagazinesActivity) controller.get();
    }
}
