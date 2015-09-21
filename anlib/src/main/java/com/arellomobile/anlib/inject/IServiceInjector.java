package com.arellomobile.anlib.inject;

import android.app.Service;
import android.content.Intent;

public interface IServiceInjector
{
	void serviceCreate();

	void serviceStartCommand(Intent startCommand, int flags, int startId);

	void serviceBind(Intent bind);

	public static class BaseServiceInjector implements IServiceInjector
	{
		private final Service mService;

		public BaseServiceInjector(Service service)
		{
			mService = service;
		}

		public Service getService()
		{
			return mService;
		}

		@Override
		public void serviceCreate()
		{
		}

		@Override
		public void serviceStartCommand(Intent startCommand, int flags, int startId)
		{
		}

		@Override
		public void serviceBind(Intent bind)
		{
		}
	}
}