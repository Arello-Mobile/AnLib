package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.IActivityInjector;
import com.arellomobile.anlib.inject.IActivityInjector.BaseActivityInjector;
import com.arellomobile.anlib.inject.IObjectInjector;
import com.arellomobile.anlib.inject.IObjectInjector.InjectorWrapper;
import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectFragment.InjectFragmentHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class FragmentInjector extends AbsFragmentInjector<InjectFragmentHolder>
{
	public FragmentInjector(FragmentManagerProvider provider, Object target)
	{
		super(provider, target);
	}

	@Override
	protected FieldsChecker<InjectFragmentHolder> getChecker()
	{
		return FragmentFieldsChecker.INSTANCE;
	}

	@Override
	public void processField(Field field, InjectFragmentHolder data)
	{
		if (isInjecting())
		{
			Fragment fragment = getManager().findFragmentById(data.id);

			if (fragment == null)
			{
				if (data.optional)
				{
					return;
				}

				throw new InjectException("Fragment not found");
			}

			InjectReflects.setField(field, getTarget(), fragment);
		}
		else
		{
			InjectReflects.setField(field, getTarget(), null);
		}
	}

	public static IActivityInjector forActivity(FragmentActivity activity)
	{
		return forActivity(of(activity));
	}

	public static IObjectInjector forObject(FragmentManager manager, Object target)
	{
		return forObject(of(manager, target));
	}

	public static IActivityInjector forActivity(final FragmentInjector injector)
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

	public static IObjectInjector forObject(final FragmentInjector injector)
	{
		return new InjectorWrapper(injector);
	}

	public static FragmentInjector of(FragmentActivity activity)
	{
		return new FragmentInjector(new ActivityFragmentManagerProvider(activity), activity);
	}

	public static FragmentInjector of(FragmentManager manager, Object target)
	{
		return new FragmentInjector(new FragmentManagerFragmentManagerProvider(manager), target);
	}

	public static FragmentInjector of(FragmentManagerProvider provider, Object target)
	{
		return new FragmentInjector(provider, target);
	}
}
