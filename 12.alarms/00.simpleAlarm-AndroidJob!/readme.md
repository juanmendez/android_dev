# [Evernote's Android-Job](https://github.com/evernote/android-job) demo

This is an extension from an older demo which showed a simple alarm.  So lets get ready to learn to use [AndroidJob](https://github.com/evernote/android-job).

From using `AlarmManager` when executed there is an option to set a `PendingIntent` to start an `ActivityResult`. This demo upon alarm execution sends a broadcast where `MainActivity` has registered to handle it through its dynamic `BroadcastReceiver`. It is quite fun to make old demos be revived with other alternatives!

* `@MyApplication`. JobManager, root of [AndroidJob](https://github.com/evernote/android-job, requires to be created, and register a `JobCreator`
* `@DemoJobCreator`. Creator can be access and request different types of jobs.
* `@DemoPeriodicJob`. Has static methods which ease the request of jobs. Upon request we get in return each job id. 
    * Job-Ids can be used anywhere in our application to cancel a job.
    * In `DemoPeriodicJob` we can start a single job, or one periodically. Jobs run periodically must have `15min` as interval. We let our jobs to start anywhere no later than `5min`
    * Each `DemoPeriodJob` execution is running in a background thread. In this case we send a broadcast intended for our `MainActivity`
*  `@MainActivity` there is a menu option to start and cancel a period job. 
    * This activity during its lifecycle is registered to a dynamic `BroadcastReceiver` which is waiting to hear from `DemoPeriodicJob`

