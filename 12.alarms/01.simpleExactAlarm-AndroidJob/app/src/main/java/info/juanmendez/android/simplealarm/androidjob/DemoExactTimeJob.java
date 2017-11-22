package info.juanmendez.android.simplealarm.androidjob;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import timber.log.Timber;

import static info.juanmendez.android.simplealarm.MainActivity.AlarmBroadcaster.ALARM_BROADCAST_ACTION;

/**
 * Created by Juan Mendez on 10/26/2017.
 * www.juanmendez.info
 * contact@juanmendez.info
 */

public class DemoExactTimeJob extends Job{
    public static final String TAG = "job_demo_tag";


    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        Timber.i( "Job is executed in here!");
        getContext().sendBroadcast( new Intent(ALARM_BROADCAST_ACTION));
        return Result.SUCCESS;
    }

    public static int scheduleJobAtAGivenTime( long timeFromNow, long timeFromNowLatest){

        Timber.i( "start scheduleJobAtAGivenTime");
        return new JobRequest.Builder(TAG)
                .setExecutionWindow( timeFromNow, timeFromNowLatest )
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
