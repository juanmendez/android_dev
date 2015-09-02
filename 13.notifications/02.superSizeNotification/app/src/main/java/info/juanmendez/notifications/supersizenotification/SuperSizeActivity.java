package info.juanmendez.notifications.supersizenotification;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SuperSizeActivity extends AppCompatActivity {

    NotificationManager mgr;
    private final int id = 2015;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_size);

        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        tellMeAboutIt(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent){
        tellMeAboutIt(intent);
    }

    private void tellMeAboutIt( Intent intent ){
        mgr.cancel(id);
        if( intent.getStringExtra("meal") != null ){

            Toast.makeText(this, intent.getStringExtra("meal"), Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_super_size, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            superSizeMe();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void superSizeMe() {

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentTitle("Here comes your big meal!")
                .setContentText("For 39 cents, can't beat that!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.supersize))
                .setTicker("big and juicy burger on its way!")
                .addAction(android.R.drawable.ic_media_play, "play", createPendingIntent(SuperSizeActivity.class));

        b.setContentIntent(createPendingIntent(Uri.parse("https://en.wikipedia.org/wiki/Whopper")));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            b.setPriority(Notification.PRIORITY_HIGH);


        NotificationCompat.InboxStyle big = new NotificationCompat.InboxStyle(b);
        big.addLine("The Whopper was created in 1957 by Burger King co-founder James McLamore and originally sold for 37¢ (US$3.27 in 2014).")
                .addLine("McLamore created the burger after he noticed that a rival restaurant was having success selling a larger burger.");
        big.setSummaryText("almigty wopper");

        mgr.notify(id, big.build());

    }


    private PendingIntent createPendingIntent( Uri uri ){
        Intent i = new Intent( Intent.ACTION_VIEW, uri );

        PendingIntent p = PendingIntent.getActivity( this, 0, i, 0 );
        return p;
    }


    private PendingIntent createPendingIntent( Class c ){
        Intent i = new Intent( this, c  );
        i.putExtra( "meal", "double whopper with cheese and extra large fries and soda");
        PendingIntent p = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );

        return p;
    }

    private PendingIntent createPendingIntent( String action ){
        Intent i = new Intent( action  );
        PendingIntent p = PendingIntent.getActivity(this, 0, i, 0 );

        return p;
    }
}