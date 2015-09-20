package com.alexios.monet;

import android.content.Context;

import com.alexios.monet.cache.ImageCacheManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public enum Monet {

	INSTANCE;

	private RequestQueue mRequestQueue = null;

	private Context mContext = null;

	// Initialises the instance of volley. -- Must always init
	public void init(Context context) {
		if (mContext == null) {
			mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
			createImageCache();
		}
	}

	/**
	 * Create the image cache. Uses on L1 Memory Cache(LRU). By default
	 * is using L2 Disk cache based on HTTP headers
	 */
	private void createImageCache() {
		ImageCacheManager.INSTANCE.init();
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}
}
