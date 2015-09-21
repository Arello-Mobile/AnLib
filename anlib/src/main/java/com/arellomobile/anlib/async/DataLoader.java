package com.arellomobile.anlib.async;

import java.util.concurrent.Executor;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

/**
 * Dec 9, 2014
 *
 * @author denis.mirochnik
 */
public abstract class DataLoader<V> extends AsyncLoader<V>
{
	private final ContentObserver mObserver;
	private final Uri mUri;

	public DataLoader(Context context, Executor executor)
	{
		this(context, executor, null);
	}

	public DataLoader(Context context, Executor executor, Uri uri)
	{
		super(context, executor);

		mUri = uri;
		mObserver = new ForceLoadContentObserver();
	}

	protected boolean isNotifyForDescendents()
	{
		return true;
	}

	protected void onCreate()
	{
		if (mUri != null)
		{
			getContext().getContentResolver().registerContentObserver(mUri, isNotifyForDescendents(), mObserver);
		}
	}

	protected void onDestroy()
	{
		if (mUri != null)
		{
			getContext().getContentResolver().unregisterContentObserver(mObserver);
		}
	}

	protected abstract boolean hasData();

	protected abstract void setData(V data);

	protected abstract V getData();

	protected abstract void releaseData(V data);

	protected abstract void releaseData();

	@Override
	public void deliverResult(V data)
	{
		if (isReset())
		{
			releaseData(data);
		}

		boolean hasData = hasData();
		V oldData = null;

		if (hasData)
		{
			oldData = getData();
		}

		setData(data);

		if (isStarted())
		{
			super.deliverResult(data);
		}

		if (hasData)
		{
			releaseData(oldData);
		}
	}

	@Override
	protected void onStartLoading()
	{
		boolean hasData = hasData();

		if (hasData)
		{
			deliverResult(getData());
		}

		onCreate();

		if (takeContentChanged() || !hasData)
		{
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading()
	{
		cancelLoad();
	}

	@Override
	public void onCanceled(V data)
	{
		super.onCanceled(data);

		releaseData(data);
	}

	@Override
	protected void onReset()
	{
		super.onReset();

		cancelLoad();

		if (hasData())
		{
			releaseData(getData());
			releaseData();
		}

		onDestroy();
	}
}
