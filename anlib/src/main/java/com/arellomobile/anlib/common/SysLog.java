package com.arellomobile.anlib.common;

import static com.arellomobile.anlib.common.Checks.requireNotNull;
import android.util.Log;

/**
 * 22.11.2012
 * 
 * @author denis.mirochnik
 */
public final class SysLog
{
	public enum Level
	{
		SILENT(Integer.MAX_VALUE),
		ERROR(Log.ERROR),
		WARN(Log.WARN),
		INFO(Log.INFO),
		DEBUG(Log.DEBUG),
		VERBOSE(Log.VERBOSE);

		private final int mLvl;

		private Level(int lvl)
		{
			mLvl = lvl;
		}

		public boolean access(Level level)
		{
			return level.mLvl >= mLvl;
		}

		public String logCatName()
		{
			return name();
		}

		public static Level valueOf(int lvl)
		{
			for (final Level level : values())
			{
				if (level.mLvl == lvl)
				{
					return level;
				}
			}

			return SILENT;
		}
	}

	private volatile static Level sLevel = Level.INFO;

	public static void setLevel(Level level)
	{
		sLevel = requireNotNull(level);
	}

	public static void e(String tag, String message)
	{
		print(Log.ERROR, tag, message);
	}

	public static void e(String tag, String message, Throwable t)
	{
		e(tag, createMessage(message, t));
	}

	public static void w(String tag, String message)
	{
		print(Log.WARN, tag, message);
	}

	public static void w(String tag, String message, Throwable t)
	{
		w(tag, createMessage(message, t));
	}

	public static void i(String tag, String message)
	{
		print(Log.INFO, tag, message);
	}

	public static void i(String tag, String message, Throwable t)
	{
		i(tag, createMessage(message, t));
	}

	public static void d(String tag, String message)
	{
		print(Log.DEBUG, tag, message);
	}

	public static void d(String tag, String message, Throwable t)
	{
		d(tag, createMessage(message, t));
	}

	public static void v(String tag, String message)
	{
		print(Log.VERBOSE, tag, message);
	}

	public static void v(String tag, String message, Throwable t)
	{
		v(tag, createMessage(message, t));
	}

	private static String createMessage(String message, Throwable t)
	{
		return message + '\n' + Log.getStackTraceString(t);
	}

	private static void print(int lvl, String tag, String message)
	{
		if (sLevel.access(Level.valueOf(lvl)))
		{
			Log.println(lvl, tag, message);
		}
	}

	private SysLog()
	{
	}
}
