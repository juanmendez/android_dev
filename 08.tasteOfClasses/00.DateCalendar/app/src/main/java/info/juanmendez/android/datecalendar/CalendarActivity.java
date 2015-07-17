package info.juanmendez.android.datecalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Calendar c = Calendar.getInstance();
        DateFormat f = formatter();
        c.set( Calendar.MONTH, 0 );
        c.set( 1970, 0, 1 );
        Logging.print("default date on Calendar: " + f.format(c.getTime()));

        c.roll( Calendar.MONTH, 40 );
        String dateString = f.format(c.getTime());
        Logging.print( "default date on Calendar: " +  dateString );

        //http://stackoverflow.com/questions/23132145/java-simpledateformat-for-yyyy-mm-ddthhmmsstzd
        String dateDemo = "2013-10-07T07:13:31Z";
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";


        //lets return the string to Date.. ;)
        try {
            f.parse( dateString );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static SimpleDateFormat formatter()
    {
        return new SimpleDateFormat("yyyy.MM.dd");
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Logging.print( "date modified " + year + ", " + monthOfYear + ", " + dayOfMonth );
    }
}
