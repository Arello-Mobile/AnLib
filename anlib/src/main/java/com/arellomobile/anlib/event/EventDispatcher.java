package com.arellomobile.anlib.event;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import com.arellomobile.anlib.AnLibHandler;

/**
 * Sep 26, 2013
 * 
 * @author denis.mirochnik
 */
public class EventDispatcher<T> implements IEventDispatcher
{
	private final EventDispatcherClient<T> mClient;

	public EventDispatcher(EventDispatcherClient<T> client)
	{
		mClient = requireNotNull(client);
	}

	@Override
	public void dispatchEvent(Event event)
	{
		event = mClient.configureEvent(event);

		for (final T target : mClient.getTargets(event))
		{
			mClient.dispatchToTarget(target, event);
		}
	}

	@Override
	public <A extends Enum<A> & EventAction> void dispatchEvent(A action)
	{
		dispatchEvent(action, null);
	}

	@Override
	public <A extends Enum<A> & EventAction> void dispatchEvent(A action, Object data)
	{
		dispatchEvent(Event.of(action, data));
	}

	@Override
	public void postDispatchEvent(final Event event)
	{
		AnLibHandler.post(new Runnable()
		{

			@Override
			public void run()
			{
				dispatchEvent(event);
			}
		});
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEvent(A action)
	{
		postDispatchEvent(action, null);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEvent(A action, Object data)
	{
		postDispatchEvent(Event.of(action, data));
	}

	@Override
	public void postDispatchEventDelayed(final Event event, long delay)
	{
		AnLibHandler.postDelayed(new Runnable()
		{

			@Override
			public void run()
			{
				dispatchEvent(event);
			}
		}, delay);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, long delay)
	{
		postDispatchEventDelayed(action, null, delay);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, Object data, long delay)
	{
		postDispatchEventDelayed(Event.of(action, data), delay);
	}

	public interface EventDispatcherClient<T>
	{
		Event configureEvent(Event event);

		Iterable<T> getTargets(Event event);

		void dispatchToTarget(T target, Event event);
	}
}
