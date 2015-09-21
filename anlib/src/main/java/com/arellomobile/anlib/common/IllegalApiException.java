package com.arellomobile.anlib.common;

/**
 * Jan 26, 2015
 *
 * @author denis.mirochnik
 */
public class IllegalApiException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public IllegalApiException()
	{
		super();
	}

	public IllegalApiException(String detailMessage)
	{
		super(detailMessage);
	}

}
