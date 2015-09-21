package com.arellomobile.anlib.adapter;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 04.02.2013
 * 
 * @author denis.mirochnik
 */
public abstract class InflateBaseAdapter<Holder> extends AbsInflateAdapter
{
	private final Class<Holder> mHolder;
	private final int mLayoutResId;

	public InflateBaseAdapter(Activity activity, int layoutResId, Class<Holder> holder)
	{
		super(activity);

		mLayoutResId = layoutResId;
		mHolder = requireNotNull(holder);
	}

	public InflateBaseAdapter(Context context, int layoutResId, Class<Holder> holder)
	{
		super(context);

		mLayoutResId = layoutResId;
		mHolder = requireNotNull(holder);
	}

	public InflateBaseAdapter(LayoutInflater inflater, Context context, int layoutResId, Class<Holder> holder)
	{
		super(inflater, context);

		mLayoutResId = layoutResId;
		mHolder = requireNotNull(holder);
	}

	@Override
	protected Holder createHolder(int position, View view)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initView(int position, Object holder)
	{
		initHolder(position, (Holder) holder);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void reinitView(int position, Object holder)
	{
		reinitHolder(position, (Holder) holder);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void bindView(int position, Object holder)
	{
		bindHolder(position, (Holder) holder);
	}

	protected void initHolder(int position, Holder holder)
	{
	}

	protected void reinitHolder(int position, Holder holder)
	{
	}

	protected abstract void bindHolder(int position, Holder holder);

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

	@Override
	protected int getLayoutResId(int position)
	{
		return mLayoutResId;
	}

	@Override
	protected Class<Holder> getViewHolder(int position)
	{
		return mHolder;
	}

}
