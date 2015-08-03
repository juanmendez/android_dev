import android.content.Context;
import android.content.Intent;

import org.apache.commons.io.IOUtils;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.MainActivity;
import info.juanmendez.android.intentservice.service.downloading.DownloadService;

/**
 * Created by Juan on 7/22/2015.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest="app/src/main/AndroidManifest.xml" )
public class FiledownloadTest {

    static{
        ShadowLog.stream = System.out;
    }

    private MainActivity activity;
    private ActivityController controller;

    @Before
    public void buildActivity(){
        controller = Robolectric.buildActivity( MainActivity.class ).create().start().resume().visible();
        activity = (MainActivity) controller.get();

        System.out.println( "hello world");
    }

    /**
     * lets test downloadservice with activity
     */

    @Test
    public void testDownloadService(){

        /*
        UIReceiver downloadReceiver = new UIReceiver( new Handler() );

        downloadReceiver.setCallback(new UIReceiver.UiCallback() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.print("Received.. " + Integer.toString(resultCode) + " " + resultData.getString("message", "hello"));
            }
        });

        Intent i = new Intent( RuntimeEnvironment.application, DownloadService.class );
        i.putExtra( "receiver", downloadReceiver );

        DownloadServiceMock service = new DownloadServiceMock();
        service.onHandleIntent( i );*/
    }

    public class DownloadServiceMock extends DownloadService{

        @Override
        public void onHandleIntent( Intent intent ){
            super.onHandleIntent(intent);
        }
    }

    //@Test
    public  void testDownloading()
    {
        Context context = RuntimeEnvironment.application.getApplicationContext();

        FileOutputStream fos = null;
        InputStream is = null;
        File downloads = new File( context.getFilesDir(), "downloads" );
        downloads.mkdir();

        File unzipDir = new File( context.getFilesDir(), "zip_1");
        unzipDir.mkdir();

        File[] listOfFiles = unzipDir.listFiles();

        for( File f: listOfFiles ){

            f.delete();
        }

        File target = new File( downloads, "zippy.zip" );

        if( target.exists() )
            target.delete();

        try {
            URL url = new URL( "http://ketchup/development/android/zippy.zip" );
            fos = new FileOutputStream( target.getPath() );
            is = url.openStream();
            url.openConnection();
            IOUtils.copy( is, fos );

            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(fos);

            ZipFile zipFile = new ZipFile( target );

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while( entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File( unzipDir, entry.getName() );

                if( entry.isDirectory() ){
                    entryDestination.mkdir();
                }else{
                     is = zipFile.getInputStream( entry );
                     fos = new FileOutputStream( entryDestination );

                    IOUtils.copy( is, fos );
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly( fos );
                }
            }

            zipFile.close();

            listOfFiles = unzipDir.listFiles();

            for( File f: listOfFiles ){

                Log.print( f.getName() );
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
