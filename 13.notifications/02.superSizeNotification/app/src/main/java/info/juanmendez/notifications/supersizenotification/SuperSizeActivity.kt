package info.juanmendez.notifications.supersizenotification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

/**
 * A demo made in 2015, and updated in 2019.
 * @see https://guides.codepath.com/android/Notifications
 */
class SuperSizeActivity : AppCompatActivity() {
    companion object {
        const val NOTIFICATION_ID = 2019
        const val CHANNEL_ID = "CHANNEL_IN_2019"
        const val CHANNEL_ALIAS = "SUPERSIZE_ME_CHANNEL"
    }

    lateinit var manager: NotificationManager

    private val smallIcon: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                R.drawable.ic_notification_icon
            } else {
                R.mipmap.ic_launcher
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_size)

        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            setupChannel()

        tellMeAboutIt(intent)
    }

    public override fun onNewIntent(intent: Intent) {
        tellMeAboutIt(intent)
    }

    private fun tellMeAboutIt(intent: Intent) {
        manager.cancel(NOTIFICATION_ID)
        if (intent.getStringExtra("meal") != null) {

            Toast.makeText(this, intent.getStringExtra("meal"), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_super_size, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            superSizeMe()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun superSizeMe() {

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

        builder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setContentTitle("Here comes your big meal!")
            .setContentText("For 39 cents, can't beat that!")
            .setSmallIcon(smallIcon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.supersize))
            .setTicker("big and juicy burger on its way!")
            .addAction(android.R.drawable.ic_media_play, "play", createPendingIntent<SuperSizeActivity>())

        builder.setContentIntent(createPendingIntent(Uri.parse("https://en.wikipedia.org/wiki/Whopper")))
        builder.priority = Notification.PRIORITY_HIGH


        val big = NotificationCompat.InboxStyle(builder)
        big.addLine(
            "The Whopper was created in 1957 by Burger King co-founder James McLamore and originally sold for 37¢ (US$3.27 in 2014).")
            .addLine(
                "McLamore created the burger after he noticed that a rival restaurant was having success selling a larger burger.")
        big.setSummaryText("almighty whopper")

        manager.notify(NOTIFICATION_ID, big.build())

    }


    private fun createPendingIntent(uri: Uri): PendingIntent {
        val i = Intent(Intent.ACTION_VIEW, uri)

        return PendingIntent.getActivity(this, 0, i, 0)
    }


    private inline fun <reified T> createPendingIntent(): PendingIntent {
        val i = Intent(this, T::class.java)
        i.putExtra("meal", "double whopper with cheese and extra large fries and soda")

        return PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createPendingIntent(action: String): PendingIntent {
        val i = Intent(action)

        return PendingIntent.getActivity(this, 0, i, 0)
    }

    /**
     * For API level 26 and above, we need to create a notification channel,
     * provide a unique string id, name, and importance level.
     */
    @TargetApi(Build.VERSION_CODES.O)
    private fun setupChannel() {

        // Configure the channel
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ALIAS, importance)

        channel.description = getString(R.string.notification_description)

        // Register the channel with the notifications manager
        manager.createNotificationChannel(channel)
    }
}