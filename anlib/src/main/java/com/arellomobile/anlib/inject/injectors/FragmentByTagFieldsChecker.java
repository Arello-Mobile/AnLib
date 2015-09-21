package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import com.arellomobile.anlib.inject.injectors.InjectFragmentByTag.InjectFragmentByTagHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class FragmentByTagFieldsChecker extends AbsFragmentFieldsChecker<InjectFragmentByTagHolder, InjectFragmentByTag>
{
	public static final FragmentByTagFieldsChecker INSTANCE = new FragmentByTagFieldsChecker();

	@Override
	protected InjectFragmentByTag getAnnotation(Field field)
	{
		return field.getAnnotation(InjectFragmentByTag.class);
	}

	@Override
	protected InjectFragmentByTagHolder getHolder(InjectFragmentByTag annotation)
	{
		return InjectFragmentByTagHolder.of(annotation);
	}

	private FragmentByTagFieldsChecker()
	{
	}
}