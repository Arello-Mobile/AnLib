package com.arellomobile.anlib.inject;

import android.app.IntentService;
import android.content.Intent;

public interface IIntentServiceInjector extends IServiceInjector
{
	void intentServiceHandleIntent(Intent intent);

	public static class BaseIntentServiceInjector extends BaseServiceInjector implements IIntentServiceInjector
	{

		public BaseIntentServiceInjector(IntentService service)
		{
			super(service);
		}

		public IntentService getIntentService()
		{
			return (IntentService) super.getService();
		}

		@Override
		public void intentServiceHandleIntent(Intent intent)
		{
		}
	}
}