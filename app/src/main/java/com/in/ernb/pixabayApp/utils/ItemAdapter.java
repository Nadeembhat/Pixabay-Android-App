package com.in.ernb.pixabayApp.utils;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.in.ernb.pixabayApp.AppPreferences;
import com.in.ernb.pixabayApp.R;
import com.in.ernb.pixabayApp.utils.entity.ItemEntity;
import com.in.ernb.pixabayApp.interfaces.AdapterClickListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Map;
/**
 * Author Nadeem Bhat ,
 * Created by Nadeem Bhat on Wednesday, Aug, 2020.
 * Copy Right (c).
 * Srinagar,Kashmir
 * ennennbee@gmail.com
 * Pixabay
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ArrayList<ItemEntity> items;
    private final AdapterClickListener clickListener;
    private AppPreferences preferences;

    public ItemAdapter(ArrayList<ItemEntity> items, AdapterClickListener clickListener, AppPreferences preferences) {
        this.items = items;
        this.clickListener = clickListener;
        this.preferences = preferences;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, int position) {
        final ItemEntity entity = items.get(position);
        final Long id = entity.getId();
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Long, Boolean> map = preferences.getId();
                if (map.containsKey(id) && map.containsValue(true)) {
                    Log.e("OnClick\t", " If  id is:" + entity.getId());
                    preferences.setFavImage(0L, false);
                    holder.favButton.setImageResource(R.drawable.unfav_star);
                } else {
                    Log.e("OnClick\t", " else  id is:" + entity.getId());
                    holder.favButton.setImageResource(R.drawable.clicked_start);
                    preferences.setFavImage(id, true);
                }
            }
        });
        Map<Long, Boolean> map = preferences.getId();
        if (map.containsKey(id) && map.containsValue(true)) {
            Log.e("OnClick\t", " If Condition and id is:" + entity.getId());
            preferences.setFavImage(0L, false);
            holder.favButton.setImageResource(R.drawable.clicked_start);
        } else {
            Log.e("OnClick\t", " else Condition and id is:" + entity.getId());
            holder.favButton.setImageResource(R.drawable.unfav_star);
            preferences.setFavImage(id, true);
        }
        setThumbnailImage(entity.getWebformatURL(), holder.thumbnailView);
        setAuthorImage(entity.getUserImageURL(), holder.authorImage);

        String author = holder.authorName.getContext()
                .getString(R.string.author, items.get(position).getUserName());
        String downloads = holder.downloadsView.getContext()
                .getString(R.string.total_downloads, entity.getDownloads());
        String size = holder.sizeView.getContext()
                .getString(R.string.size, entity.getWebformatWidth(), entity.getWebformatHeight());

        holder.authorName.setText(author);
        holder.likeView.setText(entity.getLikes());
        holder.favoriteView.setText(entity.getFavorites());
        holder.downloadsView.setText(downloads);
        holder.sizeView.setText(size);
    }

    private void setThumbnailImage(String url, ImageView thumbnailView) {
        Picasso.with(thumbnailView.getContext())
                .load(url)
                .into(thumbnailView);
    }

    private void setAuthorImage(String authorImageUrl, ImageView authorImage) {
        if (authorImageUrl != null && !authorImageUrl.isEmpty()) {
            Picasso.with(authorImage.getContext())
                    .load(authorImageUrl)
                    .error(R.drawable.ic_person_32dp)
                    .into(authorImage);
        } else {
            authorImage.setImageResource(R.drawable.ic_person_32dp);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cancelPicassoRequest();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final AdapterClickListener clickListener;
        private final ImageView thumbnailView;
        private final ImageView authorImage;
        private final TextView authorName;
        private final TextView likeView;
        private final TextView favoriteView;
        private final TextView downloadsView;
        private final TextView sizeView;
        private final Button btnDownload;
        private ImageButton favButton;

        ViewHolder(View itemView, AdapterClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            thumbnailView = itemView.findViewById(R.id.thumbnail_view);
            authorImage = itemView.findViewById(R.id.author_img);
            authorName = itemView.findViewById(R.id.txt_view_author);
            likeView = itemView.findViewById(R.id.txt_view_like);
            favoriteView = itemView.findViewById(R.id.txt_view_favorite);
            downloadsView = itemView.findViewById(R.id.txt_view_downloads);
            favButton = itemView.findViewById(R.id.favbutton);
            sizeView = itemView.findViewById(R.id.txt_view_size);
            btnDownload = itemView.findViewById(R.id.action_download);
            thumbnailView.setOnClickListener(this);
            btnDownload.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getAdapterPosition(), view);

        }

        private void cancelPicassoRequest() {
            Context context = thumbnailView.getContext();

            Picasso.with(context)
                    .cancelRequest(thumbnailView);

            Picasso.with(context)
                    .cancelRequest(authorImage);
        }
    }

}
