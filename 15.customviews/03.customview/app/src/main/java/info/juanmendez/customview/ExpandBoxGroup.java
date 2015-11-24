package info.juanmendez.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Juan on 11/22/2015.
 *
 * Have two boxes and make one slide
 */
public class ExpandBoxGroup extends ViewGroup
{
    public ExpandBoxGroup(Context context) {
        this( context, null );
    }

    public ExpandBoxGroup(Context context, AttributeSet attrs) {this( context, attrs, 0 );}

    public ExpandBoxGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     *
     * when it comes to child measurments if the children were set in the layout
     * they indirectly will force onMeasure to be called, and then return for each its dimensions.
     * the first time onMeasure is called dimensions are all 0x0.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // Report our final dimensions.
        setMeasuredDimension( widthMeasureSpec, heightMeasureSpec );
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int count = getChildCount();
        View child;
        int x = 0;
        int y = 0;
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        for( int i = 0; i < count; i++ ){
            child = getChildAt(i);
        }
    }
}
