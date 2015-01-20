package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketwatch.demo.models.SocialItem;
import com.pocketwatch.demo.ui.CircleImageView;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/31/14.
 */
public class SocialItemAdapter extends ArrayAdapter<SocialItem> {
    private static final String TAG = "SocialItemAdapter";
    private List<SocialItem> mSocialItems;
    private Context mContext;

    public SocialItemAdapter(Context context, int viewId, List<SocialItem> socialItems) {
        super(context, viewId, socialItems);
        this.mSocialItems = socialItems;
        this.mContext = context;
    }

    private class ViewHolder {
        public CircleImageView avatar;
        public TextView creator;
        public TextView content;
        public ImageView image;
    }

    @Override
    public int getCount() { return mSocialItems.size(); }

    @Override
    public SocialItem getItem(int position) {
        return mSocialItems.get(position);
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

        SocialItem socialItem = mSocialItems.get(position);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.social_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.social_avatar);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.social_image);
            viewHolder.creator = (TextView) convertView.findViewById(R.id.social_creator);
            viewHolder.content = (TextView) convertView.findViewById(R.id.social_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.creator.setText(socialItem.getCreatorName());
        viewHolder.content.setText(socialItem.getContent());

        ImageLoader.loadImage(viewHolder.avatar,
                              socialItem.getAvatarImage().getSocialItemImageUrl(),
                              ImageLoader.getCache(),
                              R.drawable.thumb_placeholder);

        if (null != socialItem.getStandardImage())
            imgUrls.add(socialItem.getStandardImage().getSocialItemImageUrl());
        if (null != socialItem.getLargeImage())
            imgUrls.add(socialItem.getLargeImage().getSocialItemImageUrl());
        if (null != socialItem.getMediumImage())
            imgUrls.add(socialItem.getMediumImage().getSocialItemImageUrl());
        if (null != socialItem.getSmallImage())
            imgUrls.add(socialItem.getSmallImage().getSocialItemImageUrl());
        if (null != socialItem.getThumbnailImage())
            imgUrls.add(socialItem.getThumbnailImage().getSocialItemImageUrl());

        if (!imgUrls.isEmpty()) {
            ImageLoader.loadImage(viewHolder.image,
                                  imgUrls,
                                  ImageLoader.getCache(),
                                  R.drawable.tile_placeholder);
        }

        Utils.Exit(TAG, func);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public boolean isEmpty() {
        return mSocialItems.isEmpty();
    }
}
