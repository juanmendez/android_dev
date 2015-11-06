package com.sqisland.android.pizza;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Juan on 11/1/2015.
 */
public class Pizza extends View {
    private PizzaAttrs pizzaAttrs = new PizzaAttrs();
    private StyleAttrs styleAttrs = new StyleAttrs();

    public Pizza(Context context) {
        super(context);
    }

    public Pizza(Context context, AttributeSet attrs) {
        super(context, attrs);

        styleAttrs.inflate( context, attrs );
    }

    public Pizza(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        styleAttrs.inflate( context, attrs );
    }

    @Override
    protected void onDraw(Canvas canvas) {

        pizzaAttrs.inflate(styleAttrs.getPaint());
        canvas.drawCircle(pizzaAttrs.cx, pizzaAttrs.cy, pizzaAttrs.radius, styleAttrs.getPaint());
        drawPizzaCuts( canvas );
    }

    void drawPizzaCuts( Canvas canvas){
        final int numWedges = styleAttrs.numWedges;
        final float degrees = 360f/numWedges;

        for( int i = 0; i < numWedges; i++ )
        {
            canvas.drawLine(pizzaAttrs.cx, pizzaAttrs.cy, pizzaAttrs.cx, pizzaAttrs.cy - pizzaAttrs.radius, styleAttrs.getPaint());
            canvas.rotate(degrees, pizzaAttrs.cx, pizzaAttrs.cy);
        }

        canvas.rotate( degrees/2, pizzaAttrs.cx, pizzaAttrs.cy );
        canvas.restore();
    }

    class PizzaAttrs{
        int width;
        int height;
        int cx;
        int cy;
        float diameter;
        float radius;

        public void inflate(Paint paint){

            width = getWidth() - getPaddingLeft() - getPaddingRight();
            height = getHeight() - getPaddingTop() - getPaddingBottom();
            cx = width/2 + getPaddingLeft();
            cy = height/2 + getPaddingTop();
            diameter = Math.min( width, height) - paint.getStrokeWidth();
            radius = diameter/2;
        }
    }

    class StyleAttrs{
        int strokeWidth = 4;
        int color = Color.YELLOW;
        int numWedges = 1;
        Paint paint;


        void inflate( Context context, AttributeSet attrs ){

            TypedArray array = context.obtainStyledAttributes( attrs, R.styleable.Pizza);
            strokeWidth = array.getDimensionPixelSize( R.styleable.Pizza_stroke_width, strokeWidth);
            color = array.getColor( R.styleable.Pizza_cheese_color, color);
            numWedges = array.getInt(R.styleable.Pizza_num_wedges, numWedges);
        }

        Paint getPaint(){

            if( paint == null )
            {
                paint = new Paint( Paint.ANTI_ALIAS_FLAG );
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(strokeWidth);
                paint.setColor(color);
            }

            return paint;
        }
    }
}