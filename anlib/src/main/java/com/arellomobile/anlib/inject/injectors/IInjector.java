package com.arellomobile.anlib.inject.injectors;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public interface IInjector<T>
{
	void inject();

	void deinject();
}