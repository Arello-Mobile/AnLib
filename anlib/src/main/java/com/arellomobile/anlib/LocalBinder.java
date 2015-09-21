package com.arellomobile.anlib;

import android.app.Service;
import android.os.Binder;

public class LocalBinder extends Binder
{
	private final Service mService;

	public LocalBinder(Service service)
	{
		mService = service;
	}

	public Service getService()
	{
		return mService;
	}

	public static Binder of(Service service)
	{
		return new LocalBinder(service);
	}
}