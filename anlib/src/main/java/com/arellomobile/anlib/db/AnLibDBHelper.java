package com.arellomobile.anlib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public abstract class AnLibDBHelper extends OrmLiteSqliteOpenHelper
{
	private final String mDatabaseName;
	private final Context mContext;

	public AnLibDBHelper(Context context, String databaseName, CursorFactory factory, int version)
	{
		super(context, databaseName, factory, version);

		mContext = context;
		mDatabaseName = databaseName;
	}

	public String getDBName()
	{
		return mDatabaseName;
	}

	public Context getContext()
	{
		return mContext;
	}
}
