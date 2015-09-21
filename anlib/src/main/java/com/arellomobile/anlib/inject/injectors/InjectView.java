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
public @interface InjectView
{
	int value();

	boolean optional() default false;

	/**
	 * Oct 13, 2013
	 * 
	 * @author denis.mirochnik
	 */
	public static class InjectViewHolder
	{
		public final int id;
		public final boolean optional;

		public InjectViewHolder(int id, boolean optional)
		{
			this.id = id;
			this.optional = optional;
		}

		public static InjectViewHolder of(InjectView anno)
		{
			return new InjectViewHolder(anno.value(), anno.optional());
		}
	}
}
