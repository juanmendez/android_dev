package info.juanmendez.notifications.supersizenotification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class SuperSizeActivity extends AppCompatActivity implements View.OnLongClickListener {

    private ImageView imageView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_size);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnLongClickListener(this);
        tellMeAboutIt(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent){

        tellMeAboutIt(intent);
    }

    private void tellMeAboutIt( Intent intent ){

        if( intent.getStringExtra("file") != null ){
            path =  intent.getStringExtra("file");
            loadImage();
        }
    }

    private void loadImage()
    {
        if( path != null )
        {
            Picasso.with( this ).load( "file://" + path ).into(imageView);
            Toast.makeText(this, "Long press to set as wallpaper!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        path = savedInstanceState.getString("path");
        loadImage();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("path", path );
        super.onSaveInstanceState(outState);
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
        if (id == R.id.start_service) {

            Intent i = new Intent(this, WhopperService.class);
            i.putExtra("url", "http://ketchup/development/android/gallery_for_artists/whopper.jpg");
            startService(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Upon long click the user can save image as wallpaper.
     * This works if the file is in external storage. Otherwise it won't load an internal file.
     * Makes sense since you can uninstall your app.. :)
     *
     * @param v (imageView)
     * @return
     */
    @Override
    public boolean onLongClick(View v) {

        if( path != null && !path.isEmpty() ){

            Uri uri = Uri.fromFile( new File( path));
            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(uri, "image/jpeg");
            intent.putExtra("mimeType", "image/jpeg");

            //set as, should be a resource in production!
            startActivity(Intent.createChooser(intent, "Set as:"));
            return true;
        }


        return false;
    }
}
