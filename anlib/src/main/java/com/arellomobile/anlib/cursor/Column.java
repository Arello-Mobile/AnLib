package com.arellomobile.anlib.cursor;

import android.database.Cursor;

/**
 * Nov 18, 2013
 * 
 * @author denis.mirochnik
 */
public interface Column<T>
{
	String getName();

	void fill(T data, Cursor cursor, int ind);
}
