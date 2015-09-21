package com.arellomobile.anlib;

import static com.arellomobile.anlib.common.Checks.isMainThread;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Jan 26, 2015
 *
 * @author denis.mirochnik
 */
public class AnLibHandler
{
	public static final Handler HANDLER = new Handler(Looper.getMainLooper());

	public static void runOnUiThread(Runnable runnable)
	{
		if (isMainThread())
		{
			runnable.run();
		}
		else
		{
			post(runnable);
		}
	}

	public static boolean post(Runnable r)
	{
		return HANDLER.post(r);
	}

	public static boolean postAtTime(Runnable r, long uptimeMillis)
	{
		return HANDLER.postAtTime(r, uptimeMillis);
	}

	public static boolean postAtTime(Runnable r, Object token, long uptimeMillis)
	{
		return HANDLER.postAtTime(r, token, uptimeMillis);
	}

	public static boolean postDelayed(Runnable r, long delayMillis)
	{
		return HANDLER.postDelayed(r, delayMillis);
	}

	public static boolean postAtFrontOfQueue(Runnable r)
	{
		return HANDLER.postAtFrontOfQueue(r);
	}

	public static boolean sendMessage(Message msg)
	{
		return HANDLER.sendMessage(msg);
	}

	public static boolean sendEmptyMessage(int what)
	{
		return HANDLER.sendEmptyMessage(what);
	}

	public static boolean sendEmptyMessageDelayed(int what, long delayMillis)
	{
		return HANDLER.sendEmptyMessageDelayed(what, delayMillis);
	}

	public static boolean sendEmptyMessageAtTime(int what, long uptimeMillis)
	{
		return HANDLER.sendEmptyMessageAtTime(what, uptimeMillis);
	}

	public static boolean sendMessageDelayed(Message msg, long delayMillis)
	{
		return HANDLER.sendMessageDelayed(msg, delayMillis);
	}

	public static boolean sendMessageAtTime(Message msg, long uptimeMillis)
	{
		return HANDLER.sendMessageAtTime(msg, uptimeMillis);
	}

	public static boolean sendMessageAtFrontOfQueue(Message msg)
	{
		return HANDLER.sendMessageAtFrontOfQueue(msg);
	}

	private AnLibHandler()
	{
	}
}
