package com.arellomobile.anlib.common;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;

/**
 * 19.02.2013
 * 
 * @author denis.mirochnik
 */
public class Themes
{
	//TODO make other getters

	public static Context wrapTheme(Context base, int theme)
	{
		return new ContextThemeWrapper(requireNotNull(base), theme);
	}

	public static Drawable getDrawable(Context context, int attr)
	{
		final TypedArray array = makeArray(context, attr);

		if (array == null)
		{
			return null;
		}

		final Drawable drawable = array.getDrawable(0);

		array.recycle();

		return drawable;
	}

	public static int getResourceId(Context context, int attr, int defValue)
	{
		final TypedArray array = makeArray(context, attr);

		if (array == null)
		{
			return defValue;
		}

		final int resourceId = array.getResourceId(0, defValue);

		array.recycle();

		return resourceId;
	}

	public static ColorStateList getColorStateList(Context context, int attr)
	{
		final TypedArray array = makeArray(context, attr);

		if (array == null)
		{
			return null;
		}

		final ColorStateList colorStateList = array.getColorStateList(0);

		array.recycle();

		return colorStateList;
	}

	public static int getColor(Context context, int attr, int defValue)
	{
		final TypedArray array = makeArray(context, attr);

		if (array == null)
		{
			return defValue;
		}

		final int color = array.getColor(0, defValue);

		array.recycle();

		return color;
	}

	private static TypedArray makeArray(Context context, int attr)
	{
		final int attrs[] = new int[] { attr };

		final TypedArray array = requireNotNull(context).obtainStyledAttributes(attrs);

		if (!array.hasValue(0))
		{
			array.recycle();

			return null;
		}

		return array;
	}

	private Themes()
	{
	}
}
