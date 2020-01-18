package com.android15dev.songviewer.splash;

import android.content.Intent;
import android.os.Bundle;

import com.android15dev.songviewer.BaseActivity;
import com.android15dev.songviewer.R;
import com.android15dev.songviewer.songs_list.SongListActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashPresenter = new SplashPresenter(this);
        splashPresenter.decideNextActivity();
    }

    @Override
    public void showSongListing() {
        Intent intent = new Intent(this, SongListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.onDestroy();
    }
}
