package com.arellomobile.anlib;

import static com.arellomobile.anlib.common.Checks.as;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.anlib.event.Event;
import com.arellomobile.anlib.event.EventAction;
import com.arellomobile.anlib.event.EventDispatcher;
import com.arellomobile.anlib.event.FragmentDispatcherController;
import com.arellomobile.anlib.event.IEventDispatcher;
import com.arellomobile.anlib.inject.FragmentInjectManager;
import com.arellomobile.anlib.inject.IFragmentInjector;
import com.arellomobile.anlib.inject.injectors.ArgumentInjector;
import com.arellomobile.anlib.inject.injectors.ExtraInjector;
import com.arellomobile.anlib.inject.injectors.SaveStateInjector;
import com.arellomobile.anlib.inject.injectors.ViewInjector;

/**
 * Sep 23, 2013
 * 
 * @author denis.mirochnik
 */
public class AnLibFragment extends Fragment implements IEventDispatcher, IFragmentController
{
	private volatile IEventDispatcher mEventDispatcher;
	private IFragmentController mFragmentHelper;

	private IFragmentInjector mFragmentInjector;

	private FragmentDispatcherController mDispatcherController;

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		getLoaderManager();

		/*@formatter:off*/
		
		mFragmentInjector = new FragmentInjectManager(
				ViewInjector.forFragment(this),
				ExtraInjector.forFragment(this),
				ArgumentInjector.forFragment(this),
				SaveStateInjector.forFragment(this)
				);
		
		/*@formatter:on*/

		mDispatcherController = new FragmentDispatcherController(this)
		{
			@Override
			public Event configureEvent(Event event)
			{
				return AnLibFragment.this.configureEvent(event);
			}
		};

		mEventDispatcher = new EventDispatcher<>(mDispatcherController);

		initFragmentManager();

		mFragmentInjector.fragmentAttach();
	}

	private void initFragmentManager()
	{
		mFragmentHelper = new FragmentController(getChildFragmentManager());
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mFragmentInjector.fragmentCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		mFragmentInjector.fragmentViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		mFragmentInjector.fragmentActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart()
	{
		super.onStart();

		mFragmentInjector.fragmentStart();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		mFragmentInjector.fragmentResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();

		mFragmentInjector.fragmentPause();
	}

	@Override
	public void onStop()
	{
		super.onStop();

		mFragmentInjector.fragmentStop();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();

		mFragmentInjector.fragmentDestroyView();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		mFragmentInjector.fragmentDestroy();
	}

	@Override
	public void onDetach()
	{
		super.onDetach();

		mFragmentInjector.fragmentDetach();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		mFragmentInjector.fragmentSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final int id = onCreateView(savedInstanceState);
		return id == 0 ? null : inflater.inflate(id, container, false);
	}

	protected int onCreateView(Bundle savedInstanceState)
	{
		return 0;
	}

	public void setTargetFragementId(int id)
	{
		mDispatcherController.setTargetFragmentId(id);
	}

	public void setTargetFragementTag(String targetFragementTag)
	{
		mDispatcherController.setTargetFragmentTag(targetFragementTag);
	}

	public String getTargetFragementTag()
	{
		return mDispatcherController.getTargetFragmentTag();
	}

	public Integer getTargetFragementId()
	{
		return mDispatcherController.getTargetFragmentId();
	}

	@Override
	public void postDispatchEvent(Event event)
	{
		mEventDispatcher.postDispatchEvent(event);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEvent(A action)
	{
		mEventDispatcher.postDispatchEvent(action);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEvent(A action, Object data)
	{
		mEventDispatcher.postDispatchEvent(action, data);
	}

	@Override
	public void postDispatchEventDelayed(Event event, long delay)
	{
		mEventDispatcher.postDispatchEventDelayed(event, delay);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, long delay)
	{
		mEventDispatcher.postDispatchEventDelayed(action, delay);
	}

	@Override
	public <A extends Enum<A> & EventAction> void postDispatchEventDelayed(A action, Object data, long delay)
	{
		mEventDispatcher.postDispatchEventDelayed(action, data, delay);
	}

	@Override
	public void dispatchEvent(Event event)
	{
		mEventDispatcher.dispatchEvent(event);
	}

	@Override
	public <A extends Enum<A> & EventAction> void dispatchEvent(A action)
	{
		mEventDispatcher.dispatchEvent(action);
	}

	@Override
	public <A extends Enum<A> & EventAction> void dispatchEvent(A action, Object data)
	{
		mEventDispatcher.dispatchEvent(action, data);
	}

	protected Event configureEvent(Event event)
	{
		return event;
	}

	@Override
	public Fragment findFragment(String tag)
	{
		return mFragmentHelper.findFragment(tag);
	}

	@Override
	public Fragment findFragment(int id)
	{
		return mFragmentHelper.findFragment(id);
	}

	@Override
	public boolean hasFragment(String tag)
	{
		return mFragmentHelper.hasFragment(tag);
	}

	@Override
	public boolean hasFragment(int id)
	{
		return mFragmentHelper.hasFragment(id);
	}

	@Override
	public void addFragment(String tag, Fragment frag)
	{
		mFragmentHelper.addFragment(tag, frag);
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag)
	{
		mFragmentHelper.addFragmentLoss(tag, frag);
	}

	@Override
	public void addFragment(String tag, Fragment frag, int contId)
	{
		mFragmentHelper.addFragment(tag, frag, contId);
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag, int contId)
	{
		mFragmentHelper.addFragmentLoss(tag, frag, contId);
	}

	@Override
	public void removeFragment(String tag)
	{
		mFragmentHelper.removeFragment(tag);
	}

	@Override
	public void removeFragmentLoss(String tag)
	{
		mFragmentHelper.removeFragmentLoss(tag);
	}

	@Override
	public void removeFragment(int id)
	{
		mFragmentHelper.removeFragment(id);
	}

	@Override
	public void removeFragmentLoss(int id)
	{
		mFragmentHelper.removeFragmentLoss(id);
	}

	@Override
	public void removeFragment(Fragment fragment)
	{
		mFragmentHelper.removeFragment(fragment);
	}

	@Override
	public void removeFragmentLoss(Fragment fragment)
	{
		mFragmentHelper.removeFragmentLoss(fragment);
	}

/*	@Override
	public final <Result> AnLibLoader<Result> initLoader(String id, LoaderFactory<Result> factory, LoaderListener<Result> callback)
	{
		return mLoaderController.initLoader(id, factory, callback);
	}

	@Override
	public <Result> AnLibLoader<Result> getLoader(String id)
	{
		return mLoaderController.getLoader(id);
	}

	@Override
	public void destroyLoader(String id)
	{
		mLoaderController.destroyLoader(id);
	}

	@Override
	public final <Result> AnLibLoader<Result> restartLoader(String id, AnLibLoader<Result> loader, LoaderListener<Result> callback)
	{
		return mLoaderController.restartLoader(id, loader, callback);
	}
*/
	public AnLibFragment getParentAnLibFragment()
	{
		return as(getParentFragment(), AnLibFragment.class);
	}

	public AnLibActivity getAnLibActivity()
	{
		return as(getActivity(), AnLibActivity.class);
	}
}
