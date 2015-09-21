package com.arellomobile.anlib;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * 16.01.2013
 * 
 * @author denis.mirochnik
 */
public class FragmentController implements IFragmentController
{
	private final FragmentManager mManager;

	public FragmentController(FragmentManager manager)
	{
		mManager = requireNotNull(manager);
	}

	@Override
	public Fragment findFragment(String tag)
	{
		return mManager.findFragmentByTag(tag);
	}

	@Override
	public Fragment findFragment(int id)
	{
		return mManager.findFragmentById(id);
	}

	@Override
	public boolean hasFragment(String tag)
	{
		return findFragment(tag) != null;
	}

	@Override
	public boolean hasFragment(int id)
	{
		return findFragment(id) != null;
	}

	@Override
	public void addFragment(String tag, Fragment frag)
	{
		mManager.beginTransaction().add(frag, tag).commit();
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag)
	{
		mManager.beginTransaction().add(frag, tag).commitAllowingStateLoss();
	}

	@Override
	public void addFragment(String tag, Fragment frag, int contId)
	{
		mManager.beginTransaction().add(contId, frag, tag).commit();
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag, int contId)
	{
		mManager.beginTransaction().add(contId, frag, tag).commitAllowingStateLoss();
	}

	@Override
	public void removeFragment(String tag)
	{
		removeFragment(findFragment(tag));
	}

	@Override
	public void removeFragmentLoss(String tag)
	{
		removeFragmentLoss(findFragment(tag));
	}

	@Override
	public void removeFragment(int id)
	{
		removeFragment(findFragment(id));
	}

	@Override
	public void removeFragmentLoss(int id)
	{
		removeFragmentLoss(findFragment(id));
	}

	@Override
	public void removeFragment(Fragment fragment)
	{
		mManager.beginTransaction().remove(requireNotNull(fragment)).commit();
	}

	@Override
	public void removeFragmentLoss(Fragment fragment)
	{
		mManager.beginTransaction().remove(requireNotNull(fragment)).commitAllowingStateLoss();
	}
}
