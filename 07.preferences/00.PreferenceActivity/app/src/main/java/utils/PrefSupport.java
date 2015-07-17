package utils;

import android.preference.PreferenceActivity;

/**
 * Created by Juan on 6/17/2015.
 */
public class PrefSupport
{
    public static boolean isSinglePane( PreferenceActivity activity )
    {
        return activity.onIsHidingHeaders() || !activity.onIsMultiPane();
    }


}
