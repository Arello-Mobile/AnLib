package com.arellomobile.anlib.inject;

import android.os.Bundle;

/**
 * Oct 13, 2013
 * 
 * @author denis.mirochnik
 */
public class DialogInjectManager extends InjectManager<IDialogInjector> implements IDialogInjector
{

	public DialogInjectManager(IDialogInjector... injectors)
	{
		super(injectors);
	}

	@Override
	public void dialogCreate(Bundle savedInstanceState)
	{
		for (final IDialogInjector injector : getInjectors())
		{
			injector.dialogCreate(savedInstanceState);
		}
	}

	@Override
	public void dialogContentChanged()
	{
		for (final IDialogInjector injector : getInjectors())
		{
			injector.dialogContentChanged();
		}
	}

	@Override
	public void dialogStart()
	{
		for (final IDialogInjector injector : getInjectors())
		{
			injector.dialogStart();
		}
	}

	@Override
	public void dialogStop()
	{
		for (final IDialogInjector injector : getInjectors())
		{
			injector.dialogStop();
		}
	}

	@Override
	public void dialogSaveInstanceState(Bundle outState)
	{
		for (final IDialogInjector injector : getInjectors())
		{
			injector.dialogSaveInstanceState(outState);
		}
	}

	@Override
	public void dialogRestoreInstanceState(Bundle savedInstanceState)
	{
		for (final IDialogInjector injector : getInjectors())
		{
			injector.dialogRestoreInstanceState(savedInstanceState);
		}
	}
}
