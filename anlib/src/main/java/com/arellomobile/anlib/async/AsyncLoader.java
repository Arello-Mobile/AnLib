/*
 * Copyright (C) 2010 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arellomobile.anlib.async;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.OperationCanceledException;
import android.support.v4.content.Loader;

import com.arellomobile.anlib.common.Sdks;

/**
 * Abstract Loader that provides an {@link AsyncTask} to do the work. See
 * {@link Loader} and {@link android.app.LoaderManager} for more details.
 *
 * <p>
 * Here is an example implementation of an AsyncTaskLoader subclass that loads
 * the currently installed applications from the package manager. This
 * implementation takes care of retrieving the application labels and sorting
 * its result set from them, monitoring for changes to the installed
 * applications, and rebuilding the list when a change in configuration requires
 * this (such as a locale change).
 *
 * {@sample
 * development/samples/ApiDemos/src/com/example/android/apis/app/LoaderCustom.
 * java loader}
 *
 * <p>
 * An example implementation of a fragment that uses the above loader to show
 * the currently installed applications in a list is below.
 *
 * {@sample
 * development/samples/ApiDemos/src/com/example/android/apis/app/LoaderCustom.
 * java fragment}
 *
 * @param <D>
 *            the data type to be loaded.
 */
public abstract class AsyncLoader<D> extends Loader<D>
{
	private final class LoadTask extends Async<D>
	{
		private final CountDownLatch mDone = new CountDownLatch(1);

		/* Runs on a worker thread */
		@Override
		protected D doWork()
		{
			try
			{
				return AsyncLoader.this.loadInBackground();
			}
			catch (OperationCanceledException ex)
			{
				if (!isCancelled())
				{
					// onLoadInBackground threw a canceled exception spuriously.
					// This is problematic because it means that the LoaderManager did not
					// cancel the Loader itself and still expects to receive a result.
					// Additionally, the Loader's own state will not have been updated to
					// reflect the fact that the task was being canceled.
					// So we treat this case as an unhandled exception.
					throw ex;
				}

				return null;
			}
		}

		/* Runs on the UI thread */
		@Override
		protected void onResult(D data)
		{
			try
			{
				AsyncLoader.this.dispatchOnLoadComplete(this, data);
			}
			finally
			{
				mDone.countDown();
			}
		}

		/* Runs on the UI thread */
		@Override
		protected void onCancelled()
		{
			try
			{
				AsyncLoader.this.dispatchOnCancelled(this, null);
			}
			finally
			{
				mDone.countDown();
			}
		}
	}

	private final Executor mExecutor;

	volatile LoadTask mTask;
	volatile LoadTask mCancellingTask;

	public AsyncLoader(Context context, Executor executor)
	{
		super(context);

		mExecutor = requireNotNull(executor);
	}

	public boolean cancelLoad()
	{
		return onCancelLoad();
	}

	@Override
	protected void onForceLoad()
	{
		super.onForceLoad();
		cancelLoad();
		mTask = new LoadTask();
		executePendingTask();
	}

	protected boolean onCancelLoad()
	{
		if (mTask != null)
		{
			if (mCancellingTask != null)
			{
				mTask = null;
				return false;
			}
			else
			{
				boolean cancelled = mTask.cancel(false);
				if (cancelled)
				{
					mCancellingTask = mTask;
				}
				mTask = null;
				return cancelled;
			}
		}
		return false;
	}

	/**
	 * Called if the task was canceled before it was completed. Gives the class
	 * a chance to clean up post-cancellation and to properly dispose of the
	 * result.
	 *
	 * @param data
	 *            The value that was returned by {@link #loadInBackground}, or
	 *            null if the task threw {@link OperationCanceledException}.
	 */
	public void onCanceled(D data)
	{
	}

	void executePendingTask()
	{
		if (mCancellingTask == null && mTask != null)
		{
			mTask.execute(mExecutor);
		}
	}

	@SuppressLint("NewApi")
	void dispatchOnCancelled(LoadTask task, D data)
	{
		onCanceled(data);
		if (mCancellingTask == task)
		{
			if (Sdks.GE_SDK_18)
			{
				rollbackContentChanged();
			}

			mCancellingTask = null;

			executePendingTask();
		}
	}

	@SuppressLint("NewApi")
	void dispatchOnLoadComplete(LoadTask task, D data)
	{
		if (mTask != task)
		{
			dispatchOnCancelled(task, data);
		}
		else
		{
			if (isAbandoned())
			{
				// This cursor has been abandoned; just cancel the new data.
				onCanceled(data);
			}
			else
			{
				if (Sdks.GE_SDK_18)
				{
					commitContentChanged();
				}

				mTask = null;
				deliverResult(data);
			}
		}
	}

	/**
	 * Called on a worker thread to perform the actual load and to return the
	 * result of the load operation.
	 *
	 * Implementations should not deliver the result directly, but should return
	 * them from this method, which will eventually end up calling
	 * {@link #deliverResult} on the UI thread. If implementations need to
	 * process the results on the UI thread they may override
	 * {@link #deliverResult} and do so there.
	 *
	 * To support cancellation, this method should periodically check the value
	 * of {@link #isLoadInBackgroundCanceled} and terminate when it returns
	 * true. Subclasses may also override {@link #cancelLoadInBackground} to
	 * interrupt the load directly instead of polling
	 * {@link #isLoadInBackgroundCanceled}.
	 *
	 * When the load is canceled, this method may either return normally or
	 * throw {@link OperationCanceledException}. In either case, the
	 * {@link Loader} will call {@link #onCanceled} to perform post-cancellation
	 * cleanup and to dispose of the result object, if any.
	 *
	 * @return The result of the load operation.
	 *
	 * @throws OperationCanceledException
	 *             if the load is canceled during execution.
	 *
	 * @see #isLoadInBackgroundCanceled
	 * @see #cancelLoadInBackground
	 * @see #onCanceled
	 */
	protected abstract D loadInBackground();

	/**
	 * Returns true if the current invocation of {@link #loadInBackground} is
	 * being canceled.
	 *
	 * @return True if the current invocation of {@link #loadInBackground} is
	 *         being canceled.
	 *
	 * @see #loadInBackground
	 */
	public boolean isLoadInBackgroundCanceled()
	{
		return mCancellingTask != null;
	}
}
