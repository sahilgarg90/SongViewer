package com.android15dev.songviewer.splash;

public class SplashPresenter implements SplashContract.Presenter, SplashContract.Model.OnFinishedListener {

    private SplashContract.View splashView;

    private SplashContract.Model splashModel;

    SplashPresenter(SplashContract.View splashView) {
        this.splashView = splashView;
        splashModel = new SplashModel();
    }

    @Override
    public void onDestroy() {
        this.splashView = null;
    }

    @Override
    public void decideNextActivity() {
        splashModel.waitForSometime(this);
    }

    @Override
    public void onFinished() {
        splashView.showSongListing();
    }
}
