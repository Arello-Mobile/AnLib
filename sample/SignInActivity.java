package com.arellomobile.anlib.sample;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.anlib.inject.injectors.InjectView;

import com.arellomobile.anlib.sample.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener
{
	private static final int LOADER_SIGN_IN = 1;

	@InjectView(R.id.activity_sign_in_edit_text_login)
	private EditText mLoginEditText;
	@InjectView(R.id.activity_sign_in_edit_text_password)
	private EditText mPasswordEditText;
	@InjectView(R.id.activity_sign_in_button_sign_in)
	private Button mSignInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		mSignInButton.setOnClickListener(this);

		Loader<?> signInLoader = getSupportLoaderManager().getLoader(LOADER_SIGN_IN);
		if (signInLoader != null && signInLoader.isStarted())
		{
			SignInLoaderCallbacks callback = new SignInLoaderCallbacks();
			callback.onLoadStart();

			getSupportLoaderManager().initLoader(LOADER_SIGN_IN, null, callback);
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v == mSignInButton)
		{
			SignInLoaderCallbacks callback = new SignInLoaderCallbacks();
			callback.onLoadStart();

			getSupportLoaderManager().restartLoader(LOADER_SIGN_IN, null, callback);
		}
	}

	private class SignInLoaderCallbacks implements LoaderManager.LoaderCallbacks<ComplexResult<Boolean>>
	{
		@Override
		public Loader<ComplexResult<Boolean>> onCreateLoader(int id, Bundle args)
		{
			String login = mLoginEditText.getText().toString();
			String password = mPasswordEditText.getText().toString();

			return new SignInLoader(SignInActivity.this, login, password);
		}

		public void onLoadStart()
		{
			mSignInButton.setEnabled(false);
		}

		@Override
		public void onLoadFinished(Loader<ComplexResult<Boolean>> loader, ComplexResult<Boolean> result)
		{
			mSignInButton.setEnabled(true);

			boolean isSuccess = result.getException() == null && result.getData();
			Toast.makeText(SignInActivity.this, "Sign in " + (isSuccess ? "success" : "fail"), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onLoaderReset(Loader<ComplexResult<Boolean>> loader)
		{
			// pass
		}
	}
}
