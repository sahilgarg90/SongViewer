package com.android15dev.songviewer.songs_list;

import com.android15dev.songviewer.model.Song;

import java.util.List;

public class SongListPresenter implements SongListContract.Presenter, SongListContract.Model.OnFinishedListener {

    private SongListContract.View songListView;

    private SongListContract.Model songListModel;

    SongListPresenter(SongListContract.View songListView) {
        this.songListView = songListView;
        songListModel = new SongListModel();
    }

    @Override
    public void onDestroy() {
        this.songListView = null;
    }

    @Override
    public void requestDataFromServer(boolean isRefreshed) {

        if (songListView != null && !isRefreshed) {
            songListView.showProgress();
        }
        songListModel.getSongList(this);
    }

    @Override
    public void onFinished(List<Song> songArrayList) {
        songListView.setDataToRecyclerView(songArrayList);
        if (songListView != null) {
            songListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {

        songListView.onResponseFailure(t);
        if (songListView != null) {
            songListView.hideProgress();
        }
    }
}
