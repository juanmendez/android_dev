package info.juanmendez.android.customloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.juanmendez.android.customloader.model.Owner;
import info.juanmendez.android.customloader.model.Repo;
import info.juanmendez.android.customloader.service.GithubService;
import info.juanmendez.android.customloader.service.Logging;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Juan on 7/17/2015.
 */
public class RepoActivity extends AppCompatActivity
{
    TextView ownerTextView;
    ImageButton ownerImageButton;
    WebView webView;
    Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        ownerTextView = (TextView) findViewById(R.id.ownerNameTextView);
        ownerImageButton = (ImageButton) findViewById(R.id.ownerImageButton );
        ownerImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        webView = (WebView) findViewById(R.id.webview);

        int repoid = getIntent().getIntExtra("repoId", -1);

        if(repoid>-1)
        {
            App app = ((App)getApplication());
            repo = app.getRepo( repoid );
            GithubService service = app.getGithubService();

            service.getOwner(repo.getOwner().getLogin(), new Callback<Owner>() {
                @Override
                public void success(Owner owner, Response response) {
                    if( owner != null )
                        fillOwner( owner );
                }

                @Override
                public void failure(RetrofitError error) {
                    Logging.print( "owner call failed " + error.getMessage() );
                }
            });

        }
    }

    private void fillOwner( Owner owner ){
        if( repo != null ){

            Picasso.with(this).load( owner.getAvatarUrl()).tag( this ).into(ownerImageButton);
            ownerTextView.setText( owner.getName());
            webView.loadUrl( repo.getHtmlUrl() );
        }
    }

}
