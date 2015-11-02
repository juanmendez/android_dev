package com.sqisland.android.lengthpicker;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.length_picker_right)
    LengthPicker rightPicker;

    @ViewById(R.id.length_picker_left)
    LengthPicker leftPicker;

    @ViewById(R.id.area)
    TextView mArea;

    @AfterViews
    void afterViews(){

        JoinObservables<Integer> joinObservables = new JoinObservables<>( leftPicker.asObservable(), rightPicker.asObservable() );

        joinObservables.asObservable().subscribe(integers -> {
            if (integers.size() >= 2) {
                int result = integers.get(0) * integers.get(1);
                mArea.setText(Integer.toString(result));
            }
        });
    }
}