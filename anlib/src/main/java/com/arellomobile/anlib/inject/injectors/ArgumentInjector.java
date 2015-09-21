package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.anlib.AnLibFragmentDialog;
import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.IDialogInjector;
import com.arellomobile.anlib.inject.IDialogInjector.BaseDialogInjector;
import com.arellomobile.anlib.inject.IFragmentInjector;
import com.arellomobile.anlib.inject.IFragmentInjector.BaseFragmentInjector;
import com.arellomobile.anlib.inject.IObjectInjector;
import com.arellomobile.anlib.inject.IObjectInjector.InjectorWrapper;
import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.ArgumentInjector.ArgumentsProvider;
import com.arellomobile.anlib.inject.injectors.InjectArgument.InjectArgumentHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class ArgumentInjector extends Injector<InjectArgumentHolder, ArgumentsProvider>
{
	//FIXME fragmentDialog?

	private Bundle mArguments;

	public ArgumentInjector(ArgumentsProvider provider, Object target)
	{
		super(provider, target);
	}

	@Override
	protected FieldsChecker<InjectArgumentHolder> getChecker()
	{
		return ArgumentFieldsChecker.INSTANCE;
	}

	@Override
	public boolean preProcessFields()
	{
		return (mArguments = getProvider().getArguments()) != null;
	}

	@Override
	public void processField(Field field, InjectArgumentHolder data)
	{
		if (isInjecting())
		{
			if (!mArguments.containsKey(data.key))
			{
				if (data.optional)
				{
					return;
				}

				throw new InjectException("Argument not found: " + data.key);
			}

			InjectReflects.setField(field, getTarget(), mArguments.get(data.key));
		}
		else
		{
			InjectReflects.setField(field, getTarget(), null);
		}
	}

	@Override
	public void postProcessFields()
	{
		mArguments = null;
	}

	public interface ArgumentsProvider
	{
		Bundle getArguments();
	}

	public static class FragmentArgumentProvider implements ArgumentsProvider
	{
		private final Fragment mFragment;

		public FragmentArgumentProvider(Fragment fragment)
		{
			mFragment = fragment;
		}

		@Override
		public Bundle getArguments()
		{
			return mFragment.getArguments();
		}
	}

	public static class BundleArgumentProvider implements ArgumentsProvider
	{
		private final Bundle mArguments;

		public BundleArgumentProvider(Bundle arguments)
		{
			mArguments = arguments;
		}

		@Override
		public Bundle getArguments()
		{
			return mArguments;
		}
	}

	public static IFragmentInjector forFragment(Fragment fragment)
	{
		return forFragment(of(fragment));
	}

	public static IObjectInjector forObject(Bundle args, Object target)
	{
		return forObject(of(args, target));
	}

	public static IFragmentInjector forFragment(final ArgumentInjector injector)
	{
		return new BaseFragmentInjector()
		{
			@Override
			public void fragmentAttach()
			{
				injector.inject();
			}

			@Override
			public void fragmentDetach()
			{
				injector.deinject();
			}
		};
	}

	public static IDialogInjector forFragmentDialog(final AnLibFragmentDialog dialog)
	{
		return new BaseDialogInjector()
		{
			private ArgumentInjector mInjector;

			@Override
			public void dialogCreate(Bundle savedInstanceState)
			{
				if (mInjector == null)
				{
					mInjector = ArgumentInjector.of(new FragmentArgumentProvider(dialog.getOwnerFragment()), dialog);
				}

				mInjector.inject();
			}
		};
	}

	public static IObjectInjector forObject(final ArgumentInjector injector)
	{
		return new InjectorWrapper(injector);
	}

	public static ArgumentInjector of(Fragment fragment)
	{
		return new ArgumentInjector(new FragmentArgumentProvider(fragment), fragment);
	}

	public static ArgumentInjector of(Bundle argBundle, Object target)
	{
		return new ArgumentInjector(new BundleArgumentProvider(argBundle), target);
	}

	public static ArgumentInjector of(ArgumentsProvider provider, Object target)
	{
		return new ArgumentInjector(provider, target);
	}
}
