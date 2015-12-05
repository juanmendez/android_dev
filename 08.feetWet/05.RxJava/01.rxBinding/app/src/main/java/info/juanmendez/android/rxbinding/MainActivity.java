package info.juanmendez.android.rxbinding;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

@EActivity (R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    TextView textView;

    @ViewById
    EditText editText;

    @ViewById
    Button clearButton;

    @ViewById
    Button reverseButton;

    @AfterViews
    void afterViews(){

        //Only the original thread that created a view hierarchy can touch its views.
        RxTextView.textChanges( editText ).debounce( 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(charSequence -> {
            textView.setText(charSequence);
        });

        RxView.clicks(clearButton).subscribe(aVoid -> {
            editText.setText("");
        });

        RxView.clicks(reverseButton).subscribe(aVoid -> {
            editText.setText( new StringBuilder(textView.getText()).reverse());
        });
    }
}
