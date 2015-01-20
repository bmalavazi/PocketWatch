package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 1/3/15.
 */
public class TrendingEpisodeAdapter extends ArrayAdapter<Episode> {
    private static final String TAG = "TrendingEpisodeAdapter";
    private List<Episode> mEpisodes;
    private Context mContext;

    public TrendingEpisodeAdapter(Context context, int viewId, List<Episode> episodes) {
        super(context, viewId, episodes);
        this.mEpisodes = episodes;
        this.mContext = context;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView episodeTitle;
        public TextView showTitle;
    }

    @Override
    public int getCount() {
        return mEpisodes.size();
    }

    @Override
    public Episode getItem(int position) {
        return mEpisodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final String func = "getView()";
        ViewHolder viewHolder = null;
        ArrayList<String> imgUrls = new ArrayList<String>();

        Utils.Entry(TAG, func, "Position: " + position);

        Episode episode = mEpisodes.get(position);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.trending_episodes_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.trending_episode_image);
            viewHolder.episodeTitle = (TextView) convertView.findViewById(R.id.trending_episode_title);
            viewHolder.showTitle = (TextView) convertView.findViewById(R.id.trending_episode_show_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.episodeTitle.setText(episode.getTitle());
        viewHolder.showTitle.setText(episode.getShowTitle());
        for (Episode.EpisodeThumbnail thumbnail : episode.getThumbnailList())
            imgUrls.add(Utils.getThumbnail(thumbnail.getThumbnailUrl()));
        ImageLoader.loadImage(viewHolder.image, imgUrls, ImageLoader.getCache(), R.drawable.thumb_placeholder);

        Utils.Exit(TAG, func);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public boolean isEmpty() {
        return mEpisodes.isEmpty();
    }
}
