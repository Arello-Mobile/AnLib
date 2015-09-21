package com.arellomobile.anlib.common;

import android.content.Context;

/**
 * 19.02.2013
 * 
 * @author denis.mirochnik
 */
public class Dimens
{
	private static final float ONE_TO_254 = 1.0f / 25.4f;
	private static final float ONE_TO_72 = 1.0f / 72;

	public static float pxToPt(float px, Context context)
	{
		return px / oneTo72(context);
	}

	public static float pxToIn(float px, Context context)
	{
		return px / xdpi(context);
	}

	public static float pxToMm(float px, Context context)
	{
		return px / oneTo254(context);
	}

	public static float pxToSp(float px, Context context)
	{
		return px / scaledDensity(context);
	}

	public static float pxToDp(float px, Context context)
	{
		return px / density(context);
	}

	public static float dpToPt(float dp, Context context)
	{
		return dp * density(context) * oneTo72(context);
	}

	public static float dpToIn(float dp, Context context)
	{
		return dp * density(context) * xdpi(context);
	}

	public static float doToMm(float dp, Context context)
	{
		return dp * density(context) * oneTo254(context);
	}

	public static float dpToSp(float dp, Context context)
	{
		return dp * density(context) * scaledDensity(context);
	}

	public static float dpToPx(float dp, Context context)
	{
		return dp * density(context);
	}

	public static float spToPx(float sp, Context context)
	{
		return sp * scaledDensity(context);
	}

	private static float density(Context context)
	{
		return context.getResources().getDisplayMetrics().density;
	}

	private static float oneTo72(Context context)
	{
		return xdpi(context) * ONE_TO_72;
	}

	private static float oneTo254(Context context)
	{
		return xdpi(context) * ONE_TO_254;
	}

	private static float xdpi(Context context)
	{
		return context.getResources().getDisplayMetrics().xdpi;
	}

	private static float scaledDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().scaledDensity;
	}

	private Dimens()
	{
	}
}
