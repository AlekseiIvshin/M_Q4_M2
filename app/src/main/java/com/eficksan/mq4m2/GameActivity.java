package com.eficksan.mq4m2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eficksan.mq4m2.view.VirusField;

public class GameActivity extends AppCompatActivity implements VirusField.VirusCountUpdateListener {

    private static final String TAG = GameActivity.class.getSimpleName();
    VirusField field;

    int generationSpeed = 1;

    public static Intent newGameIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        field = (VirusField) findViewById(R.id.field);
        field.setCountUpdateListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        field.startVirusGeneration(generationSpeed);
    }

    @Override
    protected void onStop() {
        field.stopVirusGeneration(false);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        field.setCountUpdateListener(null);
        field.stopVirusGeneration(true);
        super.onDestroy();
    }

    @Override
    public void onCountUpdate(int oldCount, int newCount) {
        Log.v(TAG, String.format("Update virus count: old = %d, new = %d", oldCount, newCount));
    }
}
