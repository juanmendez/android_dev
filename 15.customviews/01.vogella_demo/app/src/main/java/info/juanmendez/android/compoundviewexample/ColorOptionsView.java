package info.juanmendez.android.compoundviewexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Juan on 11/3/2015.
 */

@EViewGroup(R.layout.view_color_options)
public class ColorOptionsView extends LinearLayout {

    @ViewById(R.id.textview)
    TextView title;

    @ViewById(R.id.view)
    View mValue;

    @ViewById(R.id.imageview)
    ImageView mImage;

    private TypedArray typeArray;

    public ColorOptionsView(Context context) {
        super(context);
    }

    public ColorOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        typeArray = context.obtainStyledAttributes(attrs, R.styleable.Options );
    }

    public ColorOptionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typeArray = context.obtainStyledAttributes(attrs, R.styleable.Options );
    }

    @AfterViews
    void afterViews()
    {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        if( typeArray != null )
        {
            String titleText = typeArray.getString(R.styleable.Options_titleText);

            int valueColor = typeArray.getColor(R.styleable.Options_valueColor, Color.BLUE);
            typeArray.recycle();

            title.setText(titleText);
            mValue.setBackgroundColor(valueColor);
        }
    }

    public void setValueColor(int color) {
        mValue.setBackgroundColor(color);
    }

    public void setImageVisible(boolean visible) {
        mImage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
