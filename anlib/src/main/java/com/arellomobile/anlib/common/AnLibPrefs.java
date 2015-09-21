package com.arellomobile.anlib.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Jun 3, 2014
 *
 * @author denis.mirochnik
 */
public class AnLibPrefs
{
	private static final String SHARED_PREF_NAME = "com.arellomobile.anlib.prefs";

	public static SharedPreferences getPrefs(Context context)
	{
		return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
	}

	private AnLibPrefs()
	{
	}
}
