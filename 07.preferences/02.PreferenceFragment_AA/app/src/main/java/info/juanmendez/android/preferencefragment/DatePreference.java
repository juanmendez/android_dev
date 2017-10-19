package info.juanmendez.android.preferencefragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Juan on 6/20/2015.
 */
public class DatePreference extends DialogPreference implements DatePicker.OnDateChangedListener
{
    private String dateString;
    private String clickedDate;
    private DatePicker datePicker;
    public static final DateFormat PREF_FORMATTER = new SimpleDateFormat("yyyy.MM.dd");
    public static final DateFormat SUMMARY_FORMATTER = new SimpleDateFormat("MMMM dd, yyyy");

    public DatePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView() {
        
        datePicker = new DatePicker( getContext() );
        Calendar calendar = getDate();

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);

        return datePicker;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        Calendar selected = new GregorianCalendar(year, month, day);
        this.clickedDate = PREF_FORMATTER.format(selected.getTime());
    }

    @Override
    protected void onDialogClosed(boolean shouldSave) {
        if (shouldSave && this.clickedDate != null) {
            updateDate(this.clickedDate);
            this.clickedDate = null;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        super.onClick(dialog, which);
        datePicker.clearFocus();
        onDateChanged(datePicker, datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        onDialogClosed(which == DialogInterface.BUTTON_NEGATIVE);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object def) {
        if (restoreValue) {
            this.dateString = getPersistedString(defaultValue());
            updateDate(this.dateString);
        } else {
            boolean wasNull = this.dateString == null;
            setDate((String) def);
            if (!wasNull)
                updateDate(this.dateString);
        }
    }

    private void updateDate(String s) {
        setDate(s);
        persistString(s);
        setSummary(SUMMARY_FORMATTER.format(getDate().getTime()));
    }

    private String defaultValue() {
        if (this.dateString == null)
            setDate(defaultCalendarString());
        return this.dateString;
    }

    public static String defaultCalendarString() {
       return PREF_FORMATTER.format(new Date().getTime());
    }

    public static Calendar defaultCalendar() {
        return new GregorianCalendar(1970, 0, 1);
    }

    public void setDate(String dateString) {
        this.dateString = dateString;
    }

    public Calendar getDate() {
        try {
            Date date = PREF_FORMATTER.parse(defaultValue());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (java.text.ParseException e) {
            return defaultCalendar();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        if (isPersistent())
            return super.onSaveInstanceState();
        else
            return new DateState(super.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state != null && state instanceof DateState ) {
            DateState s = (DateState) state;
            super.onRestoreInstanceState(s.getSuperState());
            updateDate(s.dateValue);
        } else {
           super.onRestoreInstanceState( state );
        }
    }

    private static class DateState extends BaseSavedState {
        String dateValue;

        public DateState(Parcel p) {
            super(p);
            dateValue = p.readString();
        }

        public DateState(Parcelable p) {
            super(p);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(dateValue);
        }
    }


    public static Calendar getDateFor(SharedPreferences preferences, String field) {
        Date date = stringToDate(preferences.getString(field,
                defaultCalendarString()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date stringToDate(String dateString) {
        try {
            return PREF_FORMATTER.parse(dateString);
        } catch (ParseException e) {
            return defaultCalendar().getTime();
        }
    }
}
