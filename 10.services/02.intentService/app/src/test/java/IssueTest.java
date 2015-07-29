import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.model.Issue;
import info.juanmendez.android.intentservice.model.IssueService;
import info.juanmendez.android.intentservice.service.versioning.RetroService;

/**
 * Created by Juan on 7/22/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", emulateSdk = 21 )
public class IssueTest {

    /**
     *
     */
    @Test
    public void testVolume(){
        RetroService service = IssueService.getService();

        Issue v =service.getLatestIssue();

        if( v != null )
        {
            Log.print( "issue " + v.getVolume() + ", " + v.getLocation() );
        }
    }
}
