package com.arellomobile.anlib.sample.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.anlib.AnLibActivity;
import com.arellomobile.anlib.inject.injectors.InjectSavedState;
import com.arellomobile.anlib.inject.injectors.InjectView;
import com.arellomobile.anlib.sample.R;
import com.arellomobile.anlib.sample.async.ComplexResult;
import com.arellomobile.anlib.sample.async.SignInLoader;
import com.arellomobile.anlib.sample.common.AuthUtils;
import com.arellomobile.anlib.sample.common.UserUtils;
import com.arellomobile.anlib.sample.data.User;

public class SignInActivity extends AnLibActivity implements View.OnClickListener
{
	private static final int LOADER_SIGN_IN = 0;

	@InjectView(R.id.activity_sign_in_edit_text_email)
	private EditText mLoginEdit;
	@InjectView(R.id.activity_sign_in_edit_text_password)
	private EditText mPasswordEdit;
	@InjectView(R.id.activity_sign_in_button_sign_in)
	private Button mSignInButton;

	@InjectSavedState
	private String mToken;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		String token = AuthUtils.getToken(this);
		String login = UserUtils.getLogin(this);

		if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(login))
		{
			startActivity(new Intent(this, HomeActivity.class));
			finish();
			return;
		}

		setContentView(R.layout.activity_sign_in);

		mSignInButton.setOnClickListener(this);

		Loader<Object> signInLoader = getSupportLoaderManager().getLoader(LOADER_SIGN_IN);
		if (signInLoader != null && signInLoader.isStarted())
		{
			SignInCallbacks callbacks = new SignInCallbacks();
			callbacks.onLoadStarted();
			getSupportLoaderManager().initLoader(LOADER_SIGN_IN, null, callbacks);
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v == mSignInButton)
		{
			signIn();
		}
	}

	private void signIn()
	{
		String login = mLoginEdit.getText().toString();
		String password = mPasswordEdit.getText().toString();

		String credentials = String.format("%s:%s", login, password);

		mToken = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);

		SignInCallbacks callbacks = new SignInCallbacks();
		callbacks.onLoadStarted();
		getSupportLoaderManager().restartLoader(LOADER_SIGN_IN, null, callbacks);
	}

	private class SignInCallbacks implements LoaderManager.LoaderCallbacks<ComplexResult<User>>
	{

		@Override
		public Loader<ComplexResult<User>> onCreateLoader(int id, Bundle args)
		{
			return new SignInLoader(SignInActivity.this, mToken);
		}

		public void onLoadStarted()
		{
			mSignInButton.setEnabled(false);
		}

		@Override
		public void onLoadFinished(Loader<ComplexResult<User>> loader, ComplexResult<User> data)
		{
			mSignInButton.setEnabled(true);

			if (data.getException() == null)
			{
				Context context = SignInActivity.this;

				AuthUtils.setToken(context, mToken);
				UserUtils.setUser(context, data.getData());

				startActivity(new Intent(context, HomeActivity.class));
				finish();
				return;
			}

			Toast.makeText(SignInActivity.this, data.getException().getMessage(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onLoaderReset(Loader<ComplexResult<User>> loader)
		{
			mSignInButton.setEnabled(false);
		}
	}
}
