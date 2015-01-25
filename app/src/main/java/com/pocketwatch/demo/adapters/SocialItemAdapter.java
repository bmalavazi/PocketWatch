package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.models.SocialItem;
import com.pocketwatch.demo.models.SocialItem.SocialItemVideo;
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
        public ImageView avatar;
        public TextView creator;
        public TextView content;
        public ImageView image;
        public VideoView video;
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
        ArrayList<String> imgUrls = null;

        Utils.Entry(TAG, func, "Position: " + position);

        SocialItem socialItem = mSocialItems.get(position);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.social_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.social_avatar);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.social_image);
            viewHolder.video = (VideoView) convertView.findViewById(R.id.social_video);
            viewHolder.creator = (TextView) convertView.findViewById(R.id.social_creator);
            viewHolder.content = (TextView) convertView.findViewById(R.id.social_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
/*
        switch (socialItem.getProviderEnum()) {
            case VINE:
                Utils.Debug(TAG, func, "Vine");
                viewHolder.image.setVisibility(View.GONE);
                viewHolder.video.setVisibility(View.VISIBLE);
                break;
            case TWITTER:
                Utils.Debug(TAG, func, "Twitter");
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.video.setVisibility(View.GONE);
                imgUrls = getImageUrlList(socialItem);
                break;
            case INSTAGRAM:
                Utils.Debug(TAG, func, "Instagram");
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.video.setVisibility(View.GONE);
                imgUrls = getImageUrlList(socialItem);
                break;
        }
*/
        viewHolder.creator.setText(socialItem.getCreatorName());
        Utils.Debug(TAG, func, socialItem.getCreatorName());
        viewHolder.content.setText(socialItem.getContent());
        Utils.Debug(TAG, func, socialItem.getContent());

        ImageLoader.loadImage(viewHolder.avatar,
                              socialItem.getAvatarImage().getSocialItemImageUrl(),
                              ImageLoader.getCache(),
                              R.drawable.thumb_placeholder);

        if (Constants.SOCIAL_ITEMS_PROVIDER.VINE == socialItem.getProviderEnum()) {
            SocialItemVideo socialItemVideo;

            if (null != (socialItemVideo = getVideo(socialItem))) {
                //MediaController mController = new MediaController(mContext);

                //mController.show();
                //mVideoView.start();
                //viewHolder.image.setOnClickListener(new AddVideoLink(socialItem));
                viewHolder.video.setVideoURI(Uri.parse(socialItemVideo.getSocialItemVideoUrl()));
                //viewHolder.video.setMediaController(new MediaController(mContext));
                viewHolder.video.requestFocus();

                new Thread(new PlayThread(viewHolder.video)).run();

                //viewHolder.video.start();
            }
        }

        imgUrls = getImageUrlList(socialItem);

        if (null != imgUrls && !imgUrls.isEmpty()) {
            ImageLoader.loadImage(viewHolder.image,
                                  imgUrls,
                                  ImageLoader.getCache(),
                                  R.drawable.tile_placeholder);
        }

        Utils.Exit(TAG, func);

        return convertView;
    }

    private class PlayThread implements Runnable {
        VideoView mVideo;

        PlayThread(VideoView view) {
            mVideo = view;
        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DISPLAY);
            mVideo.start();
            mVideo.requestLayout();
        }
    }

    private ArrayList<String> getImageUrlList(SocialItem socialItem) {
        final String func = "getImageUrlList()";
        ArrayList<String> imgUrls = new ArrayList<String>();

        Utils.Entry(TAG, func);

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

        Utils.Exit(TAG, func);

        return imgUrls;
    }

    private SocialItemVideo getVideo(SocialItem socialItem) {
        final String func = "getVideo()";
        SocialItemVideo videoUrl = null;

        Utils.Entry(TAG, func);

        if (null != socialItem.getHighQualityVideo())
            videoUrl = socialItem.getHighQualityVideo();
        else if (null != socialItem.getLowQualityVideo())
            videoUrl = socialItem.getLowQualityVideo();
        else if (null != socialItem.getMediumImage())
            videoUrl = socialItem.getEmbedVideo();

        Utils.Exit(TAG, func, videoUrl.getSocialItemVideoUrl());

        return videoUrl;
    }

    private class AddVideoLink implements View.OnClickListener {
        private SocialItem mSocialItem;

        public AddVideoLink(SocialItem socialItem) {
            mSocialItem = socialItem;
        }

        @Override
        public void onClick(View view) {
            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(getVideo(mSocialItem).getSocialItemVideoUrl()));
            mContext.startActivity(in);
        }
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
