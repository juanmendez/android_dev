package info.juanmendez.timber;

import android.util.Log;

import timber.log.Timber;


/**
 * Created by Juan Mendez on 2/20/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class ReleaseTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {

        if( priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO ){
            return false;
        }

        return true;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

    }
}
