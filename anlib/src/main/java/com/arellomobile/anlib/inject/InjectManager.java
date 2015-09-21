package com.arellomobile.anlib.inject;

import static com.arellomobile.anlib.common.Checks.requireNotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Oct 13, 2013
 * 
 * @author denis.mirochnik
 */
public class InjectManager<T>
{
	private final List<T> mInjectors;

	public InjectManager(@SuppressWarnings("unchecked") T... injectors)
	{
		requireNotEmpty(injectors);

		final ArrayList<T> list = new ArrayList<>();
		Collections.addAll(list, injectors);
		mInjectors = Collections.unmodifiableList(list);
	}

	public List<T> getInjectors()
	{
		return mInjectors;
	}
}
