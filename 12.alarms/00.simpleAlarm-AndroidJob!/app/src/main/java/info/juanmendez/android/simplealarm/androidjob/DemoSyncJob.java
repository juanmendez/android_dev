package info.juanmendez.android.simplealarm.androidjob;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import timber.log.Timber;

import static info.juanmendez.android.simplealarm.MainActivity.AlarmBroadcaster.ALARM_BROADCAST_ACTION;

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
        getContext().sendBroadcast( new Intent(ALARM_BROADCAST_ACTION));
        return Result.SUCCESS;
    }

    /**
     * returns the id from the job created
     * @return
     */
    public static int scheduleSingleJob(@NonNull  PersistableBundleCompat extras ){

        return new JobRequest.Builder(TAG)
                .setExecutionWindow( 30_000L, 40_000L )
                .addExtras(extras)
                .build()
                .schedule();
    }

    public static int scheduleRepeatingJob( long interval, @NonNull PersistableBundleCompat extras ){

        return new JobRequest.Builder(TAG)
                .setPeriodic( interval, 5*60*1000L )
                .addExtras(extras)
                .build()
                .schedule();
    }
}
