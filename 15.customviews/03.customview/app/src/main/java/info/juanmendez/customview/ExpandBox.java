package info.juanmendez.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Juan on 11/22/2015.
 */
public class ExpandBox extends Box implements View.OnClickListener {
    ExpandStyles expandStyles = new ExpandStyles();
    private ValueAnimator mCurrentAnimator;

    public ExpandBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ExpandBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandBox(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        this.setOnClickListener( this );
        TypedArray expandAttrs = context.obtainStyledAttributes(attrs, R.styleable.ExpandStyle);
        expandStyles.setMaxWidth(expandAttrs.getDimensionPixelSize(R.styleable.ExpandStyle_max_width, expandStyles.getMaxWidth() ) );
        expandStyles.setMaxHeight( expandAttrs.getDimensionPixelSize(R.styleable.ExpandStyle_max_height, expandStyles.getMaxHeight() ) );
    }

    @Override
    public void onClick(View v) {

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        if( expandStyles.getInitWidth() == 0  )
            expandStyles.setInitWidth( getMeasuredWidth() );

        if( expandStyles.getInitHeight() == 0 )
            expandStyles.setInitHeight(getMeasuredHeight());


        int nextDimension = expandStyles.getMaxWidth();

        if( nextDimension == getMeasuredWidth() )
            nextDimension = expandStyles.getInitWidth();

        mCurrentAnimator = ValueAnimator.ofFloat(getMeasuredWidth(), nextDimension);

        mCurrentAnimator.addUpdateListener(animation -> {
            //new ValueAnimator.AnimatorUpdateListener
            ExpandBox.this.getLayoutParams().width = Math.round((float) animation.getAnimatedValue());
            ExpandBox.this.getLayoutParams().height = Math.round((float) animation.getAnimatedValue());
            requestLayout();
        });

        mCurrentAnimator.setDuration(500);
        mCurrentAnimator.start();
    }

    private class ExpandStyles{

        int maxWidth = 0;
        int initWidth;

        public int getInitHeight() {
            return initHeight;
        }

        public void setInitHeight(int initHeight) {
            this.initHeight = initHeight;
        }

        public int getInitWidth() {
            return initWidth;
        }

        public void setInitWidth(int initWidth) {
            this.initWidth = initWidth;
        }

        int initHeight;

        public int getMaxHeight() {
            return maxHeight;
        }

        public void setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
        }

        public int getMaxWidth() {
            return maxWidth;
        }

        public void setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }

        int maxHeight = 0;
    }
}
