package com.example.chuvak.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.SurfaceView;
import android.view.View;
import android.graphics.Typeface;
import android.content.res.AssetManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by chuvak on 18.10.2014.
 */
public class GraphicTest1 extends View {

    EventHandler eventHandler;

    private Rectangle rect;
    public void setRect(Rectangle rectangle)
    {
        rect = new Rectangle(rectangle);
    }

    private ShapeDrawable shapeDrawable;

    public void setShapeDrawable(ShapeDrawable shape)
    {
        shapeDrawable = shape;
        shapeDrawable.setBounds(rect.Left(), rect.Top(), rect.Right() , rect.Bottom());
        repaint();
    }

    int counter = 0;
    public void repaint()
    {
        invalidate();
    }

    private OnClickListener clickListener;
    public OnClickListener clickListenerButton_pp;
    public OnClickListener clickListenerButton_mm;

    public Typeface ttf_asset_font = null;
    int font_size = 30;
    public void addCounter()
    {
        counter++;
    }
    public void subCounter( EventArgs args)
    {
        if(args == null || args.args == null || args.args.length != 2)
            return;

        View checkBox = null;

        if (args.args[1] instanceof View)
            checkBox = (View) args.args[1]  ;

        if (checkBox != null)
            if (checkBox instanceof CheckBox){
            if (((CheckBox)checkBox).isChecked())
                counter--;
        }
    }

    ImageButton bm;
    ImageButton b;
    public GraphicTest1(final Context context)
    {
        super(context);

        setFocusable(true);
        shapeDrawable = new ShapeDrawable();

        eventHandler = new EventHandler();

        eventHandler.AddHandler(this, "addCounter", null );
        eventHandler.AddHandler(this, "repaint", null );

        setFocusable(true);
        setClickable(true);
        clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            eventHandler.SendEvent(EventArgs.Empty());
        }



    };
        setOnClickListener(clickListener);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        shapeDrawable.draw(canvas);

        canvas.drawARGB(255, 0xD4, 0xD0, 0xC8);

        Paint paint = new Paint(shapeDrawable.getPaint());
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setTextSize(font_size);

        paint.setTypeface(ttf_asset_font);

        canvas.drawText("Size :"+rect.ToString(), 10, font_size, paint);
        canvas.drawText("Counter :"+counter, 10, font_size*2, paint);
        canvas.drawText("Это Русский текст :"+font_size, 10, font_size*3, paint);

    }



}
