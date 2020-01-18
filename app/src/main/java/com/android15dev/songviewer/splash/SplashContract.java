package com.android15dev.songviewer.splash;

public interface SplashContract {

    interface Model {
        interface OnFinishedListener {
            void onFinished();
        }

        void waitForSometime(OnFinishedListener onFinishedListener);
    }

    interface View {
        void showSongListing();
    }

    interface Presenter {
        void onDestroy();

        void decideNextActivity();
    }
}
