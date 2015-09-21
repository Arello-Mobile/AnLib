package com.arellomobile.anlib.event;

import static com.arellomobile.anlib.common.Checks.isMainThread;
import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.util.concurrent.CopyOnWriteArraySet;

import android.app.Service;

import com.arellomobile.anlib.AnLibHandler;
import com.arellomobile.anlib.event.EventDispatcher.EventDispatcherClient;

/**
 * Sep 30, 2013
 * 
 * @author denis.mirochnik
 */
public class ServiceDispatcherController implements EventDispatcherClient<OnServiceEventListener>
{
	private final Service mSource;
	private final CopyOnWriteArraySet<OnServiceEventListener> mListeners = new CopyOnWriteArraySet<>();

	public ServiceDispatcherController(Service source)
	{
		mSource = requireNotNull(source);
	}

	public void addListener(OnServiceEventListener listener)
	{
		mListeners.add(listener);
	}

	public void removeListener(OnServiceEventListener listener)
	{
		mListeners.remove(listener);
	}

	@Override
	public Event configureEvent(Event event)
	{
		return event;
	}

	@Override
	public Iterable<OnServiceEventListener> getTargets(Event event)
	{
		return mListeners;
	}

	@Override
	public void dispatchToTarget(final OnServiceEventListener target, final Event event)
	{
		if (isMainThread())
		{
			target.onServiceEvent(mSource, event);
		}
		else
		{
			AnLibHandler.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					target.onServiceEvent(mSource, event);
				}
			});
		}
	}
}
