package com.android15dev.songviewer.songs_list;

import android.util.Log;

import com.android15dev.songviewer.model.Song;
import com.android15dev.songviewer.model.SongListResponse;
import com.android15dev.songviewer.network.ApiClient;
import com.android15dev.songviewer.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListModel implements SongListContract.Model {

    private final String TAG = SongListModel.class.getName();

    @Override
    public void getSongList(final OnFinishedListener onFinishedListener) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<SongListResponse> call = apiService.getSongs("Michael jackson");
        call.enqueue(new Callback<SongListResponse>() {
            @Override
            public void onResponse(Call<SongListResponse> call, Response<SongListResponse> response) {
                List<Song> songs = response.body().getSongs();
                Log.d(TAG, "Number of songs received: " + songs.size());
                onFinishedListener.onFinished(songs);
            }

            @Override
            public void onFailure(Call<SongListResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

}
