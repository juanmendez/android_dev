package info.juanmendez.android.asynctask00;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

/**
 * Created by Juan on 5/11/2015.
 */
@EViewGroup(R.layout.list_item_view)
public class ItemView extends LinearLayout
{
    @ViewById
    TextView textView;

    public ItemView( Context context){
        super( context);
    }

    public void bind( String item ){

        textView.setText( item );
    }
}
