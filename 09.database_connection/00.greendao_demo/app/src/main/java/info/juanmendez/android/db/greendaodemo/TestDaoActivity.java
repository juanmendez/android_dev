package info.juanmendez.android.db.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import de.greenrobot.dao.DaoException;
import de.greenrobot.daoexample.Country;
import de.greenrobot.daoexample.CountryDao;
import de.greenrobot.daoexample.DaoMaster;
import de.greenrobot.daoexample.DaoSession;
import de.greenrobot.daoexample.Link;
import de.greenrobot.daoexample.LinkDao;

/**
 * Created by Juan on 6/22/2015.
 */
public class TestDaoActivity extends AppCompatActivity
{
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private CountryDao countryDao;
    private LinkDao linkDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "country-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        countryDao = daoSession.getCountryDao();
        linkDao = daoSession.getLinkDao();

        Country country = new Country();

        try{
            country = countryDao.queryBuilder().where(CountryDao.Properties.Country.eq("United States") ).unique();
        }
        catch( DaoException e )
        {
            List<Country> deleteCountries = countryDao.queryBuilder().where( CountryDao.Properties.Country.eq("United States") ).list();

            for( Country deleteCountry:deleteCountries )
            {
                countryDao.delete( deleteCountry );
            }
        }
        catch( Exception e )
        {
            Logging.print( e.getMessage() );
        }

        if( country == null )
        {
            country = new Country();
            country.setCountry("United States");
            country.setFlag("Blue Red and White");
            countryDao.insert( country );
        }

        Long countryId = countryDao.getKey( country );

        //List<CountryURL> urls = countryURLDao.queryBuilder().where(CountryURLDao.Properties.CountryId.eq(countryId)).list();
        List<Link> links = country.getLinks();

        if( links.size() == 0 )
        {
            Logging.print( "Lets populate with urls");
            Link link = new Link();
            link.setCountryId(countryId);
            link.setUrl("https://es.wikipedia.org/?title=Estados_Unidos");
            linkDao.insert( link );

            link = new Link();
            link.setCountryId(countryId);
            link.setUrl("https://en.wikipedia.org/?title=United_States");
            linkDao.insert( link );

            link = new Link();
            link.setCountryId(countryId);
            link.setUrl("https://ca.wikipedia.org/?title=Estats_Units_d%27Am%C3%A8rica");
            linkDao.insert( link );

            country.resetLinks();
            links = country.getLinks();
        }


        Logging.print( "we can display the list of urls" );

        for( Link link:links )
        {
            Logging.print( "link.id " + link.getId() + "link.url " + link.getUrl() + "link.countryID " + link.getCountryId() );
        }
    }
}
