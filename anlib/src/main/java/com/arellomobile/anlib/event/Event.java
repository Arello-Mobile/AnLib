package com.arellomobile.anlib.event;

/**
 * Sep 23, 2013
 * 
 * @author denis.mirochnik
 */
public class Event
{
	//TODO make global event dispatching (event bus >_<)

	private final EventAction mActionEvent;
	private final Enum<?> mActionEnum;
	private final Object mData;

	protected <T extends Enum<T> & EventAction> Event(T action, Object data)
	{
		mActionEvent = action;
		mActionEnum = action;
		mData = data;
	}

	public Event(Event event)
	{
		mActionEnum = event.mActionEnum;
		mActionEvent = event.mActionEvent;
		mData = event.mData;
	}

	public Object getData()
	{
		return mData;
	}

	public Enum<?> getEnumAction()
	{
		return mActionEnum;
	}

	public EventAction getAction()
	{
		return mActionEvent;
	}

	public static <T extends Enum<T> & EventAction> Event of(T action)
	{
		return of(action, null);
	}

	public static <T extends Enum<T> & EventAction> Event of(T action, Object data)
	{
		return new Event(action, data);
	}
}
