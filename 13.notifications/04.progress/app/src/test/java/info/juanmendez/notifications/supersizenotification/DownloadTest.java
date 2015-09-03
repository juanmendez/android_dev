package info.juanmendez.notifications.supersizenotification;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static junit.framework.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml", sdk = 21 )
public class DownloadTest {

    static{
        ShadowLog.stream = System.out;
    }

    SuperSizeActivity activity;
    Context context;

    @Before
    public void buildActivity(){
        context = RuntimeEnvironment.application.getApplicationContext();
    }


    //@Test
    public void testService() throws Exception {

        activity = Robolectric.buildActivity(SuperSizeActivity.class).create().start().resume().visible().get();
        assertTrue( activity.isFinishing()==false);
        WhopperServiceMock service = new WhopperServiceMock();
        Intent i = new Intent(activity, SuperSizeActivity.class);
        i.putExtra( "url", "http://foodswol.com/wp-content/uploads/2014/04/whopper-good-image.jpg");
        service.onHandleIntent(i);
    }

    @Test
    public void testDownload(){
        File target = new File( context.getFilesDir(), "wallpaper" );
        target.mkdir();

        target = new File( target, "whopper.jpg" );

        try {
            Boolean downloaded = download( target, new URL("http://foodswol.com/wp-content/uploads/2014/04/whopper-good-image.jpg"));
            assertTrue("yes this works", downloaded);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public class WhopperServiceMock extends WhopperService{

        public void onHandleIntent( Intent intent ){
            super.onHandleIntent(intent);
        }
    }

    private Boolean download( File target, URL url ){

        //http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/
        byte data[] = new byte[1024];
        int total = 0;
        int count = -1;
        int progress = 0;

        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            int length = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 1024);
            OutputStream output = new FileOutputStream(target);

            while( (count = input.read(data)) != -1  ){
                Thread.sleep(100);
                total += count;
                output.write(data, 0, count);
                progress = ((total*100)/length);
                Log.print(progress);
            }

            output.flush();
            output.close();
            input.close();

            return total > 0 && total == length;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return false;
    }
}