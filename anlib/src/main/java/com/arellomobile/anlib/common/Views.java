package com.arellomobile.anlib.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.anlib.R;
import com.arellomobile.anlib.inject.injectors.InjectView;
import com.arellomobile.anlib.inject.injectors.ViewInjector;

/**
 * 04.02.2013
 * 
 * @author denis.mirochnik
 */
public class Views
{

	public static final int TAG_VIEW_HOLDER = 459083;

	public static View inflate(Activity activity, int layoutResId)
	{
		return activity.getLayoutInflater().inflate(layoutResId, null);
	}

	public static View inflate(Activity activity, int layoutResId, ViewGroup parent)
	{
		return activity.getLayoutInflater().inflate(layoutResId, parent);
	}

	public static View inflate(Activity activity, int layoutResId, ViewGroup parent, boolean attach)
	{
		return activity.getLayoutInflater().inflate(layoutResId, parent, attach);
	}

	public static View inflate(Context context, int layoutResId)
	{
		return LayoutInflater.from(context).inflate(layoutResId, null);
	}

	public static View inflate(Context context, int layoutResId, ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(layoutResId, parent);
	}

	public static View inflate(Context context, int layoutResId, ViewGroup parent, boolean attach)
	{
		return LayoutInflater.from(context).inflate(layoutResId, parent, attach);
	}

	public static View inflate(LayoutInflater inflater, int layoutResId)
	{
		return inflater.inflate(layoutResId, null);
	}

	public static View inflate(LayoutInflater inflater, int layoutResId, ViewGroup parent)
	{
		return inflater.inflate(layoutResId, parent);
	}

	public static View inflate(LayoutInflater inflater, int layoutResId, ViewGroup parent, boolean attach)
	{
		return inflater.inflate(layoutResId, parent, attach);
	}

	public static View inject(Activity activity, int layoutResId, Object holder)
	{
		final View view = activity.getLayoutInflater().inflate(layoutResId, null);

		inject(view, holder);

		return view;
	}

	public static View inject(Activity activity, int layoutResId, ViewGroup parent, Object holder)
	{
		final View view = activity.getLayoutInflater().inflate(layoutResId, parent);

		inject(view, holder);

		return view;
	}

	public static View inject(Activity activity, int layoutResId, ViewGroup parent, boolean attach, Object holder)
	{
		final View view = activity.getLayoutInflater().inflate(layoutResId, parent, attach);

		inject(view, holder);

		return view;
	}

	public static View inject(Context context, int layoutResId, Object holder)
	{
		final View view = LayoutInflater.from(context).inflate(layoutResId, null);

		inject(view, holder);

		return view;
	}

	public static View inject(Context context, int layoutResId, ViewGroup parent, Object holder)
	{
		final View view = LayoutInflater.from(context).inflate(layoutResId, parent);

		inject(view, holder);

		return view;
	}

	public static View inject(Context context, int layoutResId, ViewGroup parent, boolean attach, Object holder)
	{
		final View view = LayoutInflater.from(context).inflate(layoutResId, parent, attach);

		inject(view, holder);

		return view;
	}

	public static View inject(LayoutInflater inflater, int layoutResId, Object holder)
	{
		final View view = inflater.inflate(layoutResId, null);

		inject(view, holder);

		return view;
	}

	public static View inject(LayoutInflater inflater, int layoutResId, ViewGroup parent, Object holder)
	{
		final View view = inflater.inflate(layoutResId, parent);

		inject(view, holder);

		return view;
	}

	public static View inject(LayoutInflater inflater, int layoutResId, ViewGroup parent, boolean attach, Object holder)
	{
		final View view = inflater.inflate(layoutResId, parent, attach);

		inject(view, holder);

		return view;
	}

	public static <T> T inject(Activity activity, int layoutResId, Class<T> holder)
	{
		return inject(activity.getLayoutInflater().inflate(layoutResId, null), holder);
	}

	public static <T> T inject(Activity activity, int layoutResId, ViewGroup parent, Class<T> holder)
	{
		return inject(activity.getLayoutInflater().inflate(layoutResId, parent), holder);
	}

	public static <T> T inject(Activity activity, int layoutResId, ViewGroup parent, boolean attach, Class<T> holder)
	{
		return inject(activity.getLayoutInflater().inflate(layoutResId, parent, attach), holder);
	}

	public static <T> T inject(Context context, int layoutResId, Class<T> holder)
	{
		return inject(LayoutInflater.from(context).inflate(layoutResId, null), holder);
	}

	public static <T> T inject(Context context, int layoutResId, ViewGroup parent, Class<T> holder)
	{
		return inject(LayoutInflater.from(context).inflate(layoutResId, parent), holder);
	}

	public static <T> T inject(Context context, int layoutResId, ViewGroup parent, boolean attach, Class<T> holder)
	{
		return inject(LayoutInflater.from(context).inflate(layoutResId, parent, attach), holder);
	}

	public static <T> T inject(LayoutInflater inflater, int layoutResId, Class<T> holder)
	{
		return inject(inflater.inflate(layoutResId, null), holder);
	}

	public static <T> T inject(LayoutInflater inflater, int layoutResId, ViewGroup parent, Class<T> holder)
	{
		return inject(inflater.inflate(layoutResId, parent), holder);
	}

	public static <T> T inject(LayoutInflater inflater, int layoutResId, ViewGroup parent, boolean attach, Class<T> holder)
	{
		return inject(inflater.inflate(layoutResId, parent, attach), holder);
	}

	public static <T> T inject(View view, Class<T> holder)
	{
		if (view == null)
		{
			throw new IllegalArgumentException("View cannot be null");
		}

		T object = null;

		try
		{
			object = holder.getConstructor((Class<?>[]) null).newInstance((Object[]) null);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			throw new IllegalArgumentException("Could not instantiate target object", e);
		}

		return inject(view, object, holder);
	}

	@SuppressWarnings("unchecked")
	public static <T> T inject(View view, T holder)
	{
		if (view == null)
		{
			throw new IllegalArgumentException("View cannot be null");
		}

		return inject(view, holder, (Class<T>) holder.getClass());
	}

	private static <T> T inject(View view, T holder, Class<T> clazz)
	{
		setHolder(view, holder);

		ViewInjector.of(view, holder).inject();

		final Field[] declaredFields = clazz.getDeclaredFields();

		for (final Field field : declaredFields)
		{
			field.setAccessible(true);

			if (field.isAnnotationPresent(InjectView.class))
			{
				try
				{
					final Object object = field.get(holder);

					if (object instanceof View)
					{
						final View v = (View) object;

						setHolder(v, holder);
					}
				}
				catch (final IllegalArgumentException e)
				{
				}
				catch (final IllegalAccessException e)
				{
				}
			}
		}

		return holder;
	}

	public static Object getHolder(View view)
	{
		return view.getTag(TAG_VIEW_HOLDER);
	}

	public static void setHolder(View view, Object holder)
	{
		view.setTag(TAG_VIEW_HOLDER, holder);
	}

	private Views()
	{
	}
}
