package com.arellomobile.anlib.inject;

import android.os.Bundle;

public interface IDialogInjector
{
	void dialogCreate(Bundle savedInstanceState);

	void dialogContentChanged();

	void dialogStart();

	void dialogStop();

	void dialogSaveInstanceState(Bundle outState);

	void dialogRestoreInstanceState(Bundle savedInstanceState);

	public static class BaseDialogInjector implements IDialogInjector
	{
		@Override
		public void dialogCreate(Bundle savedInstanceState)
		{
		}

		@Override
		public void dialogContentChanged()
		{
		}

		@Override
		public void dialogStart()
		{
		}

		@Override
		public void dialogStop()
		{
		}

		@Override
		public void dialogSaveInstanceState(Bundle outState)
		{
		}

		@Override
		public void dialogRestoreInstanceState(Bundle savedInstanceState)
		{
		}
	}
}