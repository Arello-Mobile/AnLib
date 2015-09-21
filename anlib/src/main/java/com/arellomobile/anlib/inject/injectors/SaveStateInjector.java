package com.arellomobile.anlib.inject.injectors;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.arellomobile.anlib.common.Sdks;
import com.arellomobile.anlib.inject.FieldCollector.FieldsChecker;
import com.arellomobile.anlib.inject.IActivityInjector;
import com.arellomobile.anlib.inject.IActivityInjector.BaseActivityInjector;
import com.arellomobile.anlib.inject.IDialogInjector;
import com.arellomobile.anlib.inject.IDialogInjector.BaseDialogInjector;
import com.arellomobile.anlib.inject.IFragmentInjector;
import com.arellomobile.anlib.inject.IFragmentInjector.BaseFragmentInjector;
import com.arellomobile.anlib.inject.IObjectInjector;
import com.arellomobile.anlib.inject.InjectException;
import com.arellomobile.anlib.inject.injectors.InjectSavedState.InjectSavedStateHolder;
import com.arellomobile.anlib.inject.injectors.SaveStateInjector.SavedStateProvider;

/**
 * Jan 27, 2015
 *
 * @author denis.mirochnik
 */
public class SaveStateInjector extends Injector<InjectSavedStateHolder, SavedStateProvider>
{
	private Bundle mSavedState;
	private Bundle mOutState;
	private boolean mSave;

	public SaveStateInjector(SavedStateProvider provider, Object target)
	{
		super(provider, target);
	}

	@Override
	protected FieldsChecker<InjectSavedStateHolder> getChecker()
	{
		return SaveStateFieldsChecker.INSTANCE;
	}

	public void saveState(Bundle outState)
	{
		mOutState = outState;
		mSave = true;
		getCollector().processFields();
		mSave = false;
	}

