package com.eficksan.mq4m2.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.eficksan.mq4m2.R;

import java.util.Random;

/**
 * Created by Aleksei Ivshin
 * on 09.11.2016.
 */

public class VirusSeed {
    public final int virusColor;
    public final int virusSize;
    public final int virusShapeCode;

    public VirusSeed(int virusColor, int virusSize, int virusShapeCode) {
        this.virusColor = virusColor;
        this.virusSize = virusSize;
        this.virusShapeCode = virusShapeCode;
    }

    public static VirusSeed generateRandom(Context context) {
        Random random = new Random();
        return generateRandom(context, random);
    }

    public static VirusSeed generateRandom(Context context, Random random) {
        int virusShapeCode = random.nextInt(3);
        int dpSize = random.nextInt(Virus.DEFAULT_SIZE * 3) + Virus.DEFAULT_SIZE;
        int virusSize = (int) (dpSize * context.getResources().getDisplayMetrics().density);
        int virusColor = ContextCompat.getColor(context, R.color.colorAccent);

        return new VirusSeed(virusColor, virusSize, virusShapeCode);
    }

}
