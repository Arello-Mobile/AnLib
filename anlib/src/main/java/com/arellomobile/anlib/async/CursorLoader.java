package com.arellomobile.anlib.async;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * 13 дек. 2014 г.
 *
 * @author denis.mirochnik
 */
public abstract class CursorLoader extends DataLoader<Cursor>
{
	private Cursor mCursor;

	public CursorLoader(Context context)
	{
		this(context, null);
	}

	public CursorLoader(Context context, Uri uri)
	{
		super(context, Execs.DISK, uri);
	}

	@Override
	protected boolean hasData()
	{
		return mCursor != null;
	}

	@Override
	protected void setData(Cursor data)
	{
		mCursor = data;
	}

	@Override
	protected Cursor getData()
	{
		return mCursor;
	}

	@Override
	protected void releaseData(Cursor data)
	{
		if (data != null)
		{
			data.close();
		}
	}

	@Override
	protected void releaseData()
	{
		mCursor = null;
	}
}
