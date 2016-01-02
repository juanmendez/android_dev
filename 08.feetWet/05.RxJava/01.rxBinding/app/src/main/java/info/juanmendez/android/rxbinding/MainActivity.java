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

import rx.Subscription;
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

    //to prevent memory leak, it's usefull to unsubscribe on pause.
    //and if not using @Afterviews, then use instead onResume
    Subscription s1, s2, s3;

    @AfterViews
    void afterViews(){

        //Only the original thread that created a view hierarchy can touch its views.
        s1 = RxTextView.textChanges(editText).debounce(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(charSequence -> {
            textView.setText(charSequence);
        });

        s2 = RxView.clicks(clearButton).subscribe(aVoid -> {
            editText.setText("");
        });

        s3 = RxView.clicks(reverseButton).subscribe(aVoid -> {
            editText.setText( new StringBuilder(textView.getText()).reverse());
        });
    }


    @Override
    protected void onPause(){
        super.onPause();

        s1.unsubscribe();
        s2.unsubscribe();
        s3.unsubscribe();
    }
}
