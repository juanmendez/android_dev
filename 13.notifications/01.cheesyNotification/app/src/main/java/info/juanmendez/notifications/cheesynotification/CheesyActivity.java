package info.juanmendez.notifications.cheesynotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CheesyActivity extends AppCompatActivity {

    NotificationManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheesy);

        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meny_cheesy, menu);
        return true;
    }

    @Override
    public void onNewIntent(Intent intent){

        setIntent( intent );
        Toast.makeText( this, getIntent().getStringExtra("info.juanmendez.notifications.cheesynotification.Story"), Toast.LENGTH_LONG ).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            showNotification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showNotification(){

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);


        b.setContentTitle( "The cheesiest story ever told")
                .setContentText( "There were so many JS developers in this world, and Javascript became so famous, they felt bored and wanted to do staff in java")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("The day JS took over the Android world");


        Intent i = new Intent( this, CheesyActivity.class );
        i.putExtra("info.juanmendez.notifications.cheesynotification.Story", "The day JS took over the Android world");

        /**
         * http://stackoverflow.com/questions/18049352/notification-created-by-intentservice-uses-allways-a-wrong-intent/18049676#18049676
         */
        b.setContentIntent(PendingIntent.getActivity( this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT ));

        mgr.notify( 1, b.build() );

    }
}
