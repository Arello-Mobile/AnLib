package com.arellomobile.anlib.common;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Apr 23, 2014
 *
 * @author denis.mirochnik
 */
public class Permissions
{
	public static boolean havePermission(Context context, String permission)
	{
		if (requireNotNull(context).checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
		{
			return true;
		}

		return false;
	}

	public static boolean networkState(Context context)
	{
		return havePermission(context, permission.ACCESS_NETWORK_STATE);
	}

	public static boolean readLogs(Context context)
	{
		return Sdks.GE_JELLY_BEAN || havePermission(context, permission.READ_LOGS);
	}

	public static boolean phoneState(Context context)
	{
		return havePermission(context, permission.READ_PHONE_STATE);
	}

	public static boolean systemAlert(Context context)
	{
		return havePermission(context, permission.SYSTEM_ALERT_WINDOW);
	}

	public static boolean writeExternal(Context context)
	{
		return Sdks.GE_KITKAT || havePermission(context, permission.WRITE_EXTERNAL_STORAGE);
	}

	public static boolean internet(Context context)
	{
		return havePermission(context, permission.INTERNET);
	}

	private Permissions()
	{
	}
}
