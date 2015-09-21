package com.arellomobile.anlib.inject;

import com.arellomobile.anlib.inject.injectors.IInjector;

public interface IObjectInjector
{
	void inject();

	void deinject();

	public static class InjectorWrapper implements IObjectInjector
	{
		private final IInjector<?> mInjector;

		public InjectorWrapper(IInjector<?> injector)
		{
			mInjector = injector;
		}

		@Override
		public void inject()
		{
			mInjector.inject();
		}

		@Override
		public void deinject()
		{
			mInjector.deinject();
		}
	}
}