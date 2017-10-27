package info.juanmendez.android.simplealarm.androidjob;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import timber.log.Timber;

/**
 * Created by Juan Mendez on 10/26/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class DemoSyncJob extends Job{
    public static final String TAG = "job_demo_tag";


    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        Timber.i( "Job is executed in here!");
        return Result.SUCCESS;
    }
    
    public static int scheduleJob(){
        return new JobRequest.Builder(TAG)
                .setExecutionWindow( 30_000L, 40_000L )
                .build()
                .schedule();
    }
}
