package com.arellomobile.anlib.inject.injectors;

import static com.arellomobile.anlib.common.Checks.isEmpty;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectSavedState
{
	String value() default "";

	String additional() default "";

	/**
	 * Oct 13, 2013
	 * 
	 * @author denis.mirochnik
	 */
	public static class InjectSavedStateHolder
	{
		public final String key;

		public InjectSavedStateHolder(String key)
		{
			this.key = key;
		}

		public static InjectSavedStateHolder of(InjectSavedState anno, Field field)
		{
			if (isEmpty(anno.value()))
			{
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(field.getDeclaringClass().getName());
				stringBuilder.append('#');
				stringBuilder.append(field.getName());
				stringBuilder.append('|');
				stringBuilder.append(anno.additional());

				return new InjectSavedStateHolder(stringBuilder.toString());
			}
			else
			{
				return new InjectSavedStateHolder(anno.value() + "|" + anno.additional());
			}
		}
	}
}
