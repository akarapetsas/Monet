package com.alexios.monet;

import android.app.Application;
import android.content.Context;

public class MonetApplication extends Application {
	public static MonetApplication instance;

	public void onCreate() {
		super.onCreate();

		instance = this;
		//Instantiate the volleyRequest once and only once
		// when the application is starting.
		Monet.INSTANCE.init(instance);
	}

	public static Context getContext() {
		return instance;
	}

}
