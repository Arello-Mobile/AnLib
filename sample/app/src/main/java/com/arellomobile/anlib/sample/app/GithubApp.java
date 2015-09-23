package com.arellomobile.anlib.sample.app;

import java.lang.reflect.Field;

import com.arellomobile.anlib.AnLibApplication;
import com.arellomobile.anlib.common.SysLog;
import com.arellomobile.anlib.db.DbIniter;
import com.arellomobile.anlib.sample.common.AuthUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


import static com.arellomobile.anlib.common.Checks.isEmpty;

/**
 * Created by senneco on 29.05.2014
 */
public class GithubApp extends AnLibApplication
{
	private DbIniter<GithubDbHelper> mDbHelper;
	private GithubApi mApiClient;

	@Override
	public void onApplicationCreate()
	{
		SysLog.setLevel(SysLog.Level.DEBUG);
		mDbHelper = new DbIniter<>(GithubDbHelper.class);

		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setFieldNamingStrategy(new CustomFieldNamingPolicy())
				.setPrettyPrinting()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.serializeNulls()
				.create();

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("https://api.github.com")
				.setConverter(new GsonConverter(gson))
				.setRequestInterceptor(new RequestInterceptor()
				{
					@Override
					public void intercept(RequestFacade requestFacade)
					{
						String token = AuthUtils.getToken(GithubApp.this);

						if (!isEmpty(token))
						{
							requestFacade.addHeader("Authorization", token);
						}
					}
				})
				.build();

		mApiClient = restAdapter.create(GithubApi.class);
	}

	@Override
	public void onAsyncCreate()
	{
		// pass
	}

	public GithubDbHelper getDbHelper()
	{
		return mDbHelper.get();
	}

	public GithubApi getApiClient()
	{
		return mApiClient;
	}

	static class CustomFieldNamingPolicy implements FieldNamingStrategy
	{
		@Override
		public String translateName(Field field)
		{
			String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
			name = name.substring(2, name.length()).toLowerCase();
			return name;
		}
	}
}
