package com.arellomobile.anlib.inject;

import android.os.Bundle;
import android.view.View;

/**
 * Oct 13, 2013
 * 
 * @author denis.mirochnik
 */
//FIXME make dialog fragment injecting
public class FragmentInjectManager extends InjectManager<IFragmentInjector> implements IFragmentInjector
{

	public FragmentInjectManager(IFragmentInjector... injectors)
	{
		super(injectors);
	}

	@Override
	public void fragmentAttach()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentAttach();
		}
	}

	@Override
	public void fragmentCreate(Bundle savedInstanceState)
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentCreate(savedInstanceState);
		}
	}

	@Override
	public void fragmentViewCreated(View view, Bundle savedInstatnceState)
	{
		for (final IFragmentInjector injector : getInjectors())
		{

			injector.fragmentViewCreated(view, savedInstatnceState);
		}
	}

	@Override
	public void fragmentActivityCreated(Bundle savedInstanceState)
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentActivityCreated(savedInstanceState);
		}
	}

	@Override
	public void fragmentStart()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentStart();
		}
	}

	@Override
	public void fragmentResume()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentResume();
		}
	}

	@Override
	public void fragmentPause()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentPause();
		}
	}

	@Override
	public void fragmentStop()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentStop();
		}
	}

	@Override
	public void fragmentDestroyView()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentDestroyView();
		}
	}

	@Override
	public void fragmentDestroy()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentDestroy();
		}
	}

	@Override
	public void fragmentDetach()
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentDetach();
		}
	}

	@Override
	public void fragmentSaveInstanceState(Bundle outState)
	{
		for (final IFragmentInjector injector : getInjectors())
		{
			injector.fragmentSaveInstanceState(outState);
		}
	}
}
