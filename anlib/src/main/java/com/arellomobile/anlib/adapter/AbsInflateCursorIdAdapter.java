package com.arellomobile.anlib.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.anlib.common.Views;

/**
 * 06.02.2013
 * 
 * @author denis.mirochnik
 */
public abstract class AbsInflateCursorIdAdapter extends CursorIdAdapter
{
	private final Activity mActivity;
	private final LayoutInflater mInflater;

	public AbsInflateCursorIdAdapter(Context context, Cursor c)
	{
		super(context, c);

		mActivity = null;
		mInflater = null;
	}

	public AbsInflateCursorIdAdapter(Context context, Cursor c, String idColumn)
	{
		super(context, c, idColumn);
		mActivity = null;
		mInflater = null;
	}

	public AbsInflateCursorIdAdapter(Activity activity, Cursor c)
	{
		super(activity, c);

		mActivity = activity;
		mInflater = null;
	}

	public AbsInflateCursorIdAdapter(Activity activity, Cursor c, String idColumn)
	{
		super(activity, c, idColumn);

		mActivity = activity;
		mInflater = null;
	}

	public AbsInflateCursorIdAdapter(LayoutInflater inflater, Context context, Cursor c)
	{
		super(context, c);

		mInflater = inflater;
		mActivity = null;
	}

	public AbsInflateCursorIdAdapter(LayoutInflater inflater, Context context, Cursor c, String idColumn)
	{
		super(context, c, idColumn);

		mInflater = inflater;
		mActivity = null;
	}

	@Override
	public final View initView(Context context, Cursor cursor, ViewGroup parent)
	{
		final int resId = getLayoutResId(cursor);

		View view = null;

		if (mActivity != null)
		{
			view = Views.inflate(mActivity, resId, parent, false);
		}
		else if (mInflater != null)
		{
			view = Views.inflate(mInflater, resId, parent, false);
		}
		else if (context != null)
		{
			view = Views.inflate(context, resId, parent, false);
		}

		Object holder = createHolder(cursor, view);

		if (holder == null)
		{
			holder = Views.inject(view, getViewHolder(cursor));
		}

		initView(cursor, holder);

		return view;
	}

	protected Object createHolder(Cursor cursor, View view)
	{
		return null;
	}

	@Override
	public void renitView(View view, Context context, Cursor cursor)
	{
		reinitView(cursor, Views.getHolder(view));
	}

	@Override
	public final void bindView(View view, Context context, Cursor cursor)
	{
		bindView(cursor, Views.getHolder(view));
	}

	protected abstract void initView(Cursor cursor, Object holder);

	protected void reinitView(Cursor cursor, Object holder)
	{

	}

	protected abstract void bindView(Cursor cursor, Object holder);

	protected abstract int getLayoutResId(Cursor cursor);

	@Override
	public abstract int getItemViewType(int position);

	@Override
	public abstract int getViewTypeCount();

	protected abstract Class<?> getViewHolder(Cursor cursor);
}
