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

			if (value == null)
			{
				return;
			}

			Bundle bundle = mOutState;
			String key = data.key;

			if (Sdks.GE_LOLLIPOP)
			{
				if (value instanceof Size)
				{
					bundle.putSize(key, (Size) value);
					return;
				}
				if (value instanceof SizeF)
				{
					bundle.putSizeF(key, (SizeF) value);
					return;
				}
			}

			if (value instanceof Boolean)
			{
				bundle.putBoolean(key, (Boolean) value);
				return;
			}
			if (value instanceof boolean[])
			{
				bundle.putBooleanArray(key, (boolean[]) value);
				return;
			}
			if (value instanceof Byte)
			{
				bundle.putByte(key, (Byte) value);
				return;
			}
			if (value instanceof byte[])
			{
				bundle.putByteArray(key, (byte[]) value);
				return;
			}
			if (value instanceof Character)
			{
				bundle.putChar(key, (Character) value);
				return;
			}
			if (value instanceof char[])
			{
				bundle.putCharArray(key, (char[]) value);
				return;
			}
			if (value instanceof Double)
			{
				bundle.putDouble(key, (Double) value);
				return;
			}
			if (value instanceof double[])
			{
				bundle.putDoubleArray(key, (double[]) value);
				return;
			}
			if (value instanceof Float)
			{
				bundle.putFloat(key, (Float) value);
				return;
			}
			if (value instanceof float[])
			{
				bundle.putFloatArray(key, (float[]) value);
				return;
			}
			if (value instanceof Integer)
			{
				bundle.putInt(key, (Integer) value);
				return;
			}
			if (value instanceof int[])
			{
				bundle.putIntArray(key, (int[]) value);
				return;
			}
			if (value instanceof Long)
			{
				bundle.putLong(key, (Long) value);
				return;
			}
			if (value instanceof long[])
			{
				bundle.putLongArray(key, (long[]) value);
				return;
			}
			if (value instanceof Short)
			{
				bundle.putShort(key, (Short) value);
				return;
			}
			if (value instanceof short[])
			{
				bundle.putShortArray(key, (short[]) value);
				return;
			}
			if (value instanceof String)
			{
				bundle.putString(key, (String) value);
				return;
			}
			if (value instanceof String[])
			{
				bundle.putStringArray(key, (String[]) value);
				return;
			}
			if (value instanceof CharSequence)
			{
				bundle.putCharSequence(key, (CharSequence) value);
				return;
			}
			if (value instanceof CharSequence[])
			{
				bundle.putCharSequenceArray(key, (CharSequence[]) value);
				return;
			}
			if (value instanceof Bundle)
			{
				bundle.putBundle(key, (Bundle) value);
				return;
			}
			if (value instanceof Parcelable)
			{
				bundle.putParcelable(key, (Parcelable) value);
				return;
			}
			if (value instanceof Parcelable[])
			{
				bundle.putParcelableArray(key, (Parcelable[]) value);
				return;
			}
			if (value instanceof List)
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
								return;
							}
						}
					}
				}
			}
			if (value instanceof ArrayList)
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
								return;
							}
							if (CharSequence.class.isAssignableFrom(oneClass))
							{
								bundle.putCharSequenceArrayList(key, (ArrayList<CharSequence>) value);
								return;
							}
							if (Integer.class.isAssignableFrom(oneClass))
							{
								bundle.putIntegerArrayList(key, (ArrayList<Integer>) value);
								return;
							}
							if (Parcelable.class.isAssignableFrom(oneClass))
							{
								bundle.putParcelableArrayList(key, (ArrayList<Parcelable>) value);
								return;
							}
						}
					}
				}
			}

			if (value instanceof SparseArray)
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
								return;
							}
						}
					}
				}
			}

			if (value instanceof Serializable)
			{
				bundle.putSerializable(key, (Serializable) value);
				return;
			}

			throw new InjectException("Could not save state");

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
