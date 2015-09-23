package com.arellomobile.anlib.sample.async;

/**
 * Date: 23.09.2015
 * Time: 20:03
 *
 * @author Yuri Shmakov
 */
public class ComplexResult<D>
{
	private D mData;
	private Exception mException;

	public ComplexResult()
	{
	}

	public ComplexResult(D data)
	{
		mData = data;
	}

	public ComplexResult(Exception exception)
	{
		mException = exception;
	}

	public D getData()
	{
		return mData;
	}

	public void setData(D data)
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
