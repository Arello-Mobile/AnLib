package com.arellomobile.anlib.db;

import java.sql.SQLException;

import android.database.Cursor;

import com.j256.ormlite.android.AndroidCompiledStatement;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.DatabaseConnection;

public class Cursors
{
	public static Cursor getCursor(OrmLiteSqliteOpenHelper helper, PreparedQuery<?> preparedQuery) throws SQLException
	{
		return getCursor(helper, preparedQuery, StatementType.SELECT);
	}

	public static Cursor getCursor(OrmLiteSqliteOpenHelper helper, PreparedQuery<?> preparedQuery, StatementType type) throws SQLException
	{
		return ((AndroidCompiledStatement) preparedQuery.compile(helper.getConnectionSource().getReadOnlyConnection(), type)).getCursor();
	}

	public static Cursor getCursor(DatabaseConnection connection, PreparedQuery<?> preparedQuery) throws SQLException
	{
		return getCursor(connection, preparedQuery, StatementType.SELECT);
	}

	public static Cursor getCursor(DatabaseConnection connection, PreparedQuery<?> preparedQuery, StatementType type) throws SQLException
	{
		return ((AndroidCompiledStatement) preparedQuery.compile(connection, type)).getCursor();
	}

	private Cursors()
	{
	}
}