package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectExtra.InjectExtraHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class ExtraFieldsChecker extends AbsFieldsChecker<InjectExtraHolder>
{
	public static final ExtraFieldsChecker INSTANCE = new ExtraFieldsChecker();

	@Override
	public InjectExtraHolder checkField(Field field)
	{
		final InjectExtra annotation = field.getAnnotation(InjectExtra.class);

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

			return InjectExtraHolder.of(annotation);
		}

		return null;
	}

	private ExtraFieldsChecker()
	{
	}
}
