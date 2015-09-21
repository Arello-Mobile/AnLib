package com.arellomobile.anlib.event;

/**
 * Jun 20, 2014
 *
 * @author denis.mirochnik
 */
public interface IEventDispatcher
{
	void dispatchEvent(Event event);

	<A extends Enum<A> & EventAction> void dispatchEvent(A action);

	<A extends Enum<A> & EventAction> void dispatchEvent(A action, Object data);

	void postDispatchEvent(Event event);

	<A extends Enum<A> & EventAction> void postDispatchEvent(A action);

	<A extends Enum<A> & EventAction> void postDispatchEvent(A action, Object data);

	void postDispatchEventDelayed(Event event, long delay);

	<A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, long delay);

	<A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, Object data, long delay);
}