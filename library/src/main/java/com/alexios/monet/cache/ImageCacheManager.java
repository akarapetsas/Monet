package com.alexios.monet.cache;

import android.graphics.Bitmap;

import com.alexios.monet.Monet;
import com.android.volley.toolbox.ImageLoader;

public enum ImageCacheManager {

	INSTANCE;

	// Get max available VM memory, exceeding this amount will throw an
	// OutOfMemory exception. Stored in kilobytes as LruCache takes an
	// int in its constructor.
	static final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	// Use 1/8th of the available memory for this memory cache.
	static final int cacheSize = maxMemory / 8;

	/**
	 * Volley image loader
	 */
	private ImageLoader mImageLoader;

	/**
	 * Image cache implementation
	 */
	private static ImageLoader.ImageCache mImageCache;

	public void init() {
		mImageCache = new BitmapLruImageCache(cacheSize);
		mImageLoader = new ImageLoader(Monet.INSTANCE.getRequestQueue(), mImageCache);
	}

	public ImageLoader.ImageCache getImageCache() {
		return mImageCache;
	}

	public Bitmap getBitmap(String url) {
		try {
			return mImageCache.getBitmap(url);
		} catch (NullPointerException e) {
			throw new IllegalStateException("LRU Cache has not initialized.");
		}
	}

	public void putBitmap(String url, Bitmap bitmap) {
		try {
			mImageCache.putBitmap(url, bitmap);
		} catch (NullPointerException e) {
			throw new IllegalStateException("LRU Cache has not initialized.");
		}
	}


	/**
	 * Executes and image load
	 *
	 * @param url      location of image
	 * @param listener Listener for completion
	 */
	public void getImage(String url, ImageLoader.ImageListener listener) {
		mImageLoader.get(url, listener);
	}

	/**
	 * @return instance of the image loader
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}
}
