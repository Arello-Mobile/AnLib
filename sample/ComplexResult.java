package com.arellomobile.anlib.sample;

/**
 * Date: 22.09.2015
 * Time: 17:03
 *
 * @author Yuri Shmakov
 */
public class ComplexResult<T>
{
	private T mData;
	private Exception mException;

	public ComplexResult()
	{
	}

	public ComplexResult(T result)
	{
		mData = result;
	}

	public ComplexResult(Exception exception)
	{
		mException = exception;
	}

	public T getData()
	{
		return mData;
	}

	public void setData(T data)
	{
		mData = data;
	}

	public Exception getException()
	{
		return mException;
	}

	public void setException(Exception exception)
	{
		mException = exception;
	}
}
