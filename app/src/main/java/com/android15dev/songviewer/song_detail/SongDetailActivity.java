package com.android15dev.songviewer.song_detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.android15dev.songviewer.BaseActivity;
import com.android15dev.songviewer.R;
import com.android15dev.songviewer.model.Song;
import com.android15dev.songviewer.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.android15dev.songviewer.utils.AppConstants.KEY_SONG_DETAIL;

public class SongDetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private ImageView imageViewSongThumb;
    private ProgressBar progressBarLoadThumb;
    private TextView textViewSongTitle, textViewCollectionName, textViewGenreName, textViewTime, textViewPrice, textViewPlay;
    private FloatingActionButton fab;

    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();
        setListeners();

        Intent mIntent = getIntent();
        song = (Song) mIntent.getSerializableExtra(KEY_SONG_DETAIL);

        if (song != null) {
            setData(song);
        }
    }

    private void initUI() {
        imageViewSongThumb = findViewById(R.id.imageViewSongThumb);
        progressBarLoadThumb = findViewById(R.id.progressBarLoadThumb);
        textViewSongTitle = findViewById(R.id.textViewSongTitle);
        textViewCollectionName = findViewById(R.id.textViewCollectionName);
        textViewGenreName = findViewById(R.id.textViewGenreName);
        textViewTime = findViewById(R.id.textViewTime);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewPlay = findViewById(R.id.textViewPlay);
        fab = findViewById(R.id.fab);
    }

    private void setListeners() {
        textViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeUrl(song.getTrackViewUrl());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeUrl(song.getPreviewUrl());
            }
        });
    }

    private void setData(Song song) {
        toolbar.setTitle(song.getArtistName());
        textViewSongTitle.setText(song.getTrackName());
        textViewCollectionName.setText(String.format("%s: %s", getString(R.string.album), song.getCollectionName()));
        textViewGenreName.setText(String.format("%s | %s", song.getPrimaryGenreName(), Utils.convertDate(song.getReleaseDate())));
        textViewTime.setText(Utils.convertTime(song.getTrackTimeMillis()));
        textViewPrice.setText(String.format("%s %s", song.getTrackPrice(), song.getCurrency()));

        // I have tried to increase the quality of the song thumbnail image by replacing size in the url.
        Glide.with(this)
                .load(song.getArtworkUrl100().replace("100x100", "400x400"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBarLoadThumb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBarLoadThumb.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                .into(imageViewSongThumb);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void invokeUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
