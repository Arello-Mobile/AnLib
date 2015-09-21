package com.arellomobile.anlib.cursor;

import java.lang.reflect.Field;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.text.TextUtils;
import android.util.SparseArray;

import com.j256.ormlite.field.DatabaseField;

/**
 * 30.03.2013
 * 
 * @author denis.mirochnik
 */
public class InjectedCursor<T> extends CursorWrapper
{
	private SparseArray<Field> mFields;
	private final T mTarget;
	private final Cursor mCursor;
	private final Class<T> mClazz;
	private boolean mValid;

	public InjectedCursor(Cursor cursor, Class<T> clazz)
	{
		super(cursor);

		mValid = false;

		mCursor = cursor;
		mClazz = clazz;

		try
		{
			mTarget = clazz.newInstance();
		}
		catch (final InstantiationException e)
		{
			throw new IllegalStateException("Could not instantiate target object", e);
		}
		catch (final IllegalAccessException e)
		{
			throw new IllegalStateException("Could not instantiate target object", e);
		}

		collectFields();
	}

	private void collectFields()
	{
		Class<?> klass = mClazz;

		mFields = new SparseArray<Field>();

		do
		{
			final Field[] fields = klass.getDeclaredFields();

			for (final Field field : fields)
			{
				String columnName = null;

				final DatabaseField databaseAnno = field.getAnnotation(DatabaseField.class);

				if (databaseAnno == null)
				{
					continue;
				}

				columnName = databaseAnno.columnName();

				if (TextUtils.isEmpty(columnName))
				{
					columnName = field.getName();
				}

				final int index = mCursor.getColumnIndex(columnName);

				if (index == -1)
				{
					continue;
				}

				field.setAccessible(true);

				mFields.put(index, field);
			}

			klass = klass.getSuperclass();
		}
		while (klass != null);
	}

	private void updateTarget()
	{
		updateTarget(mTarget);
	}

	private void updateTarget(Object target)
	{
		final int size = mFields.size();

		for (int i = 0; i < size; i++)
		{
			final Field field = mFields.valueAt(i);
			final int index = mFields.keyAt(i);
			final Class<?> type = field.getType();
			Object value = null;

			if (type.equals(double.class) || type.equals(Double.class))
			{
				value = mCursor.getDouble(index);
			}
			else if (type.equals(float.class) || type.equals(Float.class))
			{
				value = mCursor.getFloat(index);
			}
			else if (type.equals(int.class) || type.equals(Integer.class))
			{
				value = mCursor.getInt(index);
			}
			else if (type.equals(long.class) || type.equals(Long.class))
			{
				value = mCursor.getLong(index);
			}
			else if (type.equals(short.class) || type.equals(Short.class))
			{
				value = mCursor.getShort(index);
			}
			else if (type.equals(String.class))
			{
				value = mCursor.getString(index);
			}

			try
			{
				field.set(target, value);
			}
			catch (final IllegalArgumentException e)
			{
				throw new IllegalStateException("Could assign value to field " + field.getName(), e);
			}
			catch (final IllegalAccessException e)
			{
				throw new IllegalStateException("Could assign value to field " + field.getName(), e);
			}
		}
	}

	public void fillData(T target)
	{
		if (!mValid)
		{
			throw new IllegalStateException("You must move cursor to fill target");
		}

		updateTarget(target);
	}

	public T getData()
	{
		if (!mValid)
		{
			throw new IllegalStateException("You must move cursor to fill target");
		}

		return mTarget;
	}

	public T getDataCopy()
	{
		if (!mValid)
		{
			throw new IllegalStateException("You must move cursor to fill target");
		}

		T instance = null;

		try
		{
			instance = mClazz.newInstance();
		}
		catch (final InstantiationException e)
		{
			throw new IllegalStateException("Could not instantiate target object", e);
		}
		catch (final IllegalAccessException e)
		{
			throw new IllegalStateException("Could not instantiate target object", e);
		}

		updateTarget(instance);

		return instance;
	}

	@Override
	public boolean move(int offset)
	{
		final boolean move = super.move(offset);
		mValid = false;

		if (move)
		{
			mValid = true;

			updateTarget();
		}

		return move;
	}

	@Override
	public boolean moveToFirst()
	{
		final boolean move = super.moveToFirst();
		mValid = false;

		if (move)
		{
			mValid = true;

			updateTarget();
		}

		return move;
	}

	@Override
	public boolean moveToLast()
	{
		final boolean move = super.moveToLast();
		mValid = false;

		if (move)
		{
			mValid = true;

			updateTarget();
		}

		return move;
	}

	@Override
	public boolean moveToNext()
	{
		final boolean move = super.moveToNext();
		mValid = false;

		if (move)
		{
			mValid = true;

			updateTarget();
		}

		return move;
	}

	@Override
	public boolean moveToPosition(int position)
	{
		final boolean move = super.moveToPosition(position);
		mValid = false;

		if (move)
		{
			mValid = true;

			updateTarget();
		}

		return move;
	}

	@Override
	public boolean moveToPrevious()
	{
		final boolean move = super.moveToPrevious();
		mValid = false;

		if (move)
		{
			mValid = true;

			updateTarget();
		}

		return move;
	}
}
