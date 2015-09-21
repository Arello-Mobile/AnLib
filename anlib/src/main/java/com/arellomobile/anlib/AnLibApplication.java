package com.arellomobile.anlib;

import android.app.Application;

import com.arellomobile.anlib.async.Async;

/**
 * Jan 26, 2015
 *
 * @author denis.mirochnik
 */
public abstract class AnLibApplication extends Application
{
	private static volatile AnLibApplication sInstance;

	public AnLibApplication()
	{
		sInstance = this;
	}

	@Override
	public final void onCreate()
	{
		super.onCreate();

		onApplicationCreate();

		new Async<Void>()
		{

			@Override
			protected Void doWork()
			{
				onAsyncCreate();

				return null;
			}
		}.doWork();
	}

	public abstract void onApplicationCreate();

	public abstract void onAsyncCreate();

	public static AnLibApplication get()
	{
		return sInstance;
	}
}
