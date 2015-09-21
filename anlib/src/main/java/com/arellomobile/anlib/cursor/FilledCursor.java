package com.arellomobile.anlib.cursor;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Nov 18, 2013
 * 
 * @author denis.mirochnik
 */
public class FilledCursor<T extends Cursorable> extends CursorWrapper
{
	private Map<String, Integer> mInds;
	private final Class<T> mTargetClass;
	private final T mTarget;

	public static <U extends Cursorable> FilledCursor<U> of(Cursor cursor, Class<U> dataClass)
	{
		return new FilledCursor<>(cursor, dataClass);
	}

	public static <U extends Cursorable> FilledCursor<U> of(Cursor cursor, U data)
	{
		return new FilledCursor<>(cursor, data);
	}

	public FilledCursor(Cursor cursor, Class<T> dataClass)
	{
		super(requireNotNull(cursor));

		mTargetClass = requireNotNull(dataClass);

		collectColumns(cursor);

		mTarget = newData();
	}

	public FilledCursor(Cursor cursor, T data)
	{
		super(requireNotNull(cursor));

		mTarget = requireNotNull(data);
		mTargetClass = null;

		collectColumns(cursor);
	}

	private void collectColumns(Cursor cursor)
	{
		final String[] names = getColumnNames();
		mInds = new HashMap<>(names.length);

		for (final String name : names)
		{
			mInds.put(name, cursor.getColumnIndex(name));
		}

		mInds = Collections.unmodifiableMap(mInds);
	}

	public T getData()
	{
		mTarget.fill(this, mInds);

		return mTarget;
	}

	public T newData()
	{
		requireNotNull(mTargetClass);

		try
		{
			final T newInstance = mTargetClass.newInstance();

			newInstance.fill(this, mInds);

			return newInstance;
		}
		catch (final InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (final IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
