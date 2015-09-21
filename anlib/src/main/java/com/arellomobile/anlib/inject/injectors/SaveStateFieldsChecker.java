package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectSavedState.InjectSavedStateHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class SaveStateFieldsChecker extends AbsFieldsChecker<InjectSavedStateHolder>
{
	public static final SaveStateFieldsChecker INSTANCE = new SaveStateFieldsChecker();

	@Override
	public InjectSavedStateHolder checkField(Field field)
	{
		final InjectSavedState annotation = field.getAnnotation(InjectSavedState.class);

		if (annotation != null)
		{
			final int modifiers = field.getModifiers();

			if (Modifier.isStatic(modifiers))
			{
				throw new InjectException("Field cannot be static");
			}

			if (Modifier.isFinal(modifiers))
			{
				throw new InjectException("Field cannot be final");
			}

			return InjectSavedStateHolder.of(annotation, field);
		}

		return null;
	}

	private SaveStateFieldsChecker()
	{
	}
}
