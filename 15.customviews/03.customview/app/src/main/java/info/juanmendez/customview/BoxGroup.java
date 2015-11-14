package info.juanmendez.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;


/**
 * Created by Juan on 11/8/2015.
 */

@RemoteViews.RemoteView
public class BoxGroup extends ViewGroup {


    /** The amount of space used by children in the left gutter. */
    private int mLeftWidth;

    /** The amount of space used by children in the right gutter. */
    private int mRightWidth;

    /** These are used for computing child frames based on their gravity. */
    private final Rect mTmpContainerRect = new Rect();
    private final Rect mTmpChildRect = new Rect();

    public BoxGroup(Context context) {
        this( context, null );
    }

    public BoxGroup(Context context, AttributeSet attrs) {
        this( context, attrs, 0 );
    }

    public BoxGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createChildren( 50, attrs );
    }


    private void createChildren( int numChildren, AttributeSet attrs ){

        Box box;

        Box.BoxStyles styles = new Box.BoxStyles();
        styles.setPadding_color(getColorResource(getContext(), R.color.colorPrimary));
        styles.setPadding_Stroke(10);
        styles.setFill_color(getColorResource(getContext(), R.color.colorAccent));

        LayoutParams params = new LayoutParams(100, 100 );

        for( int i = 0; i < numChildren; i++ ){
            box = Box.generateBox( getContext(), styles );
            box.setLayoutParams( params );
            box.setBackgroundColor(getColorResource(getContext(), R.color.colorAccent ));
            addView( box, i );
        }
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
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        View child;

        for( int i = 0; i < count; i++ ){
            child = getChildAt(i);

            if( child instanceof Box && child.getVisibility() != GONE ){

                child.measure( widthMeasureSpec, heightMeasureSpec );
            }
        }


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
        int dim = 100;
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        for( int i = 0; i < count; i++ ){
            child = getChildAt(i);

            if( child instanceof Box && child.getVisibility() != GONE ){

                child.layout( x, y, x + dim, y + dim );
                x += dim;

                if( x  > width )
                {
                    x = 0;
                    y += dim;
                }
            }
        }

    }

    // ----------------------------------------------------------------------
    // The rest of the implementation is for custom per-child layout parameters.
    // If you do not need these (for example you are writing a layout manager
    // that does fixed positioning of its children), you can drop all of this.

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BoxGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }


     int getColorResource(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor( context, id );
        } else {
            return context.getResources().getColor(id);
        }
    }
}
