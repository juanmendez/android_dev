package info.juanmendez.customview;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Juan on 11/8/2015.
 */
@RemoteViews.RemoteView
public class BoxGroup extends ViewGroup {

    public BoxGroup(Context context) {
        this( context, null );
    }

    public BoxGroup(Context context, AttributeSet attrs) {this( context, attrs, 0 );}

    public BoxGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void createChildren( int numChildren ){

        Box box;

        /**
         * I was trying hard to work with AttributeStyles programmatically
         * when in fact, I simply need to talk to the children in plain java objects.
         *
         * What I did was to set a default pojo for each child view, and then override
         * values if they come from AttributeStyles.
         */
        Box.BoxStyles styles = new Box.BoxStyles();
        styles.setPadding_color(getColorResource(getContext(), R.color.colorPrimary));
        styles.setPadding_Stroke(10);
        styles.setFill_color(getColorResource(getContext(), R.color.colorAccent));

        LayoutParams params = new LayoutParams(150, 150 );

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
        int roundWidth = 800, roundHeight = 800;
        int dim = 150;
        int count;

        if( getChildCount() == 0 )
        {
            createChildren( count = 15 );
        }
        else
        {
            count = getChildCount();
        }

        if( heightMode == MeasureSpec.EXACTLY )
        {
            roundHeight = heightSize;
        }
        else
        if( heightMode == MeasureSpec.AT_MOST )
        {
            roundHeight = Math.min( roundHeight, heightSize );
        }


        int totalHeight = count * dim;

        /**find out the height of this viewgroup**/
        if( roundHeight > totalHeight  )
        {
            roundHeight = totalHeight;
            roundWidth = dim;
        }
        else
        {
            roundHeight = Double.valueOf( Math.floor( (float)roundHeight / dim ) ).intValue() * dim;
            roundWidth = Double.valueOf( Math.ceil( (float)totalHeight / roundHeight) ).intValue() * dim;
        }


        View child;

        for( int i = 0; i < count; i++ ){
            child = getChildAt(i);

            if( child instanceof Box && child.getVisibility() != GONE ){

                child.measure(roundWidth, roundHeight);
            }
        }

        // Report our final dimensions.
        setMeasuredDimension( roundWidth, roundHeight );
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
        int dim = 150;
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        for( int i = 0; i < count; i++ ){
            child = getChildAt(i);

            if( child instanceof Box && child.getVisibility() != GONE ){

                child.layout( x, y, x + dim, y + dim );
                ((Box) child ).setIndex( i );
                x += dim;

                if( x + dim > width )
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
