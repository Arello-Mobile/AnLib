package com.arellomobile.anlib.event;

import static com.arellomobile.anlib.common.Checks.as;
import static com.arellomobile.anlib.common.Checks.isMainThread;

import java.util.Collections;

import android.support.v4.app.Fragment;

import com.arellomobile.anlib.AnLibHandler;

/**
 * Sep 26, 2013
 * 
 * @author denis.mirochnik
 */
public class FragmentDispatcherController extends AbsFragmentDispatcherController<OnFragmentEventListener>
{
	public FragmentDispatcherController(Fragment source)
	{
		super(source);
	}

	@Override
	public Event configureEvent(Event event)
	{
		return event;
	}

	@Override
	public Iterable<OnFragmentEventListener> getTargets(Event event)
	{
		final Object target = findTarget();

		final OnFragmentEventListener listener = as(target, OnFragmentEventListener.class);

		return listener == null ? Collections.<OnFragmentEventListener> emptyList() : Collections.singletonList(listener);
	}

	@Override
	public void dispatchToTarget(final OnFragmentEventListener target, final Event event)
	{
		if (isMainThread())
		{
			target.onFragmentEvent(getSource(), event);
		}
		else
		{
			AnLibHandler.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					target.onFragmentEvent(getSource(), event);
				}
			});
		}
	}
}
