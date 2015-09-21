package com.arellomobile.anlib.inject.injectors;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public abstract class AbsFieldsChecker<T> implements FieldsChecker<T>
{
	@Override
	public boolean checkClass(Class<?> clazz)
	{
		return clazz != FragmentActivity.class && clazz != Activity.class && clazz != Fragment.class && clazz != Object.class && clazz != Dialog.class;
	}
}
