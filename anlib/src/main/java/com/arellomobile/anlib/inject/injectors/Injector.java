package com.arellomobile.anlib.inject.injectors;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import com.arellomobile.anlib.inject.FieldCollector;
import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.FieldCollector.FieldsProcessor;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public abstract class Injector<T, P> implements FieldsProcessor<T>, IInjector<T>
{
	private final FieldCollector<T> mCollector;
	private final P mProvider;
	private final Object mTarget;

	private boolean mInjecting;

	public Injector(P provider, Object target)
	{
		mProvider = requireNotNull(provider);
		mTarget = requireNotNull(target);

		mCollector = new FieldCollector<>(getChecker(), this);
		mCollector.collectFields(target);
	}

	@Override
	public void inject()
	{
		mInjecting = true;
		mCollector.processFields();
	}

	@Override
	public void deinject()
	{
		mInjecting = false;
		mCollector.processFields();
	}

	protected FieldCollector<T> getCollector()
	{
		return mCollector;
	}

	protected Object getTarget()
	{
		return mTarget;
	}

	protected P getProvider()
	{
		return mProvider;
	}

	protected boolean isInjecting()
	{
		return mInjecting;
	}

	protected abstract FieldsChecker<T> getChecker();
}
