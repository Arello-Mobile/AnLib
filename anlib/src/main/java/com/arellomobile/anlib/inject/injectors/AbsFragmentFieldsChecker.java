package com.arellomobile.anlib.inject.injectors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import android.support.v4.app.Fragment;

import com.arellomobile.anlib.inject.InjectException;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public abstract class AbsFragmentFieldsChecker<T, A extends Annotation> extends AbsFieldsChecker<T>
{

	@Override
	public T checkField(Field field)
	{
		final A annotation = getAnnotation(field);

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

			if (!Fragment.class.isAssignableFrom(field.getType()))
			{
				throw new InjectException("Field must be subclass of Fragment");
			}

			return getHolder(annotation);
		}

		return null;
	}

	protected abstract T getHolder(A annotation);

	protected abstract A getAnnotation(Field field);
}
