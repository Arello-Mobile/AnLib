package com.arellomobile.anlib.inject.injectors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.arellomobile.anlib.inject.injectors.AbsFragmentInjector.FragmentManagerProvider;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public abstract class AbsFragmentInjector<T> extends Injector<T, FragmentManagerProvider>
{
	private FragmentManager mManager;

	public AbsFragmentInjector(FragmentManagerProvider provider, Object target)
	{
		super(provider, target);
	}

	protected FragmentManager getManager()
	{
		return mManager;
	}

	@Override
	public boolean preProcessFields()
	{
		return (mManager = getProvider().getFragmentManager()) != null;
	}

	@Override
	public void postProcessFields()
	{
		mManager = null;
	}

	public interface FragmentManagerProvider
	{
		FragmentManager getFragmentManager();
	}

	public static class FragmentManagerFragmentManagerProvider implements FragmentManagerProvider
	{
		private final FragmentManager mManager;

		public FragmentManagerFragmentManagerProvider(FragmentManager manager)
		{
			mManager = manager;
		}

		@Override
		public FragmentManager getFragmentManager()
		{
			return mManager;
		}
	}

	public static class ActivityFragmentManagerProvider implements FragmentManagerProvider
	{
		private final FragmentActivity mActivity;

		public ActivityFragmentManagerProvider(FragmentActivity activity)
		{
			mActivity = activity;
		}

		@Override
		public FragmentManager getFragmentManager()
		{
			return mActivity.getSupportFragmentManager();
		}
	}

	public static class FragmentFragmentManagerProvider implements FragmentManagerProvider
	{
		private final Fragment mFragment;

		public FragmentFragmentManagerProvider(Fragment fragment)
		{
			mFragment = fragment;
		}

		@Override
		public FragmentManager getFragmentManager()
		{
			return mFragment.getChildFragmentManager();
		}
	}
}
