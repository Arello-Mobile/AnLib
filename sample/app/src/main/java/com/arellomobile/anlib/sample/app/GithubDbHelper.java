package com.arellomobile.anlib.sample.app;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.arellomobile.anlib.db.AnLibDBHelper;
import com.arellomobile.anlib.sample.data.Repository;
import com.arellomobile.anlib.sample.data.User;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by senneco on 31.05.2014
 */
public class GithubDbHelper extends AnLibDBHelper
{
	private static final String DB_NAME = "github";
	private static final int DB_VERSION = 1;

	public GithubDbHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
	{
		try
		{
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Repository.class);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2)
	{
	}
}
