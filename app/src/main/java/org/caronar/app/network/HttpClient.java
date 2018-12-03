package org.caronar.app.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class HttpClient {

    private final RequestQueue mRequestQueue;
    private final ImageLoader mImageLoader;

    private HttpClient(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                cache = new LruCache<>(20);

            @Override public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private static HttpClient sClient;
    public static HttpClient getInstance(Context context) {
        if (sClient == null)
            sClient = new HttpClient(context);
        return sClient;
    }
}
