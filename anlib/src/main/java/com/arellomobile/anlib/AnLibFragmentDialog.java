package com.arellomobile.anlib;

import static com.arellomobile.anlib.common.Checks.as;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.arellomobile.anlib.event.AbsFragmentDispatcherController;
import com.arellomobile.anlib.event.DialogDispatcherController;
import com.arellomobile.anlib.event.Event;
import com.arellomobile.anlib.event.EventAction;
import com.arellomobile.anlib.event.EventDispatcher;
import com.arellomobile.anlib.event.IEventDispatcher;
import com.arellomobile.anlib.event.OnDialogEventListener;
import com.arellomobile.anlib.inject.DialogInjectManager;
import com.arellomobile.anlib.inject.IDialogInjector;
import com.arellomobile.anlib.inject.injectors.ArgumentInjector;
import com.arellomobile.anlib.inject.injectors.ExtraInjector;
import com.arellomobile.anlib.inject.injectors.SaveStateInjector;
import com.arellomobile.anlib.inject.injectors.ViewInjector;

/**
 * Sep 25, 2013
 * 
 * @author denis.mirochnik
 */
public class AnLibFragmentDialog extends Dialog implements IEventDispatcher
{
	private Fragment mOwnerFragment;

	private volatile IEventDispatcher mEventDispatcher;
	private DialogDispatcherController mDispatcherController;

	private IDialogInjector mDialogInjector;

	public AnLibFragmentDialog(Context context)
	{
		this(context, 0);
	}

	public AnLibFragmentDialog(Context context, int theme)
	{
		super(context, theme);

		mDispatcherController = new DialogDispatcherController(this)
		{
			@Override
			public Event configureEvent(Event event)
			{
				return AnLibFragmentDialog.this.configureEvent(event);
			}
		};

		mEventDispatcher = new EventDispatcher<>(mDispatcherController);

		/*@formatter:off*/
		
		mDialogInjector = new DialogInjectManager(
				ViewInjector.forDialog(this),
				ExtraInjector.forDialog(this),
				ArgumentInjector.forFragmentDialog(this),
				SaveStateInjector.forDialog(this)
				);
		
		/*@formatter:on*/
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mDialogInjector.dialogCreate(savedInstanceState);
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		mDialogInjector.dialogStart();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		mDialogInjector.dialogStop();
	}

	@Override
	public Bundle onSaveInstanceState()
	{
		final Bundle state = super.onSaveInstanceState();

		mDialogInjector.dialogSaveInstanceState(state);

		return state;
	}

