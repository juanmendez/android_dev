package info.juanmendez.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Juan on 3/15/2015.
 */
public class StaticFragment extends Fragment{

    private EditText editText;

    @Override
    public void onAttach(Activity activity) {
        Log.i( MainActivity.tag, "onAttach" );
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i( MainActivity.tag, "onCreateView" );

        View theView = inflater.inflate( R.layout.fragment_layout, null );
        editText = (EditText) theView.findViewById(R.id.editText);

        return theView;
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {

        Log.i( MainActivity.tag, "onCreateView" );
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i( MainActivity.tag, "onViewCreated" );
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i( MainActivity.tag, "onActivityCreated" );
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i( MainActivity.tag, "onStart" );
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i( MainActivity.tag, "onResume" );
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i( MainActivity.tag, "onSaveInstanceState" );
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Log.i( MainActivity.tag, "onPause" );
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i( MainActivity.tag, "onStop" );
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i( MainActivity.tag, "onDestroyView" );
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i( MainActivity.tag, "onDestroy" );
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i( MainActivity.tag, "onDetach" );
        super.onDetach();
    }
}
