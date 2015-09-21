package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.IActivityInjector;
import com.arellomobile.anlib.inject.IActivityInjector.BaseActivityInjector;
import com.arellomobile.anlib.inject.IDialogInjector;
import com.arellomobile.anlib.inject.IDialogInjector.BaseDialogInjector;
import com.arellomobile.anlib.inject.IFragmentInjector;
import com.arellomobile.anlib.inject.IFragmentInjector.BaseFragmentInjector;
import com.arellomobile.anlib.inject.IObjectInjector;
import com.arellomobile.anlib.inject.IObjectInjector.InjectorWrapper;
import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.ExtraInjector.ExtrasProvider;
import com.arellomobile.anlib.inject.injectors.InjectExtra.InjectExtraHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class ExtraInjector extends Injector<InjectExtraHolder, ExtrasProvider>
{
	private Bundle mExtras;

	public ExtraInjector(ExtrasProvider provider, Object target)
	{
		super(provider, target);
	}

	@Override
	protected FieldsChecker<InjectExtraHolder> getChecker()
	{
		return ExtraFieldsChecker.INSTANCE;
	}

	@Override
	public boolean preProcessFields()
	{
		return (mExtras = getProvider().getExtras()) != null;
	}

	@Override
	public void processField(Field field, InjectExtraHolder data)
	{
		if (isInjecting())
		{
			if (!mExtras.containsKey(data.key))
			{
				if (data.optional)
				{
					return;
				}

				throw new InjectException("Extra not found: " + data.key);
			}

			InjectReflects.setField(field, getTarget(), mExtras.get(data.key));
		}
		else
		{
			InjectReflects.setField(field, getTarget(), null);
		}
	}

	@Override
	public void postProcessFields()
	{
		mExtras = null;
	}

	public interface ExtrasProvider
	{
		Bundle getExtras();
	}

	public static class BundleExtrasProvider implements ExtrasProvider
	{
		private final Bundle mExtras;

		public BundleExtrasProvider(Bundle extras)
		{
			mExtras = extras;
		}

		@Override
		public Bundle getExtras()
		{
			return mExtras;
		}
	}

	public static class ActivityExtrasProvider implements ExtrasProvider
	{
		private final Activity mActivity;

		public ActivityExtrasProvider(Activity activity)
		{
			mActivity = activity;
		}

		@Override
		public Bundle getExtras()
		{
			return mActivity.getIntent().getExtras();
		}
	}

	public static class FragmentExtrasProvider implements ExtrasProvider
	{
		private final Fragment mFragment;

		public FragmentExtrasProvider(Fragment fragment)
		{
			mFragment = fragment;
		}

		@Override
		public Bundle getExtras()
		{
			return mFragment.getActivity().getIntent().getExtras();
		}
	}

	public static class DialogExtrasProvider implements ExtrasProvider
	{
		private final Dialog mDialog;

		public DialogExtrasProvider(Dialog dialog)
		{
			mDialog = dialog;
		}

		@Override
		public Bundle getExtras()
		{
			return mDialog.getOwnerActivity().getIntent().getExtras();
		}
	}

	public static IDialogInjector forDialog(Dialog dialog)
	{
		return forDialog(of(dialog));
	}

	public static IFragmentInjector forFragment(Fragment fragment)
	{
		return forFragment(of(fragment));
	}

	public static IActivityInjector forActivity(Activity activity)
	{
		return forActivity(of(activity));
	}

	public static IObjectInjector forObject(Bundle extras, Object target)
	{
		return forObject(of(extras, target));
	}

	public static IDialogInjector forDialog(final ExtraInjector injector)
	{
		return new BaseDialogInjector()
		{
			@Override
			public void dialogCreate(Bundle savedInstanceState)
			{
				injector.inject();
			}
		};
	}

	public static IFragmentInjector forFragment(final ExtraInjector injector)
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

	public static IActivityInjector forActivity(final ExtraInjector injector)
	{
		return new BaseActivityInjector()
		{
			@Override
			public void activityCreate(Bundle savedInstanceSate)
			{
				injector.inject();
			}

			@Override
			public void activityDestroy()
			{
				injector.deinject();
			}
		};
	}

	public static IObjectInjector forObject(final ExtraInjector injector)
	{
		return new InjectorWrapper(injector);
	}

	public static ExtraInjector of(Dialog dialog)
	{
		return new ExtraInjector(new DialogExtrasProvider(dialog), dialog);
	}

	public static ExtraInjector of(Fragment fragment)
	{
		return new ExtraInjector(new FragmentExtrasProvider(fragment), fragment);
	}

	public static ExtraInjector of(Activity activity)
	{
		return new ExtraInjector(new ActivityExtrasProvider(activity), activity);
	}

	public static ExtraInjector of(Bundle extars, Object target)
	{
		return new ExtraInjector(new BundleExtrasProvider(extars), target);
	}

	public static ExtraInjector of(ExtrasProvider provider, Object target)
	{
		return new ExtraInjector(provider, target);
	}
}
