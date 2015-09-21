package com.arellomobile.anlib.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Implementation of {@link PagerAdapter} which uses {@link View} for each item.<br>
 * Also view's are reused so works like {@link BaseAdapter}
 * <p>
 * 29.09.2012
 * 
 * @author denis.mirochnik
 */
public abstract class ViewPagerAdapter extends PagerAdapter
{
	private final ArrayList<WeakReference<View>> mFreeViews;

	public ViewPagerAdapter()
	{
		mFreeViews = new ArrayList<>();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		View view = null;

		if (mFreeViews.size() != 0)
		{
			view = mFreeViews.remove(0).get();
		}

		view = getView(container, view, position);

		container.addView(view);

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView((View) object);
		mFreeViews.add(new WeakReference<>((View) object));
	}

	protected abstract View getView(ViewGroup parent, View view, int position);

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}
}
