package com.arellomobile.anlib.cursor;

import static com.arellomobile.anlib.common.Checks.requireNotEmpty;

import java.util.Map;

import android.database.Cursor;

/**
 * Nov 18, 2013
 * 
 * @author denis.mirochnik
 */
public class Filler<T>
{
	private final Column<T>[] mColumns;

	public static <U> Filler<U> of(Column<U>[] columns)
	{
		return new Filler<>(columns);
	}

	public Filler(Column<T>[] columns)
	{
		mColumns = requireNotEmpty(columns);
	}

	public void fill(T data, Cursor cursor, Map<String, Integer> inds)
	{
		for (final Column<T> column : mColumns)
		{
			final String name = column.getName();
			final Integer integer = inds.get(name);
			int ind;

			if (integer == null || (ind = integer.intValue()) < 0)
			{
				continue;
			}

			column.fill(data, cursor, ind);
		}
	}
}
