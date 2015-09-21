package com.arellomobile.anlib;

import java.lang.reflect.Field;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Jun 20, 2014
 *
 * @author denis.mirochnik
 */
public class DialogFragmentLoss extends DialogFragment
{
	private Field mDismissedField;
	private Field mShownByMeField;
	private Field mViewDestroyedField;
	private Field mBackStackIdField;

	public DialogFragmentLoss()
	{
		final Class<DialogFragment> clazz = DialogFragment.class;

		try
		{
			mDismissedField = clazz.getDeclaredField("mDismissed");
			mDismissedField.setAccessible(true);

			mShownByMeField = clazz.getDeclaredField("mShownByMe");
			mShownByMeField.setAccessible(true);

			mViewDestroyedField = clazz.getDeclaredField("mViewDestroyed");
			mViewDestroyedField.setAccessible(true);

			mBackStackIdField = clazz.getDeclaredField("mBackStackId");
			mBackStackIdField.setAccessible(true);
		}
		catch (final NoSuchFieldException e)
		{
		}
	}

	public void showAllowingStateLoss(FragmentManager manager, String tag)
	{
		try
		{
			mDismissedField.set(this, false);
			mShownByMeField.set(this, true);
		}
		catch (final Throwable e)
		{
		}

		final FragmentTransaction ft = manager.beginTransaction();
		ft.add(this, tag);
		ft.commitAllowingStateLoss();
	}

	public int showAllowingStateLoss(FragmentTransaction transaction, String tag)
	{
		try
		{
			mDismissedField.set(this, false);
			mShownByMeField.set(this, true);
		}
		catch (final Throwable e)
		{
		}

		transaction.add(this, tag);

		try
		{
			mViewDestroyedField.set(this, false);
		}
		catch (final Throwable e)
		{
		}

		final int backStackId = transaction.commitAllowingStateLoss();

		try
		{
			mBackStackIdField.set(this, backStackId);
		}
		catch (final Throwable e)
		{
		}

		return backStackId;
	}
}
