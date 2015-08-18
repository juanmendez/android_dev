package info.juanmendez.android.intentservice.service.download;

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
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.BuildConfig;
import info.juanmendez.android.intentservice.ui.MagazineApp;
import info.juanmendez.android.intentservice.helper.MagazineUtil;
import info.juanmendez.android.intentservice.helper.PageUtil;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.pojo.Page;
import info.juanmendez.android.intentservice.service.provider.MagazineProvider;

/**
 * The DownloadService is in charge of downloading a zip and extracting
 * files to a specific directory.
 *
 * It's going to also notify ContentProvider for latest zip downloaded, and files extracted.
 */
public class DownloadService extends IntentService
{
    @Inject
    MagazineDispatcher dispatcher;
    public DownloadService()
    {
        super("download-zip");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        MagazineApp app = ((MagazineApp) getApplication());
        app.inject(this);

        ResultReceiver rec = intent.getParcelableExtra( "receiver" );
        Bundle bundle = new Bundle();
        bundle.putString( "message", "nothing happened");
        int result = Activity.RESULT_CANCELED;
        Magazine lastMagazine = dispatcher.getMagazine();

        if( lastMagazine != null )
        {
            File downloads = new File( getFilesDir(), "magazines" );
            downloads.mkdir();
            File target = new File( downloads, "target.zip" );


            if( download( target, MagazineUtil.getMagazineURL(app.getLocalhost(), lastMagazine))){
                File unzipDir = new File( getFilesDir(), "version_" + lastMagazine.getIssue());

                if( unzipDir.exists() )
                    unzipDir.delete();

                unzipDir.mkdir();

                int itemsModified = storeMagazine();

                if( itemsModified > 0 )
                {
                    List<Page> pages = unzip( target, unzipDir );

                    if( pages.size() > 0  ){

                        storePages( pages );
                        result = Activity.RESULT_OK;

                    }else{

                        bundle.putString("message", "couldn't unzip");
                    }
                }
                else{
                    bundle.putString("message", "couldn't store first magazine " + lastMagazine.getIssue() );
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

    private List<Page> unzip( File zip, File directory ){

        FileOutputStream fos = null;
        InputStream is = null;
        File entryDestination = null;
        ZipFile zipFile = null;
        Magazine lastMagazine = dispatcher.getMagazine();
        List<Page> pages = new ArrayList<Page>();

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
            File file;
            Page page;

            for( int i = 0; i < listOfFiles.length; i++ ){
                file = listOfFiles[i];

                if( !file.isDirectory()){
                    page = new Page();
                    page.setName( file.getName() );
                    page.setPosition(i);
                    page.setMagId(lastMagazine.getId());

                    pages.add( page );
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pages;
    }

    private int storeMagazine(){

        Magazine lastMagazine = dispatcher.getMagazine();
        Magazine cloneMagazine = MagazineUtil.clone(lastMagazine);
        cloneMagazine.setFileLocation(  "file://" + getFilesDir().getAbsolutePath() +  "/version_" + lastMagazine.getIssue()  );
        cloneMagazine.setStatus( MagazineStatus.DOWNLOADED );

        MagazineApp app = ((MagazineApp) getApplication());
        Uri magazineURI = Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider" + "/magazines/" + cloneMagazine.getId());

        ContentResolver cr = getContentResolver();
        ContentValues c = MagazineUtil.toContentValues(cloneMagazine);


        if( lastMagazine.getId() <= 0 )
        {
            Uri result = cr.insert( Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider" + "/magazines/"), c );

            if( MagazineProvider.uriMatcher.match(result) == MagazineProvider.SINGLE_MAGAZINE )
            {
                cloneMagazine.setId( Integer.parseInt(result.getLastPathSegment()));
                MagazineUtil.overwrite(cloneMagazine, lastMagazine);
                return 1;
            }
        }
        else
        {
            int itemsModified = cr.update(magazineURI, c, null, null);

            if( itemsModified > 0 )
            {
                MagazineUtil.overwrite(cloneMagazine, lastMagazine);
            }

            return itemsModified;
        }

        return 0;
    }

    private void storePages( List<Page> pages ){

        MagazineApp app = ((MagazineApp) getApplication());
        Uri pagesURI = Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".service.provider.MagazineProvider" + "/pages");
        ContentResolver cr = getContentResolver();
        ContentValues c;

        for( Page page: pages ){

            c = PageUtil.toContentValues(page);
            Uri result = cr.insert( pagesURI, c );

            if( result != null && MagazineProvider.uriMatcher.match(result) == MagazineProvider.SINGLE_PAGE  )
            {
                page.setId(Integer.parseInt(result.getLastPathSegment()));
            }
        }
    }
}