package com.eficksan.mq4m2.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.eficksan.mq4m2.R;

/**
 * Created by Aleksei Ivshin
 * on 06.11.2016.
 */

public class Virus extends View {

    public static int DEFAULT_SIZE = 20;

    private static final int SQUARE = 0;
    private static final int OVAL = 1;
    private static final int TRIANGLE = 2;

    public int virusColor;
    public int virusSize;
    public int virusShapeCode;

    private Paint virusPaint;

    public Virus(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Virus, 0, 0);

        try {
            virusColor = a.getColor(R.styleable.Virus_virusColor, ContextCompat.getColor(context, R.color.colorAccent));
            virusSize = a.getDimensionPixelSize(R.styleable.Virus_virusSize, (int) (DEFAULT_SIZE * context.getResources().getDisplayMetrics().density));
            virusShapeCode = a.getInt(R.styleable.Virus_virusShape, SQUARE);
        } finally {
            a.recycle();
        }

        init();
    }

    public Virus(Context context, VirusSeed virusSeed) {
        super(context);

        this.virusColor = virusSeed.virusColor;
        this.virusSize = virusSeed.virusSize;
        this.virusShapeCode = virusSeed.virusShapeCode;

        init();
    }

    private static Path drawTriangle(int virusSize) {
        Path path = new Path();
        path.moveTo(virusSize / 2, 0);
        path.lineTo(0, virusSize);
        path.moveTo(0, virusSize);
        path.lineTo(virusSize, virusSize);
        path.moveTo(virusSize, virusSize);
        path.lineTo(virusSize / 2, 0);
        return path;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(virusSize, virusSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (virusShapeCode) {
            case SQUARE:
                canvas.drawRect(0, 0, virusSize, virusSize, virusPaint);
                break;
            case OVAL:
                canvas.drawOval(0, 0, virusSize, virusSize, virusPaint);
                break;
            case TRIANGLE:
                canvas.drawPath(drawTriangle(virusSize), virusPaint);
                break;
        }
    }

    private void init() {
        virusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        virusPaint.setColor(virusColor);
        virusPaint.setStyle(Paint.Style.FILL);
    }
}
