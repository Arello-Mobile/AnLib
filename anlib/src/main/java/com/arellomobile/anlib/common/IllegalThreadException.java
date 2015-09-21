package com.arellomobile.anlib.common;

/**
 * Jan 26, 2015
 *
 * @author denis.mirochnik
 */
public class IllegalThreadException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public IllegalThreadException()
	{
		super();
	}

	public IllegalThreadException(String detailMessage)
	{
		super(detailMessage);
	}

}
