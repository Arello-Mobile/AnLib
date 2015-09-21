package com.arellomobile.anlib.inject;

import android.os.Bundle;

/**
 * Oct 13, 2013
 * 
 * @author denis.mirochnik
 */
public class ActivityInjectManager extends InjectManager<IActivityInjector> implements IActivityInjector
{
	public ActivityInjectManager(IActivityInjector... injectors)
	{
		super(injectors);
	}

	@Override
	public void activityCreate(Bundle savedInstanceSate)
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityCreate(savedInstanceSate);
		}
	}

	@Override
	public void activityPostCreate(Bundle savedInstanceState)
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityPostCreate(savedInstanceState);
		}
	}

	@Override
	public void activityContentChanged()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityContentChanged();
		}
	}

	@Override
	public void activityStart()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityStart();
		}
	}

	@Override
	public void activityResume()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityResume();
		}
	}

	@Override
	public void activityRestart()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityRestart();
		}
	}

	@Override
	public void activityPause()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityPause();
		}
	}

	@Override
	public void activityStop()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityStop();
		}
	}

	@Override
	public void activityDestroy()
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityDestroy();
		}
	}

	@Override
	public void activitySaveInstanceState(Bundle outState)
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activitySaveInstanceState(outState);
		}
	}

	@Override
	public void activityRestoreInstanceState(Bundle savedInstatnceState)
	{
		for (final IActivityInjector injector : getInjectors())
		{
			injector.activityRestoreInstanceState(savedInstatnceState);
		}
	}
}
