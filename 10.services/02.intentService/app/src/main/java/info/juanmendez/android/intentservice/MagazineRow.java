package info.juanmendez.android.intentservice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.android.iconify.Iconify;

import javax.inject.Inject;

import info.juanmendez.android.intentservice.model.Magazine;
import info.juanmendez.android.intentservice.model.MagazineAction;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.service.downloading.DownloadDispatcher;

/**
 * Created by Juan on 8/1/2015.
 */
public class MagazineRow extends LinearLayout implements View.OnClickListener {
    TextView issueTextView;
    Button imageButton;
    Magazine magazine;

    @Inject
    DownloadDispatcher dispatcher;

    public MagazineRow(Context context){
        this( context, null );
    }

    public MagazineRow(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public MagazineRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate( R.layout.magazine_row, this, true );
        setupChildren();
    }

    public void setupChildren(){

        issueTextView = (TextView) findViewById(R.id.issueTextView);
        imageButton = (Button) findViewById(R.id.imageButton);

        MagazineApp app = (MagazineApp) getContext().getApplicationContext();
        app.inject( this );
    }

    public void setItem( Magazine magazine ){

        this.magazine = magazine;
        imageButton.setText(magazine.getStatus());
        Iconify.addIcons(imageButton);

        imageButton.setOnClickListener(this);

        issueTextView.setText( magazine.getTitle() );
    }

    public static MagazineRow inflate( ViewGroup parent ){

        MagazineRow repoRow = new MagazineRow( parent.getContext() );
        return repoRow;
    }

    @Override
    public void onClick(View v) {
        magazine.setStatus( MagazineStatus.DOWNLOADED );
        imageButton.setText(magazine.getStatus());
        Iconify.addIcons(imageButton);

        dispatcher.setAction( new MagazineAction( magazine.getStatus() ) );
    }
}