package info.juanmendez.android.intentservice.ui;

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

import info.juanmendez.android.intentservice.R;
import info.juanmendez.android.intentservice.helper.NetworkUtil;
import info.juanmendez.android.intentservice.model.MagazineStatus;
import info.juanmendez.android.intentservice.model.pojo.Magazine;
import info.juanmendez.android.intentservice.service.download.MagazineDispatcher;

/**
 * Created by Juan on 8/1/2015.
 *
 * While trying to use MVP, I can tell it's not obligatory when the view has
 * a short logic which can simply stay like in this case.
 */
public class MagazineRow extends LinearLayout implements View.OnClickListener {
    TextView issueTextView;
    Button imageButton;
    Magazine magazine;

    @Inject
    MagazineDispatcher dispatcher;

    MagazineApp app;

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

        app = (MagazineApp) getContext().getApplicationContext();
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

        if( magazine.getStatus().equals(MagazineStatus.AVAILABLE) ) {
            magazine.setStatus(MagazineStatus.PENDING);
            imageButton.setText(magazine.getStatus());
            Iconify.addIcons(imageButton);
            dispatcher.setMagazine(magazine);
        }
        else
        if( magazine.getStatus().equals(MagazineStatus.DOWNLOADED ) || magazine.getStatus().equals(MagazineStatus.READ)){
            magazine.setStatus(MagazineStatus.READ);
            Iconify.addIcons(imageButton);
            dispatcher.setMagazine(magazine);
        }
    }
}