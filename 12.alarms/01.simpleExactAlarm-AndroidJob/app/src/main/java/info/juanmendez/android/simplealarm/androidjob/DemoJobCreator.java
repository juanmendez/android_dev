package info.juanmendez.android.simplealarm.androidjob;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by Juan Mendez on 10/26/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class DemoJobCreator implements JobCreator{

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        if( tag.equals( DemoExactTimeJob.TAG )){
            return new DemoExactTimeJob();
        }
        return null;
    }
}
