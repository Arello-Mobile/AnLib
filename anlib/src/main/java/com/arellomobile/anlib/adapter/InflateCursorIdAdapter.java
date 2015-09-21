package com.arellomobile.anlib.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 06.02.2013
 * 
 * @author denis.mirochnik
 */
public abstract class InflateCursorIdAdapter<Holder> extends AbsInflateCursorIdAdapter
{
	private final Class<Holder> mHolder;
	private final int mLayoutResId;

	public InflateCursorIdAdapter(Activity activity, Cursor c, String idColumn, int layoutResId, Class<Holder> holder)
	{
		super(activity, c, idColumn);

		mLayoutResId = layoutResId;
		mHolder = holder;
	}

	public InflateCursorIdAdapter(Activity activity, Cursor c, int layoutResId, Class<Holder> holder)
	{
		super(activity, c);

		mLayoutResId = layoutResId;
		mHolder = holder;
	}

	public InflateCursorIdAdapter(Context context, Cursor c, String idColumn, int layoutResId, Class<Holder> holder)
	{
		super(context, c, idColumn);

		mLayoutResId = layoutResId;
		mHolder = holder;
	}

	public InflateCursorIdAdapter(Context context, Cursor c, int layoutResId, Class<Holder> holder)
	{
		super(context, c);

		mLayoutResId = layoutResId;
		mHolder = holder;
	}

	public InflateCursorIdAdapter(LayoutInflater inflater, Context context, Cursor c, String idColumn, int layoutResId, Class<Holder> holder)
	{
		super(inflater, context, c, idColumn);

		mLayoutResId = layoutResId;
		mHolder = holder;
	}

	public InflateCursorIdAdapter(LayoutInflater inflater, Context context, Cursor c, int layoutResId, Class<Holder> holder)
	{
		super(inflater, context, c);

		mLayoutResId = layoutResId;
		mHolder = holder;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initView(Cursor cursor, Object holder)
	{
		initHolder(cursor, (Holder) holder);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void bindView(Cursor cursor, Object holder)
	{
		bindHolder(cursor, (Holder) holder);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void reinitView(Cursor cursor, Object holder)
	{
		postIniteHolder(cursor, (Holder) holder);
	}

	@Override
	protected Holder createHolder(Cursor cursor, View view)
	{
		return null;
	}

	@Override
	protected int getLayoutResId(Cursor cursor)
	{
		return mLayoutResId;
	}

	@Override
	protected Class<?> getViewHolder(Cursor cursor)
	{
		return mHolder;
	}

	protected void initHolder(Cursor cursor, Holder holder)
	{
	}

	protected void postIniteHolder(Cursor cursor, Holder holder)
	{

	}

	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		return 0;
	}

	protected abstract void bindHolder(Cursor cursor, Holder holder);
}
