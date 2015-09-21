package com.arellomobile.anlib.event;

import static com.arellomobile.anlib.common.Checks.isMainThread;

import java.util.concurrent.CopyOnWriteArraySet;

import com.arellomobile.anlib.AnLibHandler;
import com.arellomobile.anlib.event.EventDispatcher.EventDispatcherClient;

/**
 * Feb 3, 2015
 *
 * @author denis.mirochnik
 */
public class GlobalEventDispatcher implements IEventDispatcher
{
	private static class Holder
	{
		private static final GlobalEventDispatcher INSTANCE = new GlobalEventDispatcher();
	}

	/**
	 * Feb 3, 2015
	 *
	 * @author denis.mirochnik
	 */
	private static final class GlobalDispatcherController implements EventDispatcherClient<OnGlobalEventListener>
	{
		private final CopyOnWriteArraySet<OnGlobalEventListener> mListeners = new CopyOnWriteArraySet<>();

		@Override
		public Event configureEvent(Event event)
		{
			return event;
		}

		public void addListener(OnGlobalEventListener listener)
		{
			mListeners.add(listener);
		}

		public void removeListener(OnGlobalEventListener listener)
		{
			mListeners.remove(listener);
		}

		@Override
		public Iterable<OnGlobalEventListener> getTargets(Event event)
		{
			return mListeners;
		}

		@Override
		public void dispatchToTarget(final OnGlobalEventListener target, final Event event)
		{
			if (isMainThread())
			{
				target.onGlobalEvent(event);
			}
			else
			{
				AnLibHandler.post(new Runnable()
				{

					@Override
					public void run()
					{
						target.onGlobalEvent(event);
					}
				});
			}
		}
	}

	private final IEventDispatcher mEventDispatcher;
	private final GlobalDispatcherController mDispatcherClient;

	private GlobalEventDispatcher()
	{
		mDispatcherClient = new GlobalDispatcherController();
		mEventDispatcher = new EventDispatcher<>(mDispatcherClient);
	}

	public static GlobalEventDispatcher get()
	{
		return Holder.INSTANCE;
	}

	public void addListener(OnGlobalEventListener listener)
	{
		mDispatcherClient.addListener(listener);
	}

	public void removeListener(OnGlobalEventListener listener)
	{
		mDispatcherClient.removeListener(listener);
	}

	@Override
	public void dispatchEvent(Event event)
	{
		mEventDispatcher.dispatchEvent(event);
	}

	@Override
	public <A extends Enum<A> & EventAction> void dispatchEvent(A action)
	{
		mEventDispatcher.dispatchEvent(action);
	}

	@Override
	public <A extends Enum<A> & EventAction> void dispatchEvent(A action, Object data)
	{
		mEventDispatcher.dispatchEvent(action, data);
	}

	@Override
	public void postDispatchEvent(Event event)
	{
		mEventDispatcher.postDispatchEvent(event);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEvent(A action)
	{
		mEventDispatcher.postDispatchEvent(action);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEvent(A action, Object data)
	{
		mEventDispatcher.postDispatchEvent(action, data);
	}

	@Override
	public void postDispatchEventDelayed(Event event, long delay)
	{
		mEventDispatcher.postDispatchEventDelayed(event, delay);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, long delay)
	{
		mEventDispatcher.postDispatchEventDelayed(action, delay);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, Object data, long delay)
	{
		mEventDispatcher.postDispatchEventDelayed(action, data, delay);
	}
}
