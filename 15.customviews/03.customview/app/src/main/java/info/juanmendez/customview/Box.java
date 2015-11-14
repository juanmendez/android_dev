package info.juanmendez.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Juan on 11/9/2015.
 */
public class Box extends View {
    AttributeSet attrs;
    TypedArray androidAttrs;
    TypedArray boxAttrs;
    BoxStyles boxStyles = new BoxStyles();

    public Box(Context context) {
        this(context, null);
    }


    public Box(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Box(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public static Box generateBox( Context context, BoxStyles boxStyles ){

        Box b = new Box( context );
        b.boxStyles = boxStyles;

        return b;
    }

    void init( Context context, AttributeSet attrs, int defStyleAttr ){
        this.attrs = attrs;
        boxAttrs = context.obtainStyledAttributes(attrs, R.styleable.BoxStyle);
        androidAttrs = context.obtainStyledAttributes( attrs, new int[]{ android.R.attr.background} );

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_LONG ).show();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*
        int contentWidth = 100;
        int minW = contentWidth + getPaddingLeft() + getPaddingRight();
        int minH = contentWidth + getPaddingTop() + getPaddingBottom();

        int w = resolveSizeAndState(minW, widthMeasureSpec, 0);
        int h = resolveSizeAndState(minH, heightMeasureSpec, 0);

        setMeasuredDimension( w, h );*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect_back = new Rect( );
        rect_back.left = 0 + getPaddingLeft();
        rect_back.right = canvas.getWidth() - getPaddingRight();
        rect_back.top = getPaddingTop();
        rect_back.bottom = canvas.getHeight() - getPaddingBottom();

        int padding_stroke = boxAttrs.getDimensionPixelSize(R.styleable.BoxStyle_padding_stroke, boxStyles.getPadding_Stroke());

        Rect rect = new Rect( );
        rect.left = rect_back.left + padding_stroke;
        rect.right = rect_back.right - padding_stroke;
        rect.top = rect_back.top + padding_stroke;
        rect.bottom = rect_back.bottom - padding_stroke;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG );
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(boxAttrs.getColor(R.styleable.BoxStyle_padding_color, boxStyles.getPadding_color()));
        canvas.drawRect(rect_back, paint);

        paint.setColor(androidAttrs.getColor(0, boxStyles.getFill_color()));
        canvas.drawRect(rect, paint);

        //androidAttrs.recycle();
        //boxAttrs.recycle();
    }

    static class BoxStyles{

        int padding_Stroke = 0;
        int padding_color = Color.RED;

        public int getFill_color() {
            return fill_color;
        }

        public void setFill_color(int fill_color) {
            this.fill_color = fill_color;
        }

        public int getPadding_Stroke() {
            return padding_Stroke;
        }

        public void setPadding_Stroke(int padding_Stroke) {
            this.padding_Stroke = padding_Stroke;
        }

        public int getPadding_color() {
            return padding_color;
        }

        public void setPadding_color(int padding_color) {
            this.padding_color = padding_color;
        }

        int fill_color = Color.YELLOW;



        public BoxStyles(){

        }


    }
}