package info.juanmendez.android.provigenapp;

import android.net.Uri;

import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

/**
 * Created by Juan on 6/30/2015.
 */
public interface CountryContract
{
    @ContentUri
    public static final Uri CONTENT_URI =  Uri.parse( "content://info.juanmendez.android.db.mycontentprovider/country");

    @Column(Column.Type.INTEGER)
    public static final String ID = "id";

    @Column((Column.Type.TEXT))
    public static final String NAME = "name";
}
