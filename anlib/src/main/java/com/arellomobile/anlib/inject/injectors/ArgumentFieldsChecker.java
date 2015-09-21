package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectArgument.InjectArgumentHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class ArgumentFieldsChecker extends AbsFieldsChecker<InjectArgumentHolder>
{
	public static final ArgumentFieldsChecker INSTANCE = new ArgumentFieldsChecker();

	@Override
	public InjectArgumentHolder checkField(Field field)
	{
		InjectArgument anno = field.getAnnotation(InjectArgument.class);

		if (anno != null)
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

			return InjectArgumentHolder.of(anno);
		}

		return null;
	}

	private ArgumentFieldsChecker()
	{
	}
}
