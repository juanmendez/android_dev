package info.juanmendez.android.viewpager;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Juan on 4/28/2015.
 */
@EFragment(R.layout.fragment_first)
public class FirstFragment extends Fragment
{
    @FragmentArg
    int someInt;

    @FragmentArg
    String someTitle;

    @FragmentArg
    int someColor;

    public static FirstFragment newInstance( int page, String title, int color )
    {
        FirstFragment fragment = FirstFragment_.builder().someInt( page).someTitle( title ).someColor( color ).build();
        return fragment;
    }

    @ViewById
    TextView tvLabel;

    @AfterViews
    void afterViews()
    {
        tvLabel.setText( someInt + " -- " + someTitle );

        if( this.getView() != null )
        {
            ColorDrawable cd = new ColorDrawable( getActivity().getResources().getColor( someColor ));
            this.getView().setBackground(cd);
        }

    }

}
