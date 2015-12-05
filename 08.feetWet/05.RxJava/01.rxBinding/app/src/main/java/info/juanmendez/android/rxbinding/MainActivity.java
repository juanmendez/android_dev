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

        RxTextView.textChanges( editText ).subscribe(charSequence -> {
            textView.setText( charSequence );
        });

        RxView.clicks(clearButton).subscribe(aVoid -> {
            editText.setText("");
        });

        RxView.clicks(reverseButton).subscribe(aVoid -> {
            editText.setText( new StringBuilder(textView.getText()).reverse());
        });
    }
}
