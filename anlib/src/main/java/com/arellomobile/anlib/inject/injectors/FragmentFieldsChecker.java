package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import com.arellomobile.anlib.inject.injectors.InjectFragment.InjectFragmentHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class FragmentFieldsChecker extends AbsFragmentFieldsChecker<InjectFragmentHolder, InjectFragment>
{
	public static final FragmentFieldsChecker INSTANCE = new FragmentFieldsChecker();

	@Override
	protected InjectFragment getAnnotation(Field field)
	{
		return field.getAnnotation(InjectFragment.class);
	}

	@Override
	protected InjectFragmentHolder getHolder(InjectFragment annotation)
	{
		return InjectFragmentHolder.of(annotation);
	}

	private FragmentFieldsChecker()
	{
	}
}