	@Override
	public boolean preProcessFields()
	{
		if (mSave)
		{
			return mOutState != null;
		}

		return (mSavedState = getProvider().getSavedState()) != null;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	@Override
	public void processField(Field field, InjectSavedStateHolder data)
	{
		if (mSave)
		{
			Object value = InjectReflects.getField(field, getTarget());

			Bundle bundle = mOutState;
			boolean success = true;
			String key = data.key;

			if (value instanceof Boolean)
			{
				bundle.putBoolean(key, (Boolean) value);
				success = true;
			}
			else if (value instanceof boolean[])
			{
				bundle.putBooleanArray(key, (boolean[]) value);
				success = true;
			}
			else if (value instanceof Byte)
			{
				bundle.putByte(key, (Byte) value);
				success = true;
			}
			else if (value instanceof byte[])
			{
				bundle.putByteArray(key, (byte[]) value);
				success = true;
			}
			else if (value instanceof Character)
			{
				bundle.putChar(key, (Character) value);
				success = true;
			}
			else if (value instanceof char[])
			{
				bundle.putCharArray(key, (char[]) value);
				success = true;
			}
			else if (value instanceof Double)
			{
				bundle.putDouble(key, (Double) value);
				success = true;
			}
			else if (value instanceof double[])
			{
				bundle.putDoubleArray(key, (double[]) value);
				success = true;
			}
			else if (value instanceof Float)
			{
				bundle.putFloat(key, (Float) value);
				success = true;
			}
			else if (value instanceof float[])
			{
				bundle.putFloatArray(key, (float[]) value);
				success = true;
			}
			else if (value instanceof Integer)
			{
				bundle.putInt(key, (Integer) value);
				success = true;
			}
			else if (value instanceof int[])
			{
				bundle.putIntArray(key, (int[]) value);
				success = true;
			}
			else if (value instanceof Long)
			{
				bundle.putLong(key, (Long) value);
				success = true;
			}
			else if (value instanceof long[])
			{
				bundle.putLongArray(key, (long[]) value);
				success = true;
			}
			else if (value instanceof Short)
			{
				bundle.putShort(key, (Short) value);
				success = true;
			}
			else if (value instanceof short[])
			{
				bundle.putShortArray(key, (short[]) value);
				success = true;
			}
			else if (value instanceof String)
			{
				bundle.putString(key, (String) value);
				success = true;
			}
			else if (value instanceof String[])
			{
				bundle.putStringArray(key, (String[]) value);
				success = true;
			}
			else if (value instanceof CharSequence)
			{
				bundle.putCharSequence(key, (CharSequence) value);
				success = true;
			}
			else if (value instanceof CharSequence[])
			{
				bundle.putCharSequenceArray(key, (CharSequence[]) value);
				success = true;
			}
			else if (value instanceof Bundle)
			{
				bundle.putBundle(key, (Bundle) value);
				success = true;
			}
			else if (value instanceof Parcelable)
			{
				bundle.putParcelable(key, (Parcelable) value);
				success = true;
			}
			else if (value instanceof Parcelable[])
			{
				bundle.putParcelableArray(key, (Parcelable[]) value);
				success = true;
			}
			else if (Sdks.GE_LOLLIPOP)
			{
				if (value instanceof Size)
				{
					bundle.putSize(key, (Size) value);
					success = true;
				}
				else if (value instanceof SizeF)
				{
					bundle.putSizeF(key, (SizeF) value);
					success = true;
				}
			}
			else if (value instanceof List)
			{
				final Type type = field.getGenericType();

				if (type instanceof ParameterizedType)
				{
					final ParameterizedType paramType = (ParameterizedType) type;

					final Type[] typeArguments = paramType.getActualTypeArguments();

					if (typeArguments.length == 1)
					{
						final Type oneType = typeArguments[0];

						if (oneType instanceof Class)
						{
							final Class<?> oneClass = (Class<?>) oneType;

							if (Parcelable.class.isAssignableFrom(oneClass))
							{
								bundle.putParcelableArrayList(key, (ArrayList<Parcelable>) value);
								success = true;
							}
						}
					}
				}
			}
			else if (value instanceof ArrayList)
			{
				final Type type = field.getGenericType();

				if (type instanceof ParameterizedType)
				{
					final ParameterizedType paramType = (ParameterizedType) type;

					final Type[] typeArguments = paramType.getActualTypeArguments();

					if (typeArguments.length == 1)
					{
						final Type oneType = typeArguments[0];

						if (oneType instanceof Class)
						{
							final Class<?> oneClass = (Class<?>) oneType;

							if (String.class.isAssignableFrom(oneClass))
							{
								bundle.putStringArrayList(key, (ArrayList<String>) value);
								success = true;
							}
							else if (CharSequence.class.isAssignableFrom(oneClass))
							{
								bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
								success = true;
							}
							else if (Integer.class.isAssignableFrom(oneClass))
							{
								bundle.putIntegerArrayList(key, (ArrayList<Integer>) value);
								success = true;
							}
							else if (Parcelable.class.isAssignableFrom(oneClass))
							{
								bundle.putParcelableArrayList(key, (ArrayList<Parcelable>) value);
								success = true;
							}
						}
					}
				}
			}
			else if (value instanceof SparseArray)
			{
				final Type type = field.getGenericType();

				if (type instanceof ParameterizedType)
				{
					final ParameterizedType paramType = (ParameterizedType) type;

					final Type[] typeArguments = paramType.getActualTypeArguments();

					if (typeArguments.length == 1)
					{
						final Type oneType = typeArguments[0];

						if (oneType instanceof Class)
						{
							final Class<?> oneClass = (Class<?>) oneType;

							if (Parcelable.class.isAssignableFrom(oneClass))
							{
								bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) value);
								success = true;
							}
						}
					}
				}
			}

			if (!success && value instanceof Serializable)
			{
				bundle.putSerializable(key, (Serializable) value);
				success = true;
			}

			if (!success)
			{
				throw new InjectException("Could not save state");
			}

		}
		else if (isInjecting())
		{
			if (!mSavedState.containsKey(data.key))
			{
				return;
			}

			Object value = mSavedState.get(data.key);

			InjectReflects.setField(field, getTarget(), value);
		}
		else
		{
			InjectReflects.setField(field, getTarget(), null);
		}
	}

	@Override
	public void postProcessFields()
	{
		mSavedState = null;
		mOutState = null;
	}

	public interface SavedStateProvider
	{
		Bundle getSavedState();
	}

	public static class BunldeSavedStateProvider implements SavedStateProvider
	{
		private final Bundle mSavedState;

		public BunldeSavedStateProvider(Bundle savedState)
		{
			mSavedState = savedState;
		}

		@Override
		public Bundle getSavedState()
		{
			return mSavedState;
		}
	}

	public static IDialogInjector forDialog(final Dialog dialog)
	{
		return new BaseDialogInjector()
		{
			private SaveStateInjector mInjector;

			@Override
			public void dialogCreate(Bundle savedInstanceState)
			{
				if (mInjector == null)
				{
					mInjector = SaveStateInjector.of(savedInstanceState, dialog);
				}

				mInjector.inject();
			}

			@Override
			public void dialogSaveInstanceState(Bundle outState)
			{
				mInjector.saveState(outState);
			}
		};
	}

	public static IFragmentInjector forFragment(final Fragment fragment)
	{
		return new BaseFragmentInjector()
		{
			private SaveStateInjector mInjector;

			@Override
			public void fragmentCreate(Bundle savedInstanceState)
			{
				if (mInjector == null)
				{
					mInjector = SaveStateInjector.of(savedInstanceState, fragment);
				}

				mInjector.inject();
			}

			@Override
			public void fragmentSaveInstanceState(Bundle outState)
			{
				mInjector.saveState(outState);
			}

			@Override
			public void fragmentDestroy()
			{
				mInjector.deinject();
				mInjector = null;
			}
		};
	}

	public static IActivityInjector forActivity(final Activity activity)
	{
		return new BaseActivityInjector()
		{
			private SaveStateInjector mInjector;

			@Override
			public void activityCreate(Bundle savedInstanceSate)
			{
				if (mInjector == null)
				{
					mInjector = SaveStateInjector.of(savedInstanceSate, activity);
				}

				mInjector.inject();
			}

			@Override
			public void activitySaveInstanceState(Bundle outState)
			{
				mInjector.saveState(outState);
			}

			@Override
			public void activityDestroy()
			{
				mInjector.deinject();
				mInjector = null;
			}
		};
	}

	public static ISaveStateOjectInjector forObject(final SaveStateInjector injector)
	{
		return new ISaveStateOjectInjector()
		{

			@Override
			public void inject()
			{
				injector.inject();
			}

			@Override
			public void deinject()
			{
				injector.deinject();
			}

			@Override
			public void saveState(Bundle outState)
			{
				injector.saveState(outState);
			}
		};
	}

	public static SaveStateInjector of(Bundle savedState, Object target)
	{
		return new SaveStateInjector(new BunldeSavedStateProvider(savedState), target);
	}

	public static SaveStateInjector of(SavedStateProvider provider, Object target)
	{
		return new SaveStateInjector(provider, target);
	}

	public interface ISaveStateOjectInjector extends IObjectInjector
	{
		void saveState(Bundle outState);
	}
}
