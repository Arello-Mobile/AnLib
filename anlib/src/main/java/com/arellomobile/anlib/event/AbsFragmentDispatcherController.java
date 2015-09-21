package com.arellomobile.anlib.event;

import static com.arellomobile.anlib.common.Checks.as;
import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.support.v4.app.Fragment;

import com.arellomobile.anlib.event.EventDispatcher.EventDispatcherClient;

/**
 * Jun 20, 2014
 *
 * @author denis.mirochnik
 */
public abstract class AbsFragmentDispatcherController<T> implements EventDispatcherClient<T>
{
	private final Fragment mSource;
	private Object mTargetFragement;

	public AbsFragmentDispatcherController(Fragment source)
	{
		mSource = requireNotNull(source);
	}

	public void setTargetFragmentId(int id)
	{
		mTargetFragement = id;
	}

	public void setTargetFragmentTag(String targetFragementTag)
	{
		mTargetFragement = targetFragementTag;
	}

	public Integer getTargetFragmentId()
	{
		return as(mTargetFragement, Integer.class);
	}

	public String getTargetFragmentTag()
	{
		return as(mTargetFragement, String.class);
	}

	public Fragment getSource()
	{
		return mSource;
	}

	protected Object findTarget()
	{
		Object target = null;

		final Boolean targetTypeString = mTargetFragement == null ? null : mTargetFragement instanceof String;

		if (targetTypeString == null)
		{
			final Fragment parentFragment = mSource.getParentFragment();

			if (parentFragment != null)
			{
				target = parentFragment;
			}
			else
			{
				target = mSource.getActivity();
			}
		}
		else
		{
			if (targetTypeString)
			{
				target = findByTag(mSource, (String) mTargetFragement);
			}
			else
			{
				target = findById(mSource, (int) mTargetFragement);
			}
		}
		return target;
	}

	private static Object findByTag(Fragment fragment, String tag)
	{
		while (fragment.getParentFragment() != null)
		{
			fragment = fragment.getParentFragment();

			final Fragment byTag = fragment.getChildFragmentManager().findFragmentByTag(tag);

			if (byTag != null)
			{
				return byTag;
			}
		}

		return null;
	}

	private static Object findById(Fragment fragment, int id)
	{
		while (fragment.getParentFragment() != null)
		{
			fragment = fragment.getParentFragment();

			final Fragment byTag = fragment.getChildFragmentManager().findFragmentById(id);

			if (byTag != null)
			{
				return byTag;
			}
		}

		return null;
	}
}
