package com.arellomobile.anlib;

import android.content.Intent;
import android.os.IBinder;

/**
 * Jun 3, 2014
 *
 * @author denis.mirochnik
 */
public class BindedService extends AnLibService
{
	private volatile boolean mBinded;
	private volatile boolean mDone;
	private boolean mStarted;

	@Override
	public synchronized IBinder onBind(Intent intent)
	{
		mBinded = true;

		if (!mStarted)
		{
			mStarted = true;
			startService(new Intent(this, getClass()));
		}

		return LocalBinder.of(this);
	}

	@Override
	public synchronized void onRebind(Intent intent)
	{
		mBinded = true;

		super.onRebind(intent);
	}

	@Override
	public synchronized boolean onUnbind(Intent intent)
	{
		mBinded = false;

		if (isDone())
		{
			stopSelf();
		}

		return true;
	}

	public synchronized void wordkStarted()
	{
		mDone = false;
	}

	public synchronized void workDone()
	{
		mDone = true;

		if (!isBinded())
		{
			stopSelf();
		}
	}

	public boolean isDone()
	{
		return mDone;
	}

	public boolean isBinded()
	{
		return mBinded;
	}
}
