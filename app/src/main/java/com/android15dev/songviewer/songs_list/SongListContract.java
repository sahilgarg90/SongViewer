package com.android15dev.songviewer.songs_list;

import com.android15dev.songviewer.model.Song;

import java.util.List;

public interface SongListContract {

    interface Model {
        interface OnFinishedListener {
            void onFinished(List<Song> songArrayList);

            void onFailure(Throwable t);
        }

        void getSongList(OnFinishedListener onFinishedListener);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Song> songArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestDataFromServer(boolean isRefreshed);
    }
}
