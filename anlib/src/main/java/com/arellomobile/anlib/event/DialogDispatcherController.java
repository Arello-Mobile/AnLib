package com.arellomobile.anlib.event;

import static com.arellomobile.anlib.common.Checks.as;
import static com.arellomobile.anlib.common.Checks.isMainThread;
import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.util.Collections;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import com.arellomobile.anlib.AnLibFragmentDialog;
import com.arellomobile.anlib.AnLibHandler;
import com.arellomobile.anlib.event.EventDispatcher.EventDispatcherClient;

/**
 * Sep 26, 2013
 * 
 * @author denis.mirochnik
 */
public class DialogDispatcherController implements EventDispatcherClient<OnDialogEventListener>
{
	private final Dialog mSource;

	public DialogDispatcherController(Dialog source)
	{
		mSource = requireNotNull(source);
	}

	@Override
	public Event configureEvent(Event event)
	{
		return event;
	}

	@Override
	public Iterable<OnDialogEventListener> getTargets(Event event)
	{
		final AnLibFragmentDialog fragmentDialog = as(mSource, AnLibFragmentDialog.class);
		final Fragment fragment = fragmentDialog == null ? null : fragmentDialog.getOwnerFragment();

		OnDialogEventListener target = as(fragment, OnDialogEventListener.class);

		if (target == null)
		{
			target = as(mSource.getOwnerActivity(), OnDialogEventListener.class);
		}

		return target == null ? Collections.<OnDialogEventListener> emptyList() : Collections.singletonList(target);
	}

	@Override
	public void dispatchToTarget(final OnDialogEventListener target, final Event event)
	{
		if (isMainThread())
		{
			target.onDialogEvent(mSource, event);
		}
		else
		{
			AnLibHandler.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					target.onDialogEvent(mSource, event);
				}
			});
		}
	}
}
