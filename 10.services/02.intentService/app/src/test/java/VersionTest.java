import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.model.Version;
import info.juanmendez.android.intentservice.model.VersionService;
import info.juanmendez.android.intentservice.service.versioning.RetroService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Juan on 7/22/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class VersionTest {

    /**
     *
     */
    @Test
    public void testVersion(){
        RetroService service = VersionService.getService();

        Version v =service.getLatestVersion();

        if( v != null )
        {
            Log.print( "version " + v.getVersion() + ", " + v.getLocation() );
        }
    }
}
