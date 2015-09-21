package com.arellomobile.anlib.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dec 9, 2014
 *
 * @author denis.mirochnik
 */
public class Execs
{
	public static final ExecutorService COMPUTE;
	public static final ExecutorService NET;
	public static final ExecutorService DISK;
	public static final ExecutorService SERIAL;

	static
	{
		SERIAL = Executors.unconfigurableExecutorService(Executors.newSingleThreadExecutor(new ThreadFactory()
		{

			@Override
			public Thread newThread(Runnable r)
			{
				return new Thread(r, "Single thread");
			}
		}));

		DISK = Executors.unconfigurableExecutorService(new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128), new NamedThreadFactory("Disk thread ")));
		NET = Executors.unconfigurableExecutorService(new ThreadPoolExecutor(4, 4, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128), new NamedThreadFactory("Net thread ")));

		int processors = Runtime.getRuntime().availableProcessors();

		COMPUTE =
				Executors.unconfigurableExecutorService(new ThreadPoolExecutor(processors, processors, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128), new NamedThreadFactory(
						"Compute thread ")));
	}

	private static class NamedThreadFactory implements ThreadFactory
	{
		private final AtomicInteger mCount = new AtomicInteger();
		private final String mPrefix;

		public NamedThreadFactory(String prefix)
		{
			mPrefix = prefix;
		}

		@Override
		public Thread newThread(Runnable r)
		{
			return new Thread(r, mPrefix + mCount.getAndIncrement());
		}

	}

	private Execs()
	{
	}
}
