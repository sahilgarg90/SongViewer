package com.android15dev.songviewer.network;

import com.android15dev.songviewer.model.SongListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search")
    Call<SongListResponse> getSongs(@Query("term") String term);
}
