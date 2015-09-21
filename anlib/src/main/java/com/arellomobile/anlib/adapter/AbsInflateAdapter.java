package com.arellomobile.anlib.adapter;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.arellomobile.anlib.common.Views;

/**
 * 04.02.2013
 * 
 * @author denis.mirochnik
 */
public abstract class AbsInflateAdapter extends BaseAdapter
{
	private final Activity mActivity;
	private final Context mContext;
	private final LayoutInflater mInflater;

	public AbsInflateAdapter(Activity activity)
	{
		mActivity = requireNotNull(activity);
		mContext = null;
		mInflater = null;
	}

	public AbsInflateAdapter(Context context)
	{
		mContext = requireNotNull(context);
		mActivity = null;
		mInflater = null;
	}

	public AbsInflateAdapter(LayoutInflater inflater, Context context)
	{
		mInflater = requireNotNull(inflater);
		mContext = requireNotNull(context);
		mActivity = null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		Object holder;

		if (view == null)
		{
			final int resId = getLayoutResId(position);

			if (mActivity != null)
			{
				view = Views.inflate(mActivity, resId, parent, false);
			}
			else if (mInflater != null)
			{
				view = Views.inflate(mInflater, resId, parent, false);
			}
			else if (mContext != null)
			{
				view = Views.inflate(mContext, resId, parent, false);
			}

			holder = createHolder(position, view);

			if (holder == null)
			{
				holder = Views.inject(view, getViewHolder(position));
			}

			initView(position, holder);
		}
		else
		{
			holder = Views.getHolder(view);

			reinitView(position, holder);
		}

		bindView(position, holder);

		return view;
	}

	protected Object createHolder(int position, View view)
	{
		return null;
	}

	protected abstract void initView(int position, Object holder);

	protected void reinitView(int position, Object holder)
	{

	}

	protected abstract void bindView(int position, Object holder);

	protected abstract int getLayoutResId(int position);

	@Override
	public abstract int getItemViewType(int position);

	@Override
	public abstract int getViewTypeCount();

	protected abstract Class<?> getViewHolder(int position);

	public Context getContext()
	{
		return mContext == null ? mActivity : mContext;
	}
}
