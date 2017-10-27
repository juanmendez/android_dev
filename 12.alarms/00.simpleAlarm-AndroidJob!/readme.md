# [Evernote's Android-Job](https://github.com/evernote/android-job) demo

This is an extension from an older demo which showed a simple alarm.  So lets get ready to learn to use [AndroidJob](https://github.com/evernote/android-job).

From using `AlarmManager` when executed there is an option to set a `PendingIntent` to start an `ActivityResult`. This demo upon alarm execution sends a broadcast where `MainActivity` has registered to handle it through its dynamic `BroadcastReceiver`. It is quite fun to make old demos be revived with other alternatives!