package info.juanmendez.android.customloader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.juanmendez.android.customloader.model.Repo;


/**
 * Created by Juan on 7/15/2015.
 */
public class RepoView extends LinearLayout
{
    ImageView imageView;
    TextView textView;

    public RepoView(Context context){
        this( context, null );
    }

    public RepoView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public RepoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate( R.layout.repo_row, this, true );
        setupChildren();
    }

    public void setupChildren(){
        imageView = (ImageView) this.findViewById(R.id.repo_image);
        imageView.setScaleType( ImageView.ScaleType.FIT_CENTER);
        textView = (TextView) findViewById(R.id.repo_text);
    }

    public void setItem( Repo repo ){
        Picasso.with(getContext()).load( repo.getOwner().getAvatarUrl()).tag( getContext() ).into(imageView);
        textView.setText( repo.getName() );
    }

    public static RepoView inflate( ViewGroup parent ){

        RepoView repoRow = new RepoView( parent.getContext() );
        return repoRow;
    }
}