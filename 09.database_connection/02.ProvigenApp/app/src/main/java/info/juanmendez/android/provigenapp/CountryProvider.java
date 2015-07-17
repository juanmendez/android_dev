package info.juanmendez.android.provigenapp;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;

/**
 * Created by Juan on 6/30/2015.
 */
public class CountryProvider extends ProviGenProvider
{
    private static final Class[] contracts = new Class[]{CountryContract.class};
    public static final String AUTHORITY = "info.juanmendez.android.db.mycontentprovider/country";

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper( getContext(), "helper", null, 1, contracts);
    }

    @Override
    public Class[] contractClasses() {
        return contracts;
    }
}
