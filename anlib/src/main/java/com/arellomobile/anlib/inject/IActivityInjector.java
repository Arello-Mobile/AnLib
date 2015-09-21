package com.arellomobile.anlib.inject;

import android.os.Bundle;

public interface IActivityInjector
{
	void activityCreate(Bundle savedInstanceState);

	void activityPostCreate(Bundle savedInstanceState);

	void activityContentChanged();

	void activityStart();

	void activityResume();

	void activityRestart();

	void activityPause();

	void activityStop();

	void activityDestroy();

	void activitySaveInstanceState(Bundle outState);

	void activityRestoreInstanceState(Bundle savedInstatnceState);

	public static class BaseActivityInjector implements IActivityInjector
	{
		@Override
		public void activityCreate(Bundle savedInstanceSate)
		{
		}

		@Override
		public void activityPostCreate(Bundle savedInstanceState)
		{
		}

		@Override
		public void activityContentChanged()
		{
		}

		@Override
		public void activityStart()
		{
		}

		@Override
		public void activityResume()
		{
		}

		@Override
		public void activityRestart()
		{
		}

		@Override
		public void activityPause()
		{
		}

		@Override
		public void activityStop()
		{
		}

		@Override
		public void activityDestroy()
		{
		}

		@Override
		public void activitySaveInstanceState(Bundle outState)
		{
		}

		@Override
		public void activityRestoreInstanceState(Bundle savedInstatnceState)
		{
		}
	}
}