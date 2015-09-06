package info.juanmendez.notifications.supersizenotification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Juan on 9/2/2015.
 */
public class WhopperService extends IntentService
{
    NotificationManager mgr;
    NotificationCompat.Builder b;
    public static final int id = 2015;

    public WhopperService() {
        super("WhopperService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        tellMeAboutIt(intent);

    }



    private void tellMeAboutIt( Intent intent ){

        if( intent.getStringExtra("url") != null ){

            b = new NotificationCompat.Builder(this);

            b.setAutoCancel(true);
            b.setTicker("downloading image")
                    .setContentTitle("Save your Whopper Wallpaper!!")
                    .setSmallIcon(R.drawable.whopper_icon);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                b.setPriority(Notification.PRIORITY_HIGH);


            File target = new File( getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "whopper" );
            target.mkdir();

            target = new File( target, "whopper.jpg" );


            if( target.exists() )
            {
                b.setContentText("Your wallpaper is broiling!!");
                b.setContentIntent(buildContentIntent(target));
                b.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBurgerBitmapped("https://lh3.googleusercontent.com/-EwzpKj41AW8/Vey1Ji4JV_I/AAAAAAAANvE/zHygDiSDI4A/h120/Hands-Free-Whopper.jpg"))
                        .setSummaryText("awesome download!!"));
                mgr.notify(2015, b.build());
            }
            else{
                Boolean downloaded = false;
                b.setOngoing(true);

                try {
                    downloaded = download( target, new URL(intent.getStringExtra("url")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                if( downloaded )
                {
                    b.setContentText("your wallpaper has been saved!")
                            .setProgress(0, 0, false).setOngoing(false);

                    b.setContentIntent(buildContentIntent(target));
                    b.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBurgerBitmapped(target)));

                    mgr.notify(2015, b.build());
                }
            }
        }
    }

    private Boolean download( File target, URL url ){

        //http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/
        byte data[] = new byte[1024];
        int total = 0;
        int count = -1;
        int progress = 0;

        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            int length = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 1024);
            OutputStream output = new FileOutputStream(target);

            while( (count = input.read(data)) != -1  ){
                total += count;
                output.write(data, 0, count);
                progress = ((total*100)/length);

                b.setProgress(100, progress, false);
                b.setContentText(Integer.toString(progress) + "%");
                mgr.notify(2015, b.build());
                SystemClock.sleep(10);
            }

            output.flush();
            output.close();
            input.close();

            return total > 0 && total == length;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private PendingIntent buildContentIntent( File file ) {
        Intent i=new Intent(getApplicationContext(), SuperSizeActivity.class);
        i.putExtra( "file", file.getAbsolutePath() );
        return(PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    private Bitmap getBurgerBitmapped(File file){

        /**
         * nice to know for url
         *
         * remote_picture = BitmapFactory.decodeStream((InputStream) new      URL(sample_url).getContent());
         */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions );

        return bitmap;
    }

    private Bitmap getBurgerBitmapped(String urlString){

        try {
            return BitmapFactory.decodeStream((InputStream) new URL(urlString).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
