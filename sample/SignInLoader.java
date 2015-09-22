package com.arellomobile.anlib.sample;

import android.app.Application;
import android.content.Context;

import com.arellomobile.anlib.async.Execs;

/**
 * Date: 22.09.2015
 * Time: 16:56
 *
 * @author Yuri Shmakov
 */
public class SignInLoader extends RestApiLoader<Boolean>
{
	private String mLogin;
	private String mPassword;

	public SignInLoader(Context context, String login, String password)
	{
		super(context, Execs.NET);

		mLogin = login;
		mPassword = password;
	}

	@Override
	protected Boolean doRequest() throws Exception
	{
		return getRestApi().signIn(mLogin, mPassword);
	}
}
