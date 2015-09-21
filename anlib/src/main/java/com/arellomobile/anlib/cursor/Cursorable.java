package com.arellomobile.anlib.cursor;

import java.util.Map;

import android.database.Cursor;

/**
 * Nov 18, 2013
 * 
 * @author denis.mirochnik
 */
public interface Cursorable
{
	void fill(Cursor cursor, Map<String, Integer> inds);
}
