package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.IActivityInjector;
import com.arellomobile.anlib.inject.IActivityInjector.BaseActivityInjector;
import com.arellomobile.anlib.inject.IFragmentInjector;
import com.arellomobile.anlib.inject.IFragmentInjector.BaseFragmentInjector;
import com.arellomobile.anlib.inject.IObjectInjector;
import com.arellomobile.anlib.inject.IObjectInjector.InjectorWrapper;
import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectFragmentByTag.InjectFragmentByTagHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class FragmentByTagInjector extends AbsFragmentInjector<InjectFragmentByTagHolder>
{
	public FragmentByTagInjector(FragmentManagerProvider provider, Object target)
	{
		super(provider, target);
	}

	@Override
	protected FieldsChecker<InjectFragmentByTagHolder> getChecker()
	{
		return FragmentByTagFieldsChecker.INSTANCE;
	}

	@Override
	public void processField(Field field, InjectFragmentByTagHolder data)
	{
		if (isInjecting())
		{
			Fragment fragment = getManager().findFragmentByTag(data.tag);

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

	public static IFragmentInjector forFragment(Fragment fragment)
	{
		return forFragment(of(fragment));
	}

	public static IFragmentInjector forFragment(final FragmentByTagInjector injector)
	{
		return new BaseFragmentInjector()
		{
			@Override
			public void fragmentViewCreated(View view, Bundle savedInstatnceState)
			{
				injector.inject();
			}

			@Override
			public void fragmentDestroyView()
			{
				injector.deinject();
			}
		};
	}

	public static IActivityInjector forActivity(final FragmentByTagInjector injector)
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

	public static IObjectInjector forObject(final FragmentByTagInjector injector)
	{
		return new InjectorWrapper(injector);
	}

	public static FragmentByTagInjector of(Fragment fragment)
	{
		return new FragmentByTagInjector(new FragmentFragmentManagerProvider(fragment), fragment);
	}

	public static FragmentByTagInjector of(FragmentActivity activity)
	{
		return new FragmentByTagInjector(new ActivityFragmentManagerProvider(activity), activity);
	}

	public static FragmentByTagInjector of(FragmentManager manager, Object target)
	{
		return new FragmentByTagInjector(new FragmentManagerFragmentManagerProvider(manager), target);
	}

	public static FragmentByTagInjector of(FragmentManagerProvider provider, Object target)
	{
		return new FragmentByTagInjector(provider, target);
	}
}
