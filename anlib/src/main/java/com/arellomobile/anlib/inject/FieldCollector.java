package com.arellomobile.anlib.inject;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Oct 13, 2013
 * 
 * @author denis.mirochnik
 */
public class FieldCollector<T>
{
	private final FieldsChecker<T> mChecker;
	private final FieldsProcessor<T> mProcessor;
	private final LinkedHashMap<Field, T> mFields = new LinkedHashMap<>();

	public FieldCollector(FieldsChecker<T> checker, FieldsProcessor<T> processor)
	{
		mChecker = requireNotNull(checker);
		mProcessor = requireNotNull(processor);
	}

	public void collectFields(Object source)
	{
		Class<? extends Object> clazz = requireNotNull(source).getClass();

		while (clazz != null && mChecker.checkClass(clazz))
		{
			final Field[] fields = clazz.getDeclaredFields();

			for (final Field field : fields)
			{
				field.setAccessible(true);

				try
				{
					final T fieldObj = mChecker.checkField(field);

					if (fieldObj != null)
					{
						mFields.put(field, fieldObj);
					}
				}
				catch (InjectException e)
				{
					throw rethrow(e, field);
				}
			}

			clazz = clazz.getSuperclass();
		}
	}

	public void processFields()
	{
		try
		{
			if (!mProcessor.preProcessFields())
			{
				return;
			}

			final Set<Field> keySet = mFields.keySet();

			for (final Field field : keySet)
			{
				try
				{

					mProcessor.processField(field, mFields.get(field));
				}
				catch (InjectException e)
				{
					throw rethrow(e, field);
				}
			}

			mProcessor.postProcessFields();
		}
		catch (InjectException e)
		{
			throw rethrow(e, null);
		}
	}

	private static RuntimeException rethrow(Exception e, Field field)
	{
		return new InjectException("Error during processing field " + createMessage(field), e);
	}

	private static String createMessage(Field field)
	{
		if (field == null)
		{
			return "unknown field";
		}

		return field.getDeclaringClass().getSimpleName() + "#" + field.getName();
	}

	public interface FieldsChecker<T>
	{
		boolean checkClass(Class<?> clazz);

		T checkField(Field field);
	}

	public interface FieldsProcessor<T>
	{
		boolean preProcessFields();

		void processField(Field field, T data);

		void postProcessFields();
	}
}
