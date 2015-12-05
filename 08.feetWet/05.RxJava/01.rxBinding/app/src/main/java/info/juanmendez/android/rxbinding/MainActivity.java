package info.juanmendez.android.rxbinding;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity (R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.textView)
    TextView textView;

    @ViewById(R.id.editText)
    EditText editText;

    @AfterViews
    void afterViews(){
        RxTextView.textChanges( editText ).subscribe(charSequence -> {
            textView.setText( charSequence );
        });
    }
}
