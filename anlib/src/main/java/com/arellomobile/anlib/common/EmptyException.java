package com.arellomobile.anlib.common;

/**
 * Jan 26, 2015
 *
 * @author denis.mirochnik
 */
public class EmptyException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public EmptyException()
	{
		super();
	}

	public EmptyException(String detailMessage)
	{
		super(detailMessage);
	}

}
