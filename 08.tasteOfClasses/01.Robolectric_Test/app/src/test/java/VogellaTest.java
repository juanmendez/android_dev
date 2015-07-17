import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import info.juanmendez.android.test.robolectricdemo.BuildConfig;
import info.juanmendez.android.test.robolectricdemo.MainActivity;
import info.juanmendez.android.test.robolectricdemo.R;
import info.juanmendez.android.test.robolectricdemo.SecondActivity;

import static org.junit.Assert.*;
import org.robolectric.Shadows;

/**
 * Created by Juan on 6/26/2015.
 */

@RunWith(MyGradleTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml")
//@RunWith(RobolectricTestRunner.class)
//@Config(manifest="app/src/main/AndroidManifest.xml")
public class VogellaTest
{
    MainActivity activity;

    @Before
    public void setup()  {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create().start().resume().visible().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new MainActivity().getResources().getString(R.string.hello_world);
        print( hello );
    }

    /*
    @Test
    public void testButton() throws  Exception{

        Button clickButton = (Button) activity.findViewById(R.id.clickButton);
        clickButton.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals( SecondActivity.class.getCanonicalName(), intent.getComponent().getClassName() );

        ActivityController controller = Robolectric.buildActivity(SecondActivity.class).withIntent( intent).create();
        SecondActivity secondActivity = (SecondActivity) controller.get();
        assertNotNull(secondActivity);

        controller = controller.start().visible().resume().pause().stop().destroy();
        secondActivity = (SecondActivity) controller.get();
        clickButton = (Button) secondActivity.findViewById(R.id.clickButton);
        assertNotNull( clickButton );

    }*/

    public void print( String content )
    {
        System.out.println( content );
    }

}
