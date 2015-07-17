import android.app.Activity;
import android.content.UriMatcher;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.jar.Manifest;

import info.juanmendez.android.test.robolectricdemo.BuildConfig;
import info.juanmendez.android.test.robolectricdemo.MainActivity;
import info.juanmendez.android.test.robolectricdemo.R;
import static org.junit.Assert.*;

/**
 * Created by Juan on 6/24/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config( manifest = Config.NONE, emulateSdk = 21 )
//@Config(manifest="app/src/main/AndroidManifest.xml")
public class TestMainActivity {

    private static final int ALLROWS=1, SINGLE_ROW = 2, SEARCH = 3;
    private static final String SEARCHING = "searching";
    private static final UriMatcher uriMatcher;

    static{
        uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        uriMatcher.addURI( "com.paad.skeletondatabaseprovider", "elements", ALLROWS);
        uriMatcher.addURI( "com.paad.skeletondatabaseprovider", "elements#", SINGLE_ROW);
        uriMatcher.addURI( "com.paad.skeletondatabaseprovider", SEARCHING, SEARCH);
    }

    @Test
    public void editText() {
        Uri.Builder builder = new Uri.Builder();

        builder.authority("com.paad.skeletondatabaseprovider").appendPath("elements1");

        Uri path = builder.build();
        int result = uriMatcher.match(path);
        assertEquals( result, -1 );
    }
}
