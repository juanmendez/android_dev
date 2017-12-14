package info.juanmendez.introfirebase.utils;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by juan on 12/8/17.
 */

public class BinderExt {

    @BindingAdapter("show")
    public static void showBindingAdapter(View view, Boolean visible){
        view.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    @BindingAdapter("itemSelected")
    public static void itemSelected(@NonNull LinearLayout linearLayout, Boolean isSelected ){
        linearLayout.setSelected( isSelected );
    }
}
