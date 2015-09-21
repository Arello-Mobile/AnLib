package com.arellomobile.anlib.async;

import java.util.concurrent.Executor;

import android.content.Context;
import android.net.Uri;

/**
 * 13 дек. 2014 г.
 *
 * @author denis.mirochnik
 */
public abstract class SimpleDataLoader<V> extends DataLoader<V>
{
	private V mData;

	public SimpleDataLoader(Context context, Executor executor, Uri uri)
	{
		super(context, executor, uri);
	}

	public SimpleDataLoader(Context context, Executor executor)
	{
		super(context, executor);
	}

	@Override
	protected boolean hasData()
	{
		return mData != null;
	}

	@Override
	protected void setData(V data)
	{
		mData = data;
	}

	@Override
	protected V getData()
	{
		return mData;
	}

	@Override
	protected void releaseData()
	{
		mData = null;
	}
}
