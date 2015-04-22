package info.juanmendez.dynamicfragments.controllers.fragments;

import android.app.Fragment;
import android.os.Bundle;

import info.juanmendez.dynamicfragments.controllers.MainActivity;

/**
 * Created by Juan on 4/18/2015.
 */
public class TheFragment extends Fragment
{
    @Override
    public void onActivityCreated( Bundle savedInstanceState ){

        super.onActivityCreated( savedInstanceState );
        ((MainActivity) getActivity()).inject( ((TheFragment)this) );
    }
}
