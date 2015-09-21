package com.arellomobile.anlib.inject;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class InjectException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InjectException()
	{
		super();
	}

	public InjectException(String detailMessage, Throwable throwable)
	{
		super(detailMessage, throwable);
	}

	public InjectException(String detailMessage)
	{
		super(detailMessage);
	}

	public InjectException(Throwable throwable)
	{
		super(throwable);
	}
}
