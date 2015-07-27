package info.juanmendez.android.intentservice.service.downloading;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.xml.transform.Result;

/**
 * The DownloadService is in charge of downloading a zip and extracting
 * files to a specific directory.
 *
 * It's going to also notify ContentProvider for latest zip downloaded, and files extracted.
 */
public class DownloadService extends IntentService
{

    public DownloadService()
    {
        super("download-zip");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String zipURL = intent.getStringExtra( "zipUrl");
        float version = intent.getFloatExtra( "version", 0 );

        ResultReceiver rec = intent.getParcelableExtra( "receiver" );
        Bundle bundle = new Bundle();
        bundle.putString( "message", "nothing happened");
        int result = Activity.RESULT_CANCELED;

        if( zipURL != null && version > 0 ){

            File downloads = new File( getFilesDir(), "downloads" );

            downloads.mkdir();
            File target = new File( downloads, "target.zip" );
            URL url = null;

            try {
                url = new URL( zipURL );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if( download( target, url )){
                File unzipDir = new File( getFilesDir(), "download_" + version);

                if( unzipDir.exists() )
                    unzipDir.delete();

                unzipDir.mkdir();

                if( unzip( target, unzipDir ) ){
                    result = Activity.RESULT_OK;
                    bundle.putString("message", "zip was downloaded and decompressed!");
                    bundle.putString("directory", unzipDir.getAbsolutePath());

                }else{
                    result = Activity.RESULT_CANCELED;
                    bundle.putString("message", "couldn't unzip");
                }

            }else{
                result = Activity.RESULT_CANCELED;
                bundle.putString("message", "couldn't download");
            }
        }

        rec.send(result, bundle);
    }

    public void forceHandleIntent( Intent intent ){
        onHandleIntent( intent );
    }

    private Boolean download( File target, URL url ){

        FileOutputStream fos = null;
        InputStream is = null;

        if( target.exists() )
            target.delete();

        try {

            fos = new FileOutputStream( target.getPath() );
            is = url.openStream();
            url.openConnection();
            IOUtils.copy(is, fos);

            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(fos);

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Boolean unzip( File target, File unzipDir ){

        FileOutputStream fos = null;
        InputStream is = null;
        File entryDestination = null;
        ZipFile zipFile = null;

        //reading the zipEntry is appending the zip file name. We want to ignore that level.
        String pattern = "\\w+\\/(.*)";
        try{
            zipFile = new ZipFile( target );
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while( entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();

                entryDestination = new File( unzipDir, entry.getName().replaceAll( pattern, "$1") );

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
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}