package com.android15dev.songviewer.songs_list;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android15dev.songviewer.BaseActivity;
import com.android15dev.songviewer.R;
import com.android15dev.songviewer.adapter.SongsAdapter;
import com.android15dev.songviewer.model.Song;
import com.android15dev.songviewer.song_detail.SongDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.android15dev.songviewer.utils.AppConstants.KEY_SONG_DETAIL;

public class SongListActivity extends BaseActivity implements SongListContract.View, ShowEmptyView, SongItemClickListener {

    private static final String TAG = SongListActivity.class.getName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewSongs;
    private ProgressBar progressBarSongs;
    private TextView textViewEmpty;

    private List<Song> songsList;
    private SongsAdapter songsAdapter;

    private SongListPresenter songListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        setActionBarTitle(R.string.app_name);

        initUI();
        setListeners();

        songListPresenter = new SongListPresenter(this);
        songListPresenter.requestDataFromServer(false);
    }

    private void initUI() {

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewSongs = findViewById(R.id.recyclerViewSongs);

        songsList = new ArrayList<>();
        songsAdapter = new SongsAdapter(this, songsList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewSongs.setLayoutManager(mLayoutManager);
        recyclerViewSongs.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSongs.setAdapter(songsAdapter);

        progressBarSongs = findViewById(R.id.progressBarSongs);
        textViewEmpty = findViewById(R.id.textViewEmpty);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (progressBarSongs.getVisibility() != View.VISIBLE) {
                    songListPresenter.requestDataFromServer(true);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void showProgress() {
        progressBarSongs.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarSongs.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setDataToRecyclerView(List<Song> songArrayList) {
        songsList.clear();
        songsList.addAll(songArrayList);
        songsAdapter.notifyDataSetChanged();
        songsAdapter.getFilter().filter("");
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, Objects.requireNonNull(throwable.getMessage()));
        Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        songListPresenter.onDestroy();
    }

    @Override
    public void showEmptyView() {
        recyclerViewSongs.setVisibility(View.GONE);
        textViewEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        recyclerViewSongs.setVisibility(View.VISIBLE);
        textViewEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onSongItemClick(Song song, View view) {
        Intent intent = new Intent(this, SongDetailActivity.class);
        intent.putExtra(KEY_SONG_DETAIL, song);
        String transitionName = getString(R.string.transition_name);

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, view, transitionName);
        startActivity(intent, transitionActivityOptions.toBundle());
    }
}
