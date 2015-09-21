package com.arellomobile.anlib.inject;

import android.os.Bundle;
import android.view.View;

public interface IFragmentInjector
{
	void fragmentAttach();

	void fragmentCreate(Bundle savedInstanceState);

	void fragmentViewCreated(View view, Bundle savedInstatnceState);

	void fragmentActivityCreated(Bundle savedInstanceState);

	void fragmentStart();

	void fragmentResume();

	void fragmentPause();

	void fragmentStop();

	void fragmentDestroyView();

	void fragmentDestroy();

	void fragmentDetach();

	void fragmentSaveInstanceState(Bundle outState);

	public static class BaseFragmentInjector implements IFragmentInjector
	{
		@Override
		public void fragmentAttach()
		{

		}

		@Override
		public void fragmentCreate(Bundle savedInstanceState)
		{

		}

		@Override
		public void fragmentViewCreated(View view, Bundle savedInstatnceState)
		{

		}

		@Override
		public void fragmentActivityCreated(Bundle savedInstanceState)
		{

		}

		@Override
		public void fragmentStart()
		{

		}

		@Override
		public void fragmentResume()
		{

		}

		@Override
		public void fragmentPause()
		{

		}

		@Override
		public void fragmentStop()
		{

		}

		@Override
		public void fragmentDestroyView()
		{

		}

		@Override
		public void fragmentDestroy()
		{

		}

		@Override
		public void fragmentDetach()
		{

		}

		@Override
		public void fragmentSaveInstanceState(Bundle outState)
		{

		}

	}
}