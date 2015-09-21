package com.arellomobile.anlib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.arellomobile.anlib.event.Event;
import com.arellomobile.anlib.event.EventAction;
import com.arellomobile.anlib.event.EventDispatcher;
import com.arellomobile.anlib.event.IEventDispatcher;
import com.arellomobile.anlib.event.OnServiceEventListener;
import com.arellomobile.anlib.event.ServiceDispatcherController;

/**
 * Sep 23, 2013
 * 
 * @author denis.mirochnik
 */
public abstract class AnLibService extends Service implements IEventDispatcher
{
	private volatile IEventDispatcher mEventDispatcher;
	private volatile ServiceDispatcherController mServiceDispatcherController;

	@Override
	public void onCreate()
	{
		super.onCreate();

		mServiceDispatcherController = new ServiceDispatcherController(this)
		{
			@Override
			public Event configureEvent(Event event)
			{
				return AnLibService.this.configureEvent(event);
			}
		};

		mEventDispatcher = new EventDispatcher<>(mServiceDispatcherController);
	}

	public void addListener(OnServiceEventListener listener)
	{
		mServiceDispatcherController.addListener(listener);
	}

	public void removeListener(OnServiceEventListener listener)
	{
		mServiceDispatcherController.removeListener(listener);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return LocalBinder.of(this);
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

	protected Event configureEvent(Event event)
	{
		return event;
	}

}
