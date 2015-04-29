package info.juanmendez.android.viewpager;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private Boolean isColorApplied = false;

    public static FirstFragment newInstance( int page, String title, int color )
    {
        FirstFragment fragment = FirstFragment_.builder().someInt( page).someTitle( title ).someColor( color ).build();
        return fragment;
    }

    @ViewById
    TextView tvLabel;

    private MainActivity mainActivity;

    @Override public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mainActivity = ((MainActivity) getActivity());
        mainActivity.inject(this);

        ColorDrawable cd = new ColorDrawable( mainActivity.getResources().getColor( someColor ));
        this.getView().setBackground(cd);
    }

    @AfterViews
    void afterViews()
    {
        tvLabel.setText( someInt + " -- " + someTitle );
    }

}
