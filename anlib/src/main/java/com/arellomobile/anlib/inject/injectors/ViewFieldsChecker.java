package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import android.view.View;

import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectView.InjectViewHolder;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class ViewFieldsChecker extends AbsFieldsChecker<InjectViewHolder>
{
	public static final ViewFieldsChecker INSTANCE = new ViewFieldsChecker();

	@Override
	public InjectViewHolder checkField(Field field)
	{
		final InjectView annotation = field.getAnnotation(InjectView.class);

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

			if (!View.class.isAssignableFrom(field.getType()))
			{
				throw new InjectException("Field must be subclass of View");
			}

			return InjectViewHolder.of(annotation);
		}

		return null;
	}

	private ViewFieldsChecker()
	{
	}
}
