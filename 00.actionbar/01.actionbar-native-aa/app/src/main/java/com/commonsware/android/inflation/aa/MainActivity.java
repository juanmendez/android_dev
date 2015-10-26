package com.commonsware.android.inflation.aa;

import android.app.ActionBar;
import android.app.ListActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.SystemService;

import java.util.ArrayList;

@EActivity(R.layout.list_layout)
@OptionsMenu(R.menu.actions)
public class MainActivity extends ListActivity implements TextView.OnEditorActionListener{

    private static final String[] items= { "lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam",
            "vel", "erat", "placerat", "ante", "porttitor", "sodales",
            "pellentesque", "augue", "purus" };


    private ArrayList<String> words=null;
    
    private ArrayAdapter<String> adapter=null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        configureActionItem(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configureActionItem(Menu menu) {
        EditText add=
                (EditText)menu.findItem(R.id.add).getActionView()
                        .findViewById(R.id.title);

        add.setOnEditorActionListener(this);

    }
    
    @AfterViews
    void afterViews(){

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initAdapter();
    }

    @OptionsItem
    void reset()
    {
        initAdapter();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
            adapter.add(v.getText().toString());
            v.setText("");

            InputMethodManager imm=
                    (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        return(true);
    }

    private void initAdapter() {
        words=new ArrayList<String>();

        for (String s : items) {
            words.add(s);
        }

        adapter=
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        words);

        setListAdapter(adapter);
    }
}
