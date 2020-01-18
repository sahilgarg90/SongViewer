package com.android15dev.songviewer.splash;

import android.os.Handler;

public class SplashModel implements SplashContract.Model {

    private final int DELAY = 2000;

    @Override
    public void waitForSometime(final OnFinishedListener onFinishedListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onFinishedListener.onFinished();
            }
        }, DELAY);
    }
}
