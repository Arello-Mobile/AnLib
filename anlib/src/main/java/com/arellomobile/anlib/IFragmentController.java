package com.arellomobile.anlib;

import android.support.v4.app.Fragment;

/**
 * Jun 20, 2014
 *
 * @author denis.mirochnik
 */
public interface IFragmentController
{

	Fragment findFragment(String tag);

	Fragment findFragment(int id);

	boolean hasFragment(String tag);

	boolean hasFragment(int id);

	void addFragment(String tag, Fragment frag);

	void addFragmentLoss(String tag, Fragment frag);

	void addFragment(String tag, Fragment frag, int contId);

	void addFragmentLoss(String tag, Fragment frag, int contId);

	void removeFragment(String tag);

	void removeFragmentLoss(String tag);

	void removeFragment(int id);

	void removeFragmentLoss(int id);

	void removeFragment(Fragment fragment);

	void removeFragmentLoss(Fragment fragment);

}