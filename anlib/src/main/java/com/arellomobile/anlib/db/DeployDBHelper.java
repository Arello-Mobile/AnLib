package com.arellomobile.anlib.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.arellomobile.anlib.common.Streams;

/**
 * Sep 21, 2013
 * 
 * @author denis.mirochnik
 */
public abstract class DeployDBHelper extends AnLibDBHelper
{
	public DeployDBHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion)
	{
		super(context, databaseName, factory, databaseVersion);
	}

	protected boolean needDeploy(boolean exists)
	{
		return !exists;
	}

	protected abstract String getAssetPath();

	protected abstract void onDeployFailed(File path, Exception e);

	@Override
	public SQLiteDatabase getReadableDatabase()
	{
		deploy();

		return super.getReadableDatabase();
	}

	@Override
	public SQLiteDatabase getWritableDatabase()
	{
		deploy();

		return super.getWritableDatabase();
	}

	protected synchronized void deploy()
	{
		final Context context = getContext();
		final File path = context.getDatabasePath(getDBName());

		boolean exists = path.exists();
		exists = needDeploy(exists);

		if (exists)
		{
			path.delete();
			path.getParentFile().mkdirs();

			InputStream asset = null;
			OutputStream db = null;

			try
			{
				asset = context.getAssets().open(getAssetPath());
				db = new FileOutputStream(path);

				Streams.transfer(asset, db);
			}
			catch (final IOException e)
			{
				onDeployFailed(path, e);
			}
			finally
			{
				if (asset != null)
				{
					try
					{
						asset.close();
					}
					catch (final IOException e)
					{
						//ignore
					}
				}

				if (db != null)
				{
					try
					{
						db.close();
					}
					catch (final IOException e)
					{
						//ignore
					}
				}
			}
		}
	}
}
