package com.arellomobile.anlib.sample;

import java.util.concurrent.Executor;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.arellomobile.anlib.async.SimpleDataLoader;

/**
 * Date: 22.09.2015
 * Time: 17:03
 *
 * @author Yuri Shmakov
 */
public abstract class RestApiLoader<T> extends SimpleDataLoader<ComplexResult<T>>
{
	public RestApiLoader(Context context, Executor executor, Uri uri)
	{
		super(context, executor, uri);
	}

	public RestApiLoader(Context context, Executor executor)
	{
		super(context, executor);
	}

	@Override
	protected void releaseData(ComplexResult<T> data)
	{
		data.setData(null);
		data.setException(null);
	}

	@Override
	protected ComplexResult<T> loadInBackground()
	{
		T result;
		try
		{
			result = doRequest();
		}
		catch (Exception e)
		{
			return new ComplexResult<>(e);
		}

		return new ComplexResult<>(result);
	}

	protected RestApi getRestApi()
	{
		return ((Application) getContext().getApplicationContext()).getRestApi();
	}

	protected abstract T doRequest() throws Exception;
}
