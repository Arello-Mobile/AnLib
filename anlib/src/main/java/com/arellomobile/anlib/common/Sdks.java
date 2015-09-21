package com.arellomobile.anlib.common;

import android.os.Build;

public class Sdks
{
	public static final boolean GE_ICE_CREAM_SANDWICH_MR1 = ge(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
	public static final boolean GE_JELLY_BEAN = ge(Build.VERSION_CODES.JELLY_BEAN);
	public static final boolean GE_JELLY_BEAN_MR1 = ge(Build.VERSION_CODES.JELLY_BEAN_MR1);
	public static final boolean GE_JELLY_BEAN_MR2 = ge(Build.VERSION_CODES.JELLY_BEAN_MR2);
	public static final boolean GE_KITKAT = ge(Build.VERSION_CODES.KITKAT);
	public static final boolean GE_KITKAT_WATCH = ge(Build.VERSION_CODES.KITKAT_WATCH);
	public static final boolean GE_LOLLIPOP = ge(Build.VERSION_CODES.LOLLIPOP);

	public static final boolean GE_SDK_15 = GE_ICE_CREAM_SANDWICH_MR1;
	public static final boolean GE_SDK_16 = GE_JELLY_BEAN;
	public static final boolean GE_SDK_17 = GE_JELLY_BEAN_MR1;
	public static final boolean GE_SDK_18 = GE_JELLY_BEAN_MR2;
	public static final boolean GE_SDK_19 = GE_KITKAT;
	public static final boolean GE_SDK_20 = GE_KITKAT_WATCH;
	public static final boolean GE_SDK_21 = GE_LOLLIPOP;

	public static final boolean L_ICE_CREAM_SANDWICH_MR1 = l(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
	public static final boolean L_JELLY_BEAN = l(Build.VERSION_CODES.JELLY_BEAN);
	public static final boolean L_JELLY_BEAN_MR1 = l(Build.VERSION_CODES.JELLY_BEAN_MR1);
	public static final boolean L_JELLY_BEAN_MR2 = l(Build.VERSION_CODES.JELLY_BEAN_MR2);
	public static final boolean L_KITKAT = l(Build.VERSION_CODES.KITKAT);
	public static final boolean L_KITKAT_WATCH = l(Build.VERSION_CODES.KITKAT_WATCH);
	public static final boolean L_LOLLIPOP = l(Build.VERSION_CODES.LOLLIPOP);

	public static final boolean L_SDK_15 = L_ICE_CREAM_SANDWICH_MR1;
	public static final boolean L_SDK_16 = L_JELLY_BEAN;
	public static final boolean L_SDK_17 = L_JELLY_BEAN_MR1;
	public static final boolean L_SDK_18 = L_JELLY_BEAN_MR2;
	public static final boolean L_SDK_19 = L_KITKAT;
	public static final boolean L_SDK_20 = L_KITKAT_WATCH;
	public static final boolean L_SDK_21 = L_LOLLIPOP;

	public static final boolean EQ_ICE_CREAM_SANDWICH_MR1 = eq(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
	public static final boolean EQ_JELLY_BEAN = eq(Build.VERSION_CODES.JELLY_BEAN);
	public static final boolean EQ_JELLY_BEAN_MR1 = eq(Build.VERSION_CODES.JELLY_BEAN_MR1);
	public static final boolean EQ_JELLY_BEAN_MR2 = eq(Build.VERSION_CODES.JELLY_BEAN_MR2);
	public static final boolean EQ_KITKAT = eq(Build.VERSION_CODES.KITKAT);
	public static final boolean EQ_KITKAT_WATCH = eq(Build.VERSION_CODES.KITKAT_WATCH);
	public static final boolean EQ_LOLLIPOP = eq(Build.VERSION_CODES.LOLLIPOP);

	public static final boolean EQ_SDK_15 = EQ_ICE_CREAM_SANDWICH_MR1;
	public static final boolean EQ_SDK_16 = EQ_JELLY_BEAN;
	public static final boolean EQ_SDK_17 = EQ_JELLY_BEAN_MR1;
	public static final boolean EQ_SDK_18 = EQ_JELLY_BEAN_MR2;
	public static final boolean EQ_SDK_19 = EQ_KITKAT;

	public static boolean ge(int version)
	{
		return Build.VERSION.SDK_INT >= version;
	}

	public static boolean l(int version)
	{
		return Build.VERSION.SDK_INT < version;
	}

	public static boolean eq(int version)
	{
		return Build.VERSION.SDK_INT == version;
	}

	private Sdks()
	{
	}
}