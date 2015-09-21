package com.arellomobile.anlib.db;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

import android.content.Context;

/**
 * Jan 26, 2015
 *
 * @author denis.mirochnik
 */
public class DbIniter<T extends AnLibDBHelper>
{
	private final CountDownLatch mCdl = new CountDownLatch(1);
	private T mDbHelper;

	private Class<T> mClazz;

	public DbIniter(Class<T> clazz)
	{
		mClazz = requireNotNull(clazz);
	}

	public synchronized void init(Context context)
	{
		if (mDbHelper == null)
		{
			try
			{
				Constructor<T> constructor = mClazz.getDeclaredConstructor(Context.class);

				mDbHelper = constructor.newInstance(context.getApplicationContext());
			}
			catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				throw new RuntimeException("Could not create db helper: " + mClazz);
			}

			mDbHelper.getWritableDatabase();

			mCdl.countDown();
		}
	}

	public T get()
	{
		try
		{
			mCdl.await();
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}

		return mDbHelper;
	}
}
