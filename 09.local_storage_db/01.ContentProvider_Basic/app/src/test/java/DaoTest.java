import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import info.juanmendez.android.db.contentprovider.BuildConfig;
import info.juanmendez.android.db.mycontentprovider.Country;
import info.juanmendez.android.db.mycontentprovider.CountryDao;
import info.juanmendez.android.db.mycontentprovider.DaoMaster;
import info.juanmendez.android.db.mycontentprovider.DaoSession;

/**
 * Created by Juan on 7/2/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class, manifest=Config.NONE)
public class DaoTest {


    @Test
    public void test()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper( RuntimeEnvironment.application.getApplicationContext(), "helper", null );
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        CountryDao dao = daoSession.getCountryDao();

        Country country = new Country();
        country.setName("Guatemala");
        dao.insert( country );

        country = new Country();
        country.setName("El Salvador");
        dao.insert( country );

        country = new Country();
        country.setName("Honduras");
        dao.insert( country );

        country = new Country();
        country.setName("Nicaragua");
        dao.insert( country );

        country = new Country();
        country.setName("Costa Rica");
        dao.insert( country );

        country = new Country();
        country.setName("Panama");
        dao.insert( country );


        List<Country> countries = dao.queryBuilder().list();

        for( Country c : countries ){
            Log.print(c.getId() + " " + c.getName());
        }

    }
}
