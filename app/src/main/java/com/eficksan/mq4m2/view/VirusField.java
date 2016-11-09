package com.eficksan.mq4m2.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eficksan.mq4m2.R;

import java.util.Random;

/**
 * Created by Aleksei Ivshin
 * on 08.11.2016.
 */

public class VirusField extends RelativeLayout {

    private static final String TAG = VirusField.class.getSimpleName();
    public static final long DEFAULT_DELAY = DateUtils.SECOND_IN_MILLIS;
    public static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_PADDING = 36;
    public static final int MINUS_ONE_VIRUS = -1;
    public static final int ADD_ONE_VIRUS = 1;

    Handler uiHandler;

    int fieldPadding;

    int generationSpeed;

    private int topBound;
    private int leftBound;
    private int bottomBound;
    private int rightBound;

    private Runnable generatorTask;
    private final Random random;

    private OnClickListener virusClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            removeView(v);
            updateVirusCount(MINUS_ONE_VIRUS);
        }
    };
    private int virusCount;

    private VirusCountUpdateListener countUpdateListener;

    public VirusField(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Virus, 0, 0);

        try {
            generationSpeed = a.getInt(R.styleable.VirusField_generationSpeed, DEFAULT_SPEED);
            fieldPadding = a.getDimensionPixelOffset(R.styleable.VirusField_fieldPadding,
                    (int) (DEFAULT_PADDING * context.getResources().getDisplayMetrics().density));
        } finally {
            a.recycle();
        }

        random = new Random();

        generatorTask = new Runnable() {
            @Override
            public void run() {
                int topCoordinate = random.nextInt(bottomBound - topBound) + topBound;
                int leftCoordinate = random.nextInt(rightBound - leftBound) + leftBound;
                VirusSeed seed = VirusSeed.generateRandom(getContext(), random);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(seed.virusSize, seed.virusSize);
                params.topMargin = topCoordinate;
                params.leftMargin = leftCoordinate;
                Virus virusView = new Virus(getContext(), seed);
                addView(virusView, params);
                virusView.setOnClickListener(virusClickListener);
                updateVirusCount(ADD_ONE_VIRUS);
                uiHandler.postDelayed(generatorTask, DEFAULT_DELAY);
            }
        };
        virusCount = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        topBound = fieldPadding;
        leftBound = fieldPadding;
        bottomBound = getMeasuredHeight() - fieldPadding;
        rightBound = getMeasuredWidth() - fieldPadding;
    }

    public void startVirusGeneration(int generationSpeed) {
        this.generationSpeed = generationSpeed;
        if (uiHandler == null) {
            uiHandler = new Handler(Looper.getMainLooper());
        }
        uiHandler.postDelayed(generatorTask, DEFAULT_DELAY);
    }

    public void stopVirusGeneration(boolean clean) {
        uiHandler.removeCallbacks(generatorTask);
        if (clean) {
            virusCount = 0;
        }
    }

    private void updateVirusCount(int addVirusCount) {
        if (countUpdateListener != null) {
            countUpdateListener.onCountUpdate(virusCount, virusCount + addVirusCount);
        }
        virusCount += addVirusCount;
    }

    public void setCountUpdateListener(VirusCountUpdateListener countUpdateListener) {
        this.countUpdateListener = countUpdateListener;
    }

    public int getGenerationSpeed() {
        return generationSpeed;
    }

    public interface VirusCountUpdateListener {
        void onCountUpdate(int oldCount, int newCount);
    }
}
