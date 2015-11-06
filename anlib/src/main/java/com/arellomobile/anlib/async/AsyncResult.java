package com.arellomobile.anlib.async;

/**
 * Dec 9, 2014
 *
 * @author denis.mirochnik
 */
public class AsyncResult<V>
{
	public final V data;
	public final Exception exception;

	public boolean isSuccess()
	{
		return exception == null;
	}

	public static <V> AsyncResult<V> ofData(V data)
	{
		return new AsyncResult<>(data, null);
	}

	public static <V> AsyncResult<V> ofException(Exception exception)
	{
		return new AsyncResult<>(null, exception);
	}

	private AsyncResult(V data, Exception exception)
	{
		this.data = data;
		this.exception = exception;
	}
}
