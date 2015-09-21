package com.arellomobile.anlib;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.arellomobile.anlib.event.DialogEvents;

/**
 * Feb 12, 2015
 *
 * @author denis.mirochnik
 */
public class SimpleDialogFragment extends AnLibDialogFragment implements OnClickListener
{
	private static final String ARG_TITLE = "argTitle";
	private static final String ARG_MESSAGE = "argMessage";
	private static final String ARG_POSITIVE = "argPos";
	private static final String ARG_NEGATIVE = "argNeg";
	private static final String ARG_NEUTRAl = "argNeu";

	private String mTitle;
	private String mMessage;
	private String mPositive;
	private String mNegative;
	private String mNeutral;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle arguments = getArguments();

		mTitle = getStringExtra(ARG_TITLE, arguments);
		mMessage = getStringExtra(ARG_MESSAGE, arguments);
		mPositive = getStringExtra(ARG_POSITIVE, arguments);
		mNegative = getStringExtra(ARG_NEGATIVE, arguments);
		mNeutral = getStringExtra(ARG_NEUTRAl, arguments);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Builder builder = new AlertDialog.Builder(getActivity()).setTitle(mTitle).setMessage(mMessage);

		if (mPositive != null)
		{
			builder.setPositiveButton(mPositive, this);
		}

		if (mNegative != null)
		{
			builder.setNegativeButton(mNegative, this);
		}

		if (mNeutral != null)
		{
			builder.setNeutralButton(mNeutral, this);
		}

		onDialogBuild(builder, savedInstanceState);

		return builder.create();
	}

	protected void onDialogBuild(Builder builder, Bundle savedInstanceState)
	{

	}

	private String getStringExtra(String key, Bundle arg)
	{
		if (arg == null)
		{
			return null;
		}

		Object object = arg.get(key);

		if (object instanceof String)
		{
			return (String) object;
		}
		else if (object instanceof Integer)
		{
			return getString((int) object);
		}

		return null;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		switch (which)
		{
			case DialogInterface.BUTTON_POSITIVE:
				dispatchEvent(DialogEvents.POSITIVE);
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				dispatchEvent(DialogEvents.NEGATIVE);
				break;

			case DialogInterface.BUTTON_NEUTRAL:
				dispatchEvent(DialogEvents.NEUTRAL);
				break;
		}
	}
}
