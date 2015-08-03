package info.juanmendez.android.intentservice.service.downloading;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import info.juanmendez.android.intentservice.MagazineApp;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;
import info.juanmendez.android.intentservice.service.provider.SQLGlobals;
import info.juanmendez.android.intentservice.service.provider.SQLMagazine;
import info.juanmendez.android.intentservice.service.provider.SQLPage;

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
        float issue = intent.getFloatExtra( "version", 0 );

        ResultReceiver rec = intent.getParcelableExtra( "receiver" );
        Bundle bundle = new Bundle();
        bundle.putString( "message", "nothing happened");
        int result = Activity.RESULT_CANCELED;

        if( zipURL != null && issue > 0 ){

            File downloads = new File( getFilesDir(), "magazines" );
            downloads.mkdir();
            File target = new File( downloads, "target.zip" );
            URL url = null;

            try {
                url = new URL( zipURL );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if( download( target, url )){
                File unzipDir = new File( getFilesDir(), "version_" + issue);

                if( unzipDir.exists() )
                    unzipDir.delete();

                unzipDir.mkdir();

                List<String> files = unzip( target, unzipDir );

                if( files.size() > 0 ){

                    int lastMagazineID = storeMagazine( issue );

                    if( lastMagazineID >= 0 ){

                        storePages(files, lastMagazineID);
                        result = Activity.RESULT_OK;
                        bundle.putString("message", "zip was downloaded and decompressed!");
                        bundle.putInt(SQLMagazine.ID, lastMagazineID);
                        bundle.putFloat( SQLMagazine.ISSUE, issue);
                        bundle.putString( SQLMagazine.LOCATION, unzipDir.getAbsolutePath());
                    }

                    bundle.putString("message", "couldn't store first magazine " + issue );

                }else{

                    bundle.putString("message", "couldn't unzip");
                }

            }else{

                bundle.putString("message", "couldn't download");
            }
        }

        rec.send(result, bundle);
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

    private List<String> unzip( File zip, File directory ){

        FileOutputStream fos = null;
        InputStream is = null;
        File entryDestination = null;
        ZipFile zipFile = null;

        List<String> files = new ArrayList<String>();

        //reading the zipEntry is appending the zip file name. We want to ignore that level.
        String pattern = "\\w+\\/(.*)";
        try{
            zipFile = new ZipFile( zip );
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while( entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();

                entryDestination = new File( directory, entry.getName().replaceAll( pattern, "$1") );

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

            File[] listOfFiles = directory.listFiles();


            for( File file: listOfFiles ){

                if( !file.isDirectory()){
                    files.add( file.getName() );
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    private int storeMagazine( float version){
        Uri magazineURI = Uri.parse( "content://" + MagazineProvider.AUTHORITY + "/magazines" );

        ContentResolver cr = getContentResolver();
        ContentValues c = new ContentValues();
        c.put(SQLMagazine.ISSUE, version);
        c.put( SQLMagazine.DATETIME, SQLGlobals.dateFormat(new Date()) );
        c.put(SQLMagazine.LOCATION, "download_" + version );
        Uri lastInsert = cr.insert(magazineURI, c);
        return Integer.parseInt(lastInsert.getLastPathSegment());
    }

    private void storePages( List<String> files, int lastMagazineID ){
        Uri pagesURI = Uri.parse("content://" + MagazineProvider.AUTHORITY + "/pages");
        ContentResolver cr = getContentResolver();
        ContentValues c;
        for( String file: files ){

            c = new ContentValues();
            c.put(SQLPage.MAG_ID, lastMagazineID );
            c.put( SQLPage.NAME, file );
            cr.insert( pagesURI, c );
        }
    }
}