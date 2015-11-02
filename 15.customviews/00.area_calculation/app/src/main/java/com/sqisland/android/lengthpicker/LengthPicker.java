package com.sqisland.android.lengthpicker;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import icepick.Icepick;
import icepick.Icicle;
import rx.Observable;
import rx.subjects.BehaviorSubject;

@EViewGroup(R.layout.length_picker)
public class LengthPicker extends LinearLayout
{
    BehaviorSubject<Integer> subject;

    @Icicle
    int mNumInches = 0;

    @ViewById(R.id.plus_button)
    View mPlusButton;

    @ViewById(R.id.text)
    TextView mTextView;

    @ViewById(R.id.minus_button)
    View mMinusButton;

    public LengthPicker(Context context) {
        super(context);
    }

    public LengthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LengthPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void afterViews(){
        setOrientation(LinearLayout.HORIZONTAL);
        subject = BehaviorSubject.create();
        updateControls();
    }

    @Click(R.id.plus_button)
    void onPlusButtonClick(){
        mNumInches++;
        updateControls();
    }

    @Click(R.id.minus_button)
    void onMinusButtonClick(){
        if (mNumInches > 0) {
            mNumInches--;
            updateControls();
        }
    }

    private void updateControls() {
        int feet = mNumInches / 12;
        int inches = mNumInches % 12;

        String text = String.format("%d' %d\"", feet, inches);

        if (feet == 0)
        {
            text = String.format("%d\"", inches);
        }
        else
        if (inches == 0)
        {
            text = String.format("%d'", feet);
        }

        mTextView.setText(text);
        mMinusButton.setEnabled(mNumInches > 0);
        subject.onNext(inches);
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        return Icepick.saveInstanceState(this, super.onSaveInstanceState() );
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(null);

        Icepick.restoreInstanceState( this, state );
        updateControls();
    }

    public Observable<Integer> asObservable(){
        return subject.asObservable();
    }
}