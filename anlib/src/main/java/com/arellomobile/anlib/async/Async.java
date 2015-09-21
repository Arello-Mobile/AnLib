package com.arellomobile.anlib.async;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Dec 9, 2014
 *
 * @author denis.mirochnik
 */
public abstract class Async<V> implements Future<V>
{
	private static final int MESSAGE_RESULT = 0;

	private static class Result<T>
	{
		public final Async<T> async;
		public final T data;

		public Result(Async<T> async, T data)
		{
			this.async = async;
			this.data = data;
		}
	}

	private static final Handler HANDLER = new Handler(Looper.getMainLooper())
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case MESSAGE_RESULT:
					deliverResult((Result<?>) msg.obj);
					break;

				default:
					super.handleMessage(msg);
					break;
			}
		}

		private <T> void deliverResult(Result<T> result)
		{
			if (result.async.isCancelled())
			{
				result.async.onCancelled();
			}
			else
			{
				result.async.onResult(result.data);
			}
		}
	};

	private final FutureTask<V> mFuture;
	private final Callable<V> mWorker;

	public Async()
	{
		mWorker = new Callable<V>()
		{

			@Override
			public V call() throws Exception
			{
				return doWork();
			}
		};

		mFuture = new FutureTask<V>(mWorker)
		{
			@Override
			protected void done()
			{
				try
				{
					HANDLER.obtainMessage(MESSAGE_RESULT, new Result<V>(Async.this, get())).sendToTarget();
				}
				catch (CancellationException e)
				{
					HANDLER.obtainMessage(MESSAGE_RESULT, new Result<V>(Async.this, null)).sendToTarget();
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
				catch (final ExecutionException e)
				{
					throw new RuntimeException(e.getCause());
				}
			}
		};
	}

	protected abstract V doWork();

	protected void onCancelled()
	{

	}

	protected void onResult(V v)
	{

	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return mFuture.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled()
	{
		return mFuture.isCancelled();
	}

	@Override
	public boolean isDone()
	{
		return mFuture.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException
	{
		return mFuture.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		return mFuture.get(timeout, unit);
	}

	public void execute(Executor service)
	{
		service.execute(mFuture);
	}
}
