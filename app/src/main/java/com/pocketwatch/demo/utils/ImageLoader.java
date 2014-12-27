package com.pocketwatch.demo.utils;

/**
 * Created by bmalavazi on 12/24/14.
 */
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;


public class ImageLoader {
    private static final int BUFFER_SIZE = 8192;
    private static final int TIMEOUT = 3000;//ms
    private static final String TAG = "ImageLoader";

    private static final int CACHE_MEM_FRACTION = 8; //reciprocal of this

    private static final int MAX_LOADERS = 5;
    private static final int MAX_RETRIES = 3;
    private static final Semaphore sLoaderSemaphore = new Semaphore(MAX_LOADERS, true);
    private static BitmapSizeCache sCache = null;
    private static final int MiB = 1024 * 1024;
    private static final int CACHE_LIMIT = MiB * 2; //2MiB

    // Per the Android documentation, an AsyncTask can only be created on
    // the UI thread, so we need to keep a Handler to instantiate those.
    private static Handler sHandler;

    public synchronized static void initialize(Context context)
    {
        sHandler = new Handler(context.getMainLooper());
        int mem = CACHE_LIMIT;
        ActivityManager mgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(mgr != null){
            final int memClass = mgr.getMemoryClass();
            if(memClass > 0){
                mem = (memClass / CACHE_MEM_FRACTION) * MiB;
            }
        }
        sCache = new BitmapSizeCache(mem);
    }

    public static class BitmapSizeCache extends LruCache<String, Bitmap> {
        public BitmapSizeCache(int size) {
            super(size);
        }
        @Override
        protected int sizeOf(String key, Bitmap bm) {
            return bm.getRowBytes() * bm.getHeight();
        }
    }

    /**
     * Clears the image cache used internally to improve performance,
     *  the cache will automatically be cleared after a certain inactivity delay.
     */
    public static void clearCache() {
        Log.i(TAG,"Clearing the cache");
        sCache.evictAll();
    }

    /**
     * @return instance image cache
     */
    public static BitmapSizeCache getCache(){
        return sCache;
    }

    private static Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    /**
     * Loads an image in an async task, set on indicated ImageView, put in optional map.
     * @param iv
     * @param url
     * @param cache (optional)
     * @param defaultDrawable image resource id, or 0: invisible and -1: gone
     */
    public static void loadImage( final ImageView iv, final String url, final LruCache<String, Bitmap> cache, final int defaultDrawable){
        ArrayList<String> urls = new ArrayList<String>();
        urls.add(url);
        loadImage(iv, urls, cache, defaultDrawable);
    }

    /**
     * Loads an image in an async task, set on indicated ImageView, put in optional map.
     * @param iv
     * @param urls
     * @param cache (optional)
     * @param defaultDrawable image resource id, or 0: invisible and -1: gone
     */
    public synchronized static void loadImage( final ImageView iv, final List<String> urls, final LruCache<String, Bitmap> cache, final int defaultDrawable) {
        Bitmap bitmap = null;

        String url = null;
        if(urls != null){
            for(String u : urls){
                if(!Utils.isEmpty(u)){
                    url = u;
                    break;
                }
            }
        }
        // This tells us what image URL should be in each image view.
        imageViews.put(iv, url);

        // First, check the cache for an image.
        if (null != cache && null != url) {
            bitmap = cache.get(url);
        }

        // Got a cache hit.  Set it and exit.
        if (bitmap != null) {
// This if statement shouldn't be needed.  If you happen to see image flashing during fast scrolling try uncommenting this.
//            if (imageViews.get(iv).equals(url)) {
            iv.setImageBitmap(bitmap);
            iv.setVisibility(View.VISIBLE);
            iv.invalidate();
//            }
            return;
        }

        // No cache hit, setup to load the image.

        // First set a default image.
        if (defaultDrawable > 0) {
            iv.setImageResource(defaultDrawable);
            iv.setVisibility(View.VISIBLE);
        } else if (defaultDrawable == -1){
            iv.setVisibility(View.GONE);
        } else {
            iv.setVisibility(View.INVISIBLE);
        }

        // Queue the request.
        ImageLoaderJobQueue.getInstance().addJob(iv, urls, cache, defaultDrawable);
    }



    /**
     * Loads Image directly, put in given cache, keyed on given url.
     * @param cache
     * @param url
     * @param cache (optional)
     * @return Bitmap
     */
    public static Bitmap loadImage( LruCache<String, Bitmap> cache, String url ){
        Bitmap cachedBm = null;
        if(cache != null) {
            cachedBm = cache.get(url);
        }
        if (cachedBm == null) {
            Bitmap bm = loadImage(url);
            if (cache != null) {
                synchronized (cache) {
                    if (bm != null && url != null && cache.get(url) == null) {
                        cache.put(url, bm);
                    }
                }
            }
            return bm;
        } else {
            return cachedBm;
        }

    }

