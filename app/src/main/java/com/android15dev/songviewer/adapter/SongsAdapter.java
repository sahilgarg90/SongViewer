package com.android15dev.songviewer.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android15dev.songviewer.R;
import com.android15dev.songviewer.model.Song;
import com.android15dev.songviewer.songs_list.SongListActivity;
import com.android15dev.songviewer.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> implements Filterable {

    private SongListActivity songListActivity;
    private List<Song> songList;

    public SongsAdapter(SongListActivity songListActivity, List<Song> songList) {
        this.songListActivity = songListActivity;
        this.songList = songList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_song_card, parent, false);
        return new MyViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Song song = songList.get(position);

        holder.textViewSongTitle.setText(song.getTrackName());
        holder.textViewArtistName.setText(String.valueOf(song.getArtistName()));
        holder.textViewTime.setText(String.format("%s | %s", Utils.convertTime(song.getTrackTimeMillis()), Utils.getMimeType(holder.context, song.getPreviewUrl())));

        Glide.with(songListActivity)
                .load(song.getArtworkUrl100())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBarLoadThumb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBarLoadThumb.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                .into(holder.imageViewSongThumb);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songListActivity.onSongItemClick(songList.get(position), holder.imageViewSongThumb);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (getItemCount() == 0) {
                    songListActivity.showEmptyView();
                } else {
                    songListActivity.hideEmptyView();
                }
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSongTitle;
        TextView textViewArtistName;
        TextView textViewTime;
        ImageView imageViewSongThumb;
        ProgressBar progressBarLoadThumb;

        Context context;

        MyViewHolder(View itemView, Context context) {
            super(itemView);

            textViewSongTitle = itemView.findViewById(R.id.textViewSongTitle);
            textViewArtistName = itemView.findViewById(R.id.textViewArtistName);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            imageViewSongThumb = itemView.findViewById(R.id.imageViewSongThumb);
            progressBarLoadThumb = itemView.findViewById(R.id.progressBarLoadThumb);
            this.context = context;
        }
    }
}
