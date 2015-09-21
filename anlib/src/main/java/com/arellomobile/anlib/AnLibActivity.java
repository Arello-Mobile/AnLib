package com.arellomobile.anlib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.arellomobile.anlib.inject.ActivityInjectManager;
import com.arellomobile.anlib.inject.IActivityInjector;
import com.arellomobile.anlib.inject.injectors.ExtraInjector;
import com.arellomobile.anlib.inject.injectors.FragmentByTagInjector;
import com.arellomobile.anlib.inject.injectors.FragmentInjector;
import com.arellomobile.anlib.inject.injectors.SaveStateInjector;
import com.arellomobile.anlib.inject.injectors.ViewInjector;

/**
 * Sep 23, 2013
 * 
 * @author denis.mirochnik
 */
public class AnLibActivity extends FragmentActivity implements IFragmentController
{
	private IFragmentController mFragmentHelper;
	private IActivityInjector mActivityInjector;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/*@formatter:off*/
		
		mActivityInjector = new ActivityInjectManager(
				ViewInjector.forActivity(this),
				FragmentInjector.forActivity(this),
				FragmentByTagInjector.forActivity(this),
				ExtraInjector.forActivity(this),
				SaveStateInjector.forActivity(this));
		
		/*@formatter:on*/

		mFragmentHelper = new FragmentController(getSupportFragmentManager());

		mActivityInjector.activityCreate(savedInstanceState);

		getLoaderManager();
	}

	@Override
	public void onContentChanged()
	{
		super.onContentChanged();

		mActivityInjector.activityContentChanged();
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		mActivityInjector.activityStart();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		mActivityInjector.activityResume();
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();

		mActivityInjector.activityRestart();
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		mActivityInjector.activityPause();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		mActivityInjector.activityStop();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		mActivityInjector.activityDestroy();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		mActivityInjector.activityRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		mActivityInjector.activitySaveInstanceState(outState);
	}

	@Override
	public Fragment findFragment(String tag)
	{
		return mFragmentHelper.findFragment(tag);
	}

	@Override
	public Fragment findFragment(int id)
	{
		return mFragmentHelper.findFragment(id);
	}

	@Override
	public boolean hasFragment(String tag)
	{
		return mFragmentHelper.hasFragment(tag);
	}

	@Override
	public boolean hasFragment(int id)
	{
		return mFragmentHelper.hasFragment(id);
	}

	@Override
	public void addFragment(String tag, Fragment frag)
	{
		mFragmentHelper.addFragment(tag, frag);
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag)
	{
		mFragmentHelper.addFragmentLoss(tag, frag);
	}

	@Override
	public void addFragment(String tag, Fragment frag, int contId)
	{
		mFragmentHelper.addFragment(tag, frag, contId);
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag, int contId)
	{
		mFragmentHelper.addFragmentLoss(tag, frag, contId);
	}

	@Override
	public void removeFragment(String tag)
	{
		mFragmentHelper.removeFragment(tag);
	}

	@Override
	public void removeFragmentLoss(String tag)
	{
		mFragmentHelper.removeFragmentLoss(tag);
	}

	@Override
	public void removeFragment(int id)
	{
		mFragmentHelper.removeFragment(id);
	}

	@Override
	public void removeFragmentLoss(int id)
	{
		mFragmentHelper.removeFragmentLoss(id);
	}

	@Override
	public void removeFragment(Fragment fragment)
	{
		mFragmentHelper.removeFragment(fragment);
	}

	@Override
	public void removeFragmentLoss(Fragment fragment)
	{
		mFragmentHelper.removeFragmentLoss(fragment);
	}
}