	protected Event configureEvent(Event event)
	{
		return event;
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

	public Bundle getArguments()
	{
		final InternalDialogFragment fixedDialogFragment = as(getOwnerFragment(), InternalDialogFragment.class);

		return fixedDialogFragment == null ? null : fixedDialogFragment.getDialogArguments();
	}

	public Fragment getOwnerFragment()
	{
		return mOwnerFragment;
	}

	void setOwnerFragment(Fragment ownerFragment)
	{
		mOwnerFragment = ownerFragment;
	}

	public DialogFragment getDialogFragment()
	{
		return as(getOwnerFragment(), DialogFragment.class);
	}

	public AnLibFragment getAnLibFragment()
	{
		return as(getOwnerFragment(), AnLibFragment.class);
	}

	public AnLibDialogFragment getAnLibDialogFragment()
	{
		return as(getOwnerFragment(), AnLibDialogFragment.class);
	}

	public AnLibActivity getAnLibActivity()
	{
		return as(getOwnerActivity(), AnLibActivity.class);
	}

	public static DialogFragment of(Class<? extends Dialog> clazz)
	{
		return of(clazz, null, 0);
	}

	public static DialogFragment of(Class<? extends Dialog> clazz, Bundle args)
	{
		return of(clazz, args, 0);
	}

	public static DialogFragment of(Class<? extends Dialog> clazz, int theme)
	{
		return of(clazz, null, theme);
	}

	public static DialogFragment of(Class<? extends Dialog> clazz, Bundle args, int theme)
	{
		return InternalDialogFragment.instantiate(clazz, args, theme);
	}

	public final static class InternalDialogFragment extends DialogFragmentLoss implements OnDialogEventListener
	{
		private static final String ARG_DIALOG_CLASS = "argDialogClass";
		private static final String ARG_DIALOG_ARGS = "argDialogArgs";
		private static final String ARG_DIALOG_THEME = "argDialogTheme";

		private volatile IEventDispatcher mEventDispatcher;
		private AbsFragmentDispatcherController<OnDialogEventListener> mDispatcherController;
		private Bundle mDialogArguments;

		@Override
		public void onAttach(Activity activity)
		{
			super.onAttach(activity);
		}

		private Bundle getDialogArguments()
		{
			return mDialogArguments;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			final Bundle arguments = getArguments();

			@SuppressWarnings("unchecked")
			final Class<? extends Dialog> clazz = (Class<? extends Dialog>) arguments.getSerializable(ARG_DIALOG_CLASS);
			final int theme = arguments.getInt(ARG_DIALOG_THEME);
			mDialogArguments = arguments.getBundle(ARG_DIALOG_ARGS);

			Constructor<? extends Dialog> constructor = null;
			Object[] constructorArgs = null;

			try
			{
				if (theme != 0)
				{
					constructor = clazz.getConstructor(Context.class, int.class);
					constructorArgs = new Object[2];
					constructorArgs[0] = getActivity();
					constructorArgs[1] = theme;
				}
				else
				{
					constructor = clazz.getConstructor(Context.class);
					constructorArgs = new Object[1];
					constructorArgs[0] = getActivity();
				}

				final Dialog dialog = constructor.newInstance(constructorArgs);

				if (dialog instanceof AnLibFragmentDialog)
				{
					((AnLibFragmentDialog) dialog).setOwnerFragment(this);
				}

				mDispatcherController = new AbsFragmentDispatcherController<OnDialogEventListener>(this)
				{

					@Override
					public Event configureEvent(Event event)
					{
						return event;
					}

					@Override
					public Iterable<OnDialogEventListener> getTargets(Event event)
					{
						final Object target = findTarget();

						final OnDialogEventListener listener = as(target, OnDialogEventListener.class);

						return listener == null ? Collections.<OnDialogEventListener> emptyList() : Collections.singletonList(listener);
					}

					@Override
					public void dispatchToTarget(final OnDialogEventListener target, final Event event)
					{
						getSource().getActivity().runOnUiThread(new Runnable()
						{

							@Override
							public void run()
							{
								target.onDialogEvent(dialog, event);
							}
						});
					}
				};

				mEventDispatcher = new EventDispatcher<>(mDispatcherController);

				return dialog;
			}
			catch (final NoSuchMethodException e)
			{
				throw new RuntimeException("Cannot instantiate", e);
			}
			catch (final IllegalArgumentException e)
			{
				throw new RuntimeException("Cannot instantiate", e);
			}
			catch (final java.lang.InstantiationException e)
			{
				throw new RuntimeException("Cannot instantiate", e);
			}
			catch (final IllegalAccessException e)
			{
				throw new RuntimeException("Cannot instantiate", e);
			}
			catch (final InvocationTargetException e)
			{
				throw new RuntimeException("Cannot instantiate", e);
			}
		}

		private static InternalDialogFragment instantiate(Class<? extends Dialog> clazz, Bundle args, int theme)
		{
			final Bundle b = new Bundle();

			b.putSerializable(ARG_DIALOG_CLASS, clazz);
			b.putBundle(ARG_DIALOG_ARGS, args);
			b.putInt(ARG_DIALOG_THEME, theme);

			final InternalDialogFragment fragment = new InternalDialogFragment();
			fragment.setArguments(b);

			return fragment;
		}

		@Override
		public void onDialogEvent(Dialog dialog, Event event)
		{
			mEventDispatcher.dispatchEvent(event);
		}
	}
}
