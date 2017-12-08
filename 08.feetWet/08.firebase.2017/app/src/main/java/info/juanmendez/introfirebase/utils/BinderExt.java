package info.juanmendez.introfirebase.utils;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by juan on 12/8/17.
 */

public class BinderExt {

    @BindingAdapter("show")
    public static void showBindingAdapter(View view, Boolean visible){
        view.setVisibility(visible?View.VISIBLE:View.GONE);
    }
}
