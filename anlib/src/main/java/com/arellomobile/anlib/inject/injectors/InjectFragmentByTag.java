package com.arellomobile.anlib.inject.injectors;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Oct 13, 2013
 * 
 * @author denis.mirochnik
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface InjectFragmentByTag
{
	String value();

	boolean optional() default false;

	/**
	 * Oct 13, 2013
	 * 
	 * @author denis.mirochnik
	 */
	public static class InjectFragmentByTagHolder
	{
		public final String tag;
		public final boolean optional;

		public InjectFragmentByTagHolder(String tag, boolean optional)
		{
			this.tag = tag;
			this.optional = optional;
		}

		public static InjectFragmentByTagHolder of(InjectFragmentByTag anno)
		{
			return new InjectFragmentByTagHolder(anno.value(), anno.optional());
		}
	}
}
