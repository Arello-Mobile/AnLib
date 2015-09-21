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
public @interface InjectArgument
{
	String value();

	boolean optional() default false;

	/**
	 * Oct 13, 2013
	 * 
	 * @author denis.mirochnik
	 */
	public static class InjectArgumentHolder
	{
		public final String key;
		public final boolean optional;

		public InjectArgumentHolder(String key, boolean optional)
		{
			this.key = key;
			this.optional = optional;
		}

		public static InjectArgumentHolder of(InjectArgument anno)
		{
			return new InjectArgumentHolder(anno.value(), anno.optional());
		}
	}
}