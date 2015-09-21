package com.arellomobile.anlib.common;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.util.EnumSet;
import java.util.Set;

import android.os.Bundle;

/**
 * 06.02.2013
 * 
 * @author denis.mirochnik
 */
public class Stater<T extends Enum<T>>
{
	private static final String KEY_STATE = "anlib.Stater.state";
	private static final String KEY_CLASS = "anlib.Stater.class";

	private final EnumSet<T> mSet;
	private final Class<T> mEnumClass;
	private Runnable mCompleteRunnable;
	private OnStateChangeListener<T> mListener;

	private boolean mIngoreDuplicates = true;

	private Stater(Class<T> enumClass, EnumSet<T> set)
	{
		mEnumClass = enumClass;
		mSet = set;
	}

	public Stater(Class<T> enumClass)
	{
		mEnumClass = requireNotNull(enumClass);
		mSet = EnumSet.noneOf(enumClass);
	}

	public boolean isIngoreDuplicates()
	{
		return mIngoreDuplicates;
	}

	public void setIngoreDuplicates(boolean ingoreDuplicates)
	{
		mIngoreDuplicates = ingoreDuplicates;
	}

	public Runnable getCompleteRunnable()
	{
		return mCompleteRunnable;
	}

	public void setCompleteRunnable(Runnable runnable)
	{
		mCompleteRunnable = runnable;
	}

	public OnStateChangeListener<T> getOnStateChangeListener()
	{
		return mListener;
	}

	public void setOnStateChangeListener(OnStateChangeListener<T> listener)
	{
		mListener = listener;
	}

	public void saveState(Bundle bundle)
	{
		requireNotNull(bundle).putSerializable(KEY_STATE, mSet);
		bundle.putSerializable(KEY_CLASS, mEnumClass);
	}

	public static <E extends Enum<E>> Stater<E> restoreState(Bundle bundle)
	{
		@SuppressWarnings("unchecked")
		final EnumSet<E> set = (EnumSet<E>) requireNotNull(bundle.getSerializable(KEY_STATE));

		@SuppressWarnings("unchecked")
		final Class<E> clazz = (Class<E>) requireNotNull(bundle.getSerializable(KEY_CLASS));

		if (!clazz.isEnum())
		{
			throw new IllegalStateException("Not enum clazz");
		}

		for (final Enum<E> en : set)
		{
			clazz.cast(en);
		}

		return new Stater<>(clazz, set);
	}

	public void actionUncomplete(T actionName)
	{
		if (!mSet.contains(requireNotNull(actionName)) && !mIngoreDuplicates)
		{
			return;
		}

		mSet.remove(actionName);

		onStateChange(actionName);

		if (mListener != null)
		{
			mListener.onStateChange(this, actionName);
		}

		if (mCompleteRunnable != null && isCompleted())
		{
			mCompleteRunnable.run();
		}
	}

	public void actionComplete(T actionName)
	{
		if (mSet.contains(requireNotNull(actionName)) && !mIngoreDuplicates)
		{
			return;
		}

		mSet.add(actionName);

		onStateChange(actionName);

		if (mListener != null)
		{
			mListener.onStateChange(this, actionName);
		}

		if (mCompleteRunnable != null && isCompleted())
		{
			mCompleteRunnable.run();
		}
	}

	public boolean checkActions(@SuppressWarnings("unchecked") T... set)
	{
		if (set == null)
		{
			return mSet.isEmpty();
		}

		for (final T en : set)
		{
			if (!mSet.contains(en))
			{
				return false;
			}
		}

		return true;
	}

	public boolean checkActions(Set<T> set)
	{
		if (set == null)
		{
			return mSet.isEmpty();
		}

		return mSet.containsAll(set);
	}

	protected void onStateChange(T changedAction)
	{
	}

	public boolean isCompleted()
	{
		return mSet.size() == mEnumClass.getEnumConstants().length;
	}

	public interface OnStateChangeListener<T extends Enum<T>>
	{
		void onStateChange(Stater<T> stater, T changedAction);
	}
}
