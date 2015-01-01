package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.ui.R;
import com.pocketwatch.demo.utils.Calendar;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/25/14.
 */
public class EpisodeAdapter extends ArrayAdapter<Episode> {
    private static final String TAG = "EpisodeAdapter";
    private List<Episode> mEpisodes;
    private Context mContext;

    public EpisodeAdapter(Context context, int viewId, List<Episode> episodes) {
        super(context, viewId, episodes);
        this.mEpisodes = episodes;
        this.mContext = context;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView dateDuration;
        public TextView description;
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
            convertView = inflater.inflate(R.layout.episodes_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.episode_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.episode_title);
            viewHolder.dateDuration = (TextView) convertView.findViewById(R.id.episode_date_duration);
            viewHolder.description = (TextView) convertView.findViewById(R.id.episode_description);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(episode.getTitle());
        Calendar calendar = new Calendar(episode.getCreationTime());
        viewHolder.dateDuration.setText(calendar.getDateString() + " * " + Utils.getFormatDuration(episode.getDuration()));
        //Log.d(TAG, "Raw Duration: " + episode.getDuration());
        //Log.d(TAG, "Duration: " + Utils.getFormatDuration(episode.getDuration()));
        viewHolder.description.setText(episode.getDescription());
        imgUrls.add(episode.getTileImageUrl());
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
