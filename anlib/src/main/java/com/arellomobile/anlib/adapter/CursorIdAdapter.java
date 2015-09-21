/*
 * Copyright (C) 2011 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arellomobile.anlib.adapter;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.arellomobile.anlib.common.Checks;

public abstract class CursorIdAdapter extends BaseAdapter
{
	private Cursor mCursor;
	private final Context mContext;
	private int mRowIDColumn;
	private String mIdColumn;

	public CursorIdAdapter(Context context, Cursor c)
	{
		mContext = context;

		init(c, "_id");
	}

	public CursorIdAdapter(Context context, Cursor c, String idColumn)
	{
		mContext = context;

		init(c, idColumn);
	}

	private void init(Cursor c, String idColumn)
	{
		mCursor = requireNotNull(c);

		mIdColumn = idColumn;

		if (Checks.isEmpty(mIdColumn))
		{
			mRowIDColumn = -1;
		}
		else
		{
			mRowIDColumn = c.getColumnIndex(mIdColumn);
		}

	}

	public Cursor getCursor()
	{
		return mCursor;
	}

	@Override
	public int getCount()
	{
		if (mCursor != null)
		{
			return mCursor.getCount();
		}

		return 0;
	}

	@Override
	public Cursor getItem(int position)
	{
		if (mCursor != null & mCursor.moveToPosition(position))
		{
			return mCursor;
		}

		return null;
	}

	@Override
	public long getItemId(int position)
	{
		if (mCursor != null & mRowIDColumn != -1 & mCursor.moveToPosition(position))
		{
			return mCursor.getLong(mRowIDColumn);
		}

		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (!mCursor.moveToPosition(position))
		{
			throw new IllegalStateException("Couldn't move cursor to position " + position);
		}

		View v;

		if (convertView == null)
		{
			v = initView(mContext, mCursor, parent);
		}
		else
		{
			v = convertView;

			renitView(v, mContext, mCursor);
		}

		bindView(v, mContext, mCursor);

		return v;
	}

	protected abstract View initView(Context context, Cursor cursor, ViewGroup parent);

	protected abstract void bindView(View view, Context context, Cursor cursor);

	protected void renitView(View view, Context context, Cursor cursor)
	{

	}

	public void changeCursor(Cursor cursor)
	{
		final Cursor old = swapCursor(cursor);

		if (old != null)
		{
			old.close();
		}
	}

	public void changeCursor(Cursor cursor, String idColumn)
	{
		final Cursor old = swapCursor(cursor, idColumn);

		if (old != null)
		{
			old.close();
		}
	}

	public Cursor swapCursor(Cursor newCursor)
	{
		if (newCursor == mCursor)
		{
			return null;
		}

		if (newCursor == null)
		{
			throw new IllegalArgumentException("Cursor can not be null");
		}

		final Cursor oldCursor = mCursor;

		init(newCursor, mIdColumn);

		notifyDataSetChanged();

		return oldCursor;
	}

	public Cursor swapCursor(Cursor newCursor, String idColumn)
	{
		if (newCursor == mCursor)
		{
			return null;
		}

		if (newCursor == null)
		{
			throw new IllegalArgumentException("Cursor can not be null");
		}

		final Cursor oldCursor = mCursor;

		init(newCursor, idColumn);

		notifyDataSetChanged();

		return oldCursor;
	}

	public Context getContext()
	{
		return mContext;
	}
}
