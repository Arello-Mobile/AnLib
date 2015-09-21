package com.arellomobile.anlib.inject.injectors;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Oct 2, 2013
 * 
 * @author denis.mirochnik
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectExtra
{
	String value();

	boolean optional() default false;

	/**
	 * Oct 13, 2013
	 * 
	 * @author denis.mirochnik
	 */
	public static class InjectExtraHolder
	{
		public final String key;
		public final boolean optional;

		public InjectExtraHolder(String key, boolean optional)
		{
			this.key = key;
			this.optional = optional;
		}

		public static InjectExtraHolder of(InjectExtra anno)
		{
			return new InjectExtraHolder(anno.value(), anno.optional());
		}
	}
}
