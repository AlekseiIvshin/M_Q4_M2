package com.eficksan.mq4m2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.eficksan.mq4m2.view.Virus;
import com.eficksan.mq4m2.view.VirusSeed;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        test();
    }

    private void test() {
        Random random = new Random();
        VirusSeed seed = VirusSeed.generateRandom(getApplicationContext(), random);
        Virus virusView = new Virus(getApplicationContext(), seed);
        int topCoordinate = 100;
        int leftCoordinate = 100;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(seed.virusSize, seed.virusSize);
        params.topMargin = topCoordinate;
        params.leftMargin = leftCoordinate;
        addContentView(virusView, params);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_new_game)
    public void handleNewGameClick() {
        startActivity(GameActivity.newGameIntent(getApplicationContext()));
    }
}
