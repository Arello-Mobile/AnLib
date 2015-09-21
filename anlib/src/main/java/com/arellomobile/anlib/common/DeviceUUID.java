/*
 * Copyright (C) 2012 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arellomobile.anlib.common;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class DeviceUUID
{
	private static final String KEY_DEVICE_UUID = "keyDeviceUuid";
	private static final String KEY_UUID = "keyUuid";

	public static String getAndroidId(Context context)
	{
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

		// see http://code.google.com/p/android/issues/detail?id=10603
		if ("9774d56d682e549c".equals(androidId))
		{
			return null;
		}

		return androidId;
	}

	public static String getDeviceId(Context context)
	{
		if (Permissions.phoneState(context))
		{
			return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		}

		return null;
	}

	public static String getDeviceUuid(Context context)
	{
		final SharedPreferences sharedPreferences = AnLibPrefs.getPrefs(context);

		String androidId = sharedPreferences.getString(KEY_DEVICE_UUID, null);

		if (androidId != null)
		{
			return androidId;
		}

		androidId = getAndroidId(context);

		if (androidId == null)
		{
			androidId = getDeviceId(context);
		}

		if (androidId == null)
		{
			androidId = UUID.randomUUID().toString();
		}

		sharedPreferences.edit().putString(KEY_DEVICE_UUID, androidId).apply();

		return androidId;
	}

	public static String getUuid(Context context)
	{
		final SharedPreferences sharedPreferences = AnLibPrefs.getPrefs(context);

		String deviceId = sharedPreferences.getString(KEY_UUID, null);

		if (null != deviceId)
		{
			return deviceId;
		}

		deviceId = UUID.randomUUID().toString();

		sharedPreferences.edit().putString(KEY_UUID, deviceId).apply();

		return deviceId;

	}

	private DeviceUUID()
	{
	}
}
