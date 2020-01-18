package com.android15dev.songviewer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SongListResponse {

    @SerializedName("results")
    @Expose
    private List<Song> songs = null;

    public List<Song> getSongs() {
        return songs;
    }
}
