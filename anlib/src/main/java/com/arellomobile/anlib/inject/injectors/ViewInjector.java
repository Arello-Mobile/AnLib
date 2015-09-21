package com.arellomobile.anlib.inject.injectors;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.IActivityInjector;
import com.arellomobile.anlib.inject.IActivityInjector.BaseActivityInjector;
import com.arellomobile.anlib.inject.IDialogInjector;
import com.arellomobile.anlib.inject.IFragmentInjector;
import com.arellomobile.anlib.inject.IFragmentInjector.BaseFragmentInjector;
import com.arellomobile.anlib.inject.IObjectInjector;
import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectView.InjectViewHolder;
import com.arellomobile.anlib.inject.injectors.ViewInjector.ViewProvider;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class ViewInjector extends Injector<InjectViewHolder, ViewProvider>
{
	private View mView;

	public ViewInjector(ViewProvider provider, Object target)
	{
		super(provider, target);
	}

	@Override
	protected FieldsChecker<InjectViewHolder> getChecker()
	{
		return ViewFieldsChecker.INSTANCE;
	}

	@Override
	public boolean preProcessFields()
	{
		return (mView = getProvider().getView()) != null;
	}

	@Override
	public void processField(Field field, InjectViewHolder data)
	{
		if (isInjecting())
		{
			View view = mView.findViewById(data.id);

			if (view == null)
			{
				if (data.optional)
				{
					return;
				}

				throw new InjectException("View not found");
			}

			InjectReflects.setField(field, getTarget(), view);
		}
		else
		{
			InjectReflects.setField(field, getTarget(), null);
		}
	}

	@Override
	public void postProcessFields()
	{
		mView = null;
	}

	public interface ViewProvider
	{
		View getView();
	}

	public static class ViewViewProvider implements ViewProvider
	{
		private final View mView;

		public ViewViewProvider(View view)
		{
			mView = requireNotNull(view);
		}

		@Override
		public View getView()
		{
			return mView;
		}
	}

	public static class ActivityViewProvider implements ViewProvider
	{
		private final Activity mActivity;

		public ActivityViewProvider(Activity activity)
		{
			mActivity = requireNotNull(activity);
		}

		@Override
		public View getView()
		{
			return mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
		}
	}

	public static class FragmentViewProvider implements ViewProvider
	{
		private final Fragment mFragment;

		public FragmentViewProvider(Fragment fragment)
		{
			mFragment = requireNotNull(fragment);
		}

		@Override
		public View getView()
		{
			return mFragment.getView();
		}
	}

	public static class DialogViewProvider implements ViewProvider
	{
		private final Dialog mDialog;

		public DialogViewProvider(Dialog dialog)
		{
			mDialog = requireNotNull(dialog);
		}

		@Override
		public View getView()
		{
			return mDialog.getWindow().findViewById(android.R.id.content);
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

	public static IObjectInjector forObject(View view, Object object)
	{
		return forObject(of(view, object));
	}

	public static IDialogInjector forDialog(final ViewInjector injector)
	{
		return new IDialogInjector.BaseDialogInjector()
		{
			@Override
			public void dialogContentChanged()
			{
				injector.inject();
			}
		};
	}

	public static IFragmentInjector forFragment(final ViewInjector injector)
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

	public static IActivityInjector forActivity(final ViewInjector injector)
	{
		return new BaseActivityInjector()
		{
			@Override
			public void activityContentChanged()
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

	public static IObjectInjector forObject(final ViewInjector injector)
	{
		return new IObjectInjector.InjectorWrapper(injector);
	}

	public static ViewInjector of(Dialog dialog)
	{
		return new ViewInjector(new DialogViewProvider(dialog), dialog);
	}

	public static ViewInjector of(Fragment fragment)
	{
		return new ViewInjector(new FragmentViewProvider(fragment), fragment);
	}

	public static ViewInjector of(Activity activity)
	{
		return new ViewInjector(new ActivityViewProvider(activity), activity);
	}

	public static ViewInjector of(View view, Object target)
	{
		return new ViewInjector(new ViewViewProvider(view), target);
	}

	public static ViewInjector of(ViewProvider provider, Object target)
	{
		return new ViewInjector(provider, target);
	}
}
