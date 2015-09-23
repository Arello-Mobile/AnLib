package com.arellomobile.anlib.sample.async;

import java.util.concurrent.Executor;

import android.content.Context;
import android.net.Uri;

import com.arellomobile.anlib.async.SimpleDataLoader;
import com.arellomobile.anlib.sample.app.GithubApi;
import com.arellomobile.anlib.sample.app.GithubApp;
import com.arellomobile.anlib.sample.app.GithubDbHelper;

/**
 * Date: 23.09.2015
 * Time: 20:00
 *
 * @author Yuri Shmakov
 */
public abstract class GithubLoader<D> extends SimpleDataLoader<ComplexResult<D>>
{
	public GithubLoader(Context context, Executor executor, Uri uri)
	{
		super(context, executor, uri);
	}

	public GithubLoader(Context context, Executor executor)
	{
		super(context, executor);
	}

	@Override
	protected void releaseData(ComplexResult<D> data)
	{
		data.setData(null);
		data.setData(null);
	}

	@Override
	protected ComplexResult<D> loadInBackground()
	{
		final ComplexResult<D> result = new ComplexResult<>();
		try
		{
			result.setData(loadData());
		}
		catch (Exception e)
		{
			result.setException(e);
		}

		return result;
	}

	protected abstract D loadData();

	protected GithubApi getApi()
	{
		return ((GithubApp) getContext().getApplicationContext()).getApiClient();
	}

	protected GithubDbHelper getDbHelper()
	{
		return ((GithubApp) getContext().getApplicationContext()).getDbHelper();
	}
}
