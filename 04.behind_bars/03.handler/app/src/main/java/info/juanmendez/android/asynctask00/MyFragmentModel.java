package info.juanmendez.android.asynctask00;

import android.app.Fragment;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 5/13/2015.
 */
public class MyFragmentModel extends Fragment
{
    private String[] items= { "lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus" };

    private List<String> _list = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public List<String> getItems()
    {
        int loc = _list.size();
        List<String> subitems = new ArrayList<String>();

        for( int i = loc; i < items.length; i++ )
        {
            subitems.add( items[i]);
        }

        return subitems;
    }

    public List<String> getList()
    {
        return _list;
    }
}
