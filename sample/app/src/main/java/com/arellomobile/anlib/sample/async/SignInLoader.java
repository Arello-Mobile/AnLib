package com.arellomobile.anlib.sample.async;

import android.content.Context;

import com.arellomobile.anlib.async.Execs;
import com.arellomobile.anlib.sample.data.User;

/**
 * Date: 23.09.2015
 * Time: 20:16
 *
 * @author Yuri Shmakov
 */
public class SignInLoader extends GithubLoader<User>
{
	private String mToken;

	public SignInLoader(Context context, String token)
	{
		super(context, Execs.NET);

		mToken = token;
	}

	@Override
	protected User loadData()
	{
		return getApi().signIn(mToken);
	}
}
