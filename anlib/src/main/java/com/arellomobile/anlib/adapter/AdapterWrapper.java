package com.arellomobile.anlib.adapter;

import static com.arellomobile.anlib.common.Checks.as;
import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

/**
 * Nov 18, 2013
 * 
 * @author denis.mirochnik
 */
public class AdapterWrapper implements ListAdapter, SpinnerAdapter
{
	private final SpinnerAdapter mSpinnerAdapter;
	private final ListAdapter mListAdapter;
	private final BaseAdapter mBaseAdapter;
	private final Adapter mAdapter;

	public AdapterWrapper(Adapter adapter)
	{
		mAdapter = requireNotNull(adapter);
		mListAdapter = as(adapter, ListAdapter.class);
		mSpinnerAdapter = as(adapter, SpinnerAdapter.class);
		mBaseAdapter = as(adapter, BaseAdapter.class);
	}

	public Adapter getAdapter()
	{
		return mAdapter;
	}

	public ListAdapter getListAdapter()
	{
		return mListAdapter;
	}

	public SpinnerAdapter getSpinnerAdapter()
	{
		return mSpinnerAdapter;
	}

	public BaseAdapter getBaseAdapter()
	{
		return mBaseAdapter;
	}

	public void notifyDataSetChanged()
	{
		if (mBaseAdapter == null)
		{
			throw new RuntimeException();
		}

		mBaseAdapter.notifyDataSetChanged();
	}

	public void notifyDataSetInvalidated()
	{
		if (mBaseAdapter == null)
		{
			throw new RuntimeException();
		}

		mBaseAdapter.notifyDataSetInvalidated();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		if (mSpinnerAdapter == null)
		{
			throw new RuntimeException();
		}

		return mSpinnerAdapter.getDropDownView(position, convertView, parent);
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		if (mListAdapter == null)
		{
			throw new RuntimeException();
		}

		return mListAdapter.areAllItemsEnabled();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		mAdapter.registerDataSetObserver(observer);
	}

	@Override
	public boolean isEnabled(int position)
	{
		if (mListAdapter == null)
		{
			throw new RuntimeException();
		}

		return mListAdapter.isEnabled(position);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		mAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public int getCount()
	{
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position)
	{
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		return mAdapter.getItemId(position);
	}

	@Override
	public boolean hasStableIds()
	{
		return mAdapter.hasStableIds();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return mAdapter.getView(position, convertView, parent);
	}

	@Override
	public int getItemViewType(int position)
	{
		return mAdapter.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount()
	{
		return mAdapter.getViewTypeCount();
	}

	@Override
	public boolean isEmpty()
	{
		return mAdapter.isEmpty();
	}
}
