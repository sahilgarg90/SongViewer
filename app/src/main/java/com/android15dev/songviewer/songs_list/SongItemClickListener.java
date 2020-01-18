package com.android15dev.songviewer.songs_list;

import android.view.View;

import com.android15dev.songviewer.model.Song;

public interface SongItemClickListener {

    void onSongItemClick(Song song, View view);
}
