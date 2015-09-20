package com.alexios.monet.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


class BitmapLruImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

	public BitmapLruImageCache() {
		this(ImageCacheManager.INSTANCE.cacheSize);
	}

	public BitmapLruImageCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public Bitmap getBitmap(String url) {
	//Get bitmap from  LRU Cache
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
    //Put bitmap in LRU Cache
		put(url, bitmap);
	}

}
