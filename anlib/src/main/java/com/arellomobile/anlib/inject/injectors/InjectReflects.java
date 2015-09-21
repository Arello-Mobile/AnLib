package com.arellomobile.anlib.inject.injectors;

import java.lang.reflect.Field;

import com.arellomobile.anlib.inject.InjectException;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
class InjectReflects
{
	public static void setField(Field field, Object target, Object value)
	{
		if (field.getType().isPrimitive())
		{
			return;
		}

		try
		{
			field.set(target, value);
		}
		catch (IllegalAccessException | IllegalArgumentException e)
		{
			throw new InjectException(e);
		}
	}

	public static Object getField(Field field, Object target)
	{
		try
		{
			return field.get(target);
		}
		catch (IllegalAccessException | IllegalArgumentException e)
		{
			throw new InjectException(e);
		}
	}
}