    /**
     * Loads an Image given a url.
     * @param url
     * @return Bitmap
     * @throws IOException
     */
    private static Bitmap loadImage(String url) {

        if (url == null || url.toString().trim().length() == 0 || !url.contains("http")) {
            return null;
        }

        Bitmap bm = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = null;
        ByteArrayOutputStream bytestream = null;

        try {
            URL imageUrl = new URL(url);
            URLConnection conn = imageUrl.openConnection();
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            conn.connect();

            bis = new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
            bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = bis.read()) != -1) {
                bytestream.write(ch);
            }
            bytestream.flush();

        } catch (IOException e) {
            Log.e(TAG, "An error occured while retreiving the icon:" + url);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                // Do nothing.
            }
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "An error occured while closing the stream:");
            }
        }

        if (bytestream != null && bytestream.size() != 0) {
            bm = BitmapFactory.decodeByteArray(bytestream.toByteArray(), 0, bytestream.size());
        }

        return bm;
    }


    private static class ImageLoaderJobQueue {
        private static ImageLoaderJobQueue sImageLoaderJobQueue = null;

        private Thread mThread = null;
        private Stack<ImageLoaderJobQueueJob> mJobs = null;

        public static synchronized ImageLoaderJobQueue getInstance() {
            if (null == sImageLoaderJobQueue) {
                sImageLoaderJobQueue = new ImageLoaderJobQueue();
            }

            return sImageLoaderJobQueue;
        }

        private ImageLoaderJobQueue() {
            super();
        }

        public synchronized void addJob(ImageView imageView, List<String> urls, LruCache<String, Bitmap> cache, int defaultDrawable) {
            if (null == mJobs) {
                mJobs = new Stack<ImageLoaderJobQueueJob>();
            }

            mJobs.add(new ImageLoaderJobQueueJob(imageView, urls, cache, defaultDrawable));

            if (null == mThread || !mThread.isAlive()) {
                mThread = new Thread(new ImageLoaderJobQueueWorkerThread(), "ImageLoaderJobQueueWorkerThread");
                mThread.start();
            }
        }

        private class ImageLoaderJobQueueWorkerThread implements Runnable {
            @Override
            public void run() {
                while (null != mJobs && !mJobs.isEmpty()) {
                    ImageLoaderJobQueueJob job = mJobs.pop();
                    if (null != job) {
                        final List<String> urls = job.getUrls();

                        final int len = (null != urls) ? urls.size() : 0;
                        final String url = (len > 0) ? urls.get(0) : null;
                        final boolean hasNext = (len > 1);


                        ImageView iv = job.getImageView();
                        if (null == iv) {
                            // This indicates that the requesting image view has been destroyed.
                            continue;
                        }
                        final WeakReference<ImageView> ivWeakReference = new WeakReference<ImageView>(iv);

                        String ivUrl = imageViews.get(iv);
                        if( ivUrl != null && !ivUrl.equals(url) ) {
                            // This indicates a different image has been requested for this image view since we were queued.
                            continue;
                        }

                        final LruCache<String, Bitmap> cache = job.getCache();
                        final int defaultDrawable = job.getDefaultDrawable();

                        try {
                            sLoaderSemaphore.acquire();
                            ivUrl = imageViews.get(iv);
                            if( ivUrl != null && !ivUrl.equals(url) ) {
                                // This indicates a different image has been requested for this image view since we were queued.
                                sLoaderSemaphore.release();
                                continue;
                            }

                            Runnable asyncTaskCreator = new Runnable() {
                                @Override
                                public void run() {
                                    new AsyncTask<String, Integer, Bitmap>() {
                                        @Override
                                        protected Bitmap doInBackground(String... params) {
                                            Bitmap bitmap = null;
                                            int retryCounter = MAX_RETRIES;

                                            while (null == bitmap && retryCounter > 0) {
                                                try {
                                                    bitmap = loadImage(cache, params[0]);
                                                } catch (Exception e) {
                                                    // Do nothing.
                                                }

                                                retryCounter--;

                                                if (bitmap == null && retryCounter > 0) {
                                                    try {
                                                        Thread.sleep(1500);
                                                    } catch (InterruptedException e) {
                                                        // Do nothing.
                                                    }
                                                }
                                            }

                                            return bitmap;
                                        }

                                        protected void onPostExecute(Bitmap result) {
                                            sLoaderSemaphore.release();
                                            ImageView iv = ivWeakReference.get();
                                            if (null == iv) {
                                                return;
                                            }

                                            if (result != null) {
                                                // success!
                                                String ivUrl = imageViews.get(iv);
                                                if (ivUrl != null && ivUrl.equals(url)) {
                                                    iv.setImageBitmap(result);
                                                    iv.setVisibility(View.VISIBLE);
                                                }
                                            } else if (hasNext) {
                                                // try next URL, if available
                                                loadImage(iv, urls.subList(1, len), cache,
                                                        defaultDrawable);
                                            }
                                        }
                                    }.execute(url);
                                }
                            };
                            sHandler.post(asyncTaskCreator);
                        } catch (InterruptedException e) {
                            sLoaderSemaphore.release();
                        }
                    }
                }
            }
        }

        private class ImageLoaderJobQueueJob {
            private WeakReference<ImageView> mImageView = null;
            private List<String> mUrls = null;
            private LruCache<String, Bitmap> mCache = null;
            private int mDefaultDrawable = 0;

            public ImageLoaderJobQueueJob(ImageView imageView, List<String> urls, LruCache<String, Bitmap> cache, int defaultDrawable) {
                mImageView = new WeakReference<ImageView>(imageView);
                mUrls = urls;
                mCache = cache;
                mDefaultDrawable = defaultDrawable;
            }

            /**
             * @return the mImageView
             */
            public ImageView getImageView() {
                return mImageView.get();
            }

            /**
             * @return the mUrls
             */
            public List<String> getUrls() {
                return mUrls;
            }

            /**
             * @return the mCache
             */
            public LruCache<String, Bitmap> getCache() {
                return mCache;
            }

            /**
             * @return the mDefaultDrawable
             */
            public int getDefaultDrawable() {
                return mDefaultDrawable;
            }
        }
    }
}
