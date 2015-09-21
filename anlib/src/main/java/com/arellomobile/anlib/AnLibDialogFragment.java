package com.arellomobile.anlib;

import static com.arellomobile.anlib.common.Checks.as;
import static com.arellomobile.anlib.common.Checks.requireApi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.arellomobile.anlib.common.Sdks;
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
//TODO common dialogs. with this class or FragmentDialog? DialogEvents?
public class AnLibDialogFragment extends DialogFragmentLoss implements IEventDispatcher, IFragmentController
{
	private volatile IEventDispatcher mEventDispatcher;
	private IFragmentController mFragmentHelper;

	private IFragmentInjector mFragmentInjector;

	private FragmentDispatcherController mDispatcherController;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

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
				return AnLibDialogFragment.this.configureEvent(event);
			}
		};

		mEventDispatcher = new EventDispatcher<>(mDispatcherController);

		initFragmentManager();

		mFragmentInjector.fragmentAttach();
	}

	@SuppressLint("NewApi")
	private void initFragmentManager()
	{
		if (Sdks.GE_SDK_17)
		{
			mFragmentHelper = new FragmentController(getChildFragmentManager());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mFragmentInjector.fragmentCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		mFragmentInjector.fragmentActivityCreated(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		mFragmentInjector.fragmentViewCreated(view, savedInstanceState);
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

	public void setTargetFragementTag(String targetFragementTag)
	{
		mDispatcherController.setTargetFragmentTag(targetFragementTag);
	}

	public void setTargetFragementId(int id)
	{
		mDispatcherController.setTargetFragmentId(id);
	}

	public Integer getTargetFragmentId()
	{
		return mDispatcherController.getTargetFragmentId();
	}

	public String getTargetFragementTag()
	{
		return mDispatcherController.getTargetFragmentTag();
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
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		return mFragmentHelper.findFragment(tag);
	}

	@Override
	public Fragment findFragment(int id)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		return mFragmentHelper.findFragment(id);
	}

	@Override
	public boolean hasFragment(String tag)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		return mFragmentHelper.hasFragment(tag);
	}

	@Override
	public boolean hasFragment(int id)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		return mFragmentHelper.hasFragment(id);
	}

	@Override
	public void addFragment(String tag, Fragment frag)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		mFragmentHelper.addFragment(tag, frag);
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		mFragmentHelper.addFragmentLoss(tag, frag);
	}

	@Override
	public void addFragment(String tag, Fragment frag, int contId)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		mFragmentHelper.addFragment(tag, frag, contId);
	}

	@Override
	public void addFragmentLoss(String tag, Fragment frag, int contId)
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

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

	@SuppressLint("NewApi")
	public AnLibFragment getParentAnLibFragment()
	{
		requireApi(Build.VERSION_CODES.JELLY_BEAN_MR1);

		return as(getParentFragment(), AnLibFragment.class);
	}

	public AnLibActivity getAnLibActivity()
	{
		return as(getActivity(), AnLibActivity.class);
	}

}
