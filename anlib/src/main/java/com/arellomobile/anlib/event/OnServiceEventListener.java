package com.arellomobile.anlib.event;

import android.app.Service;

public interface OnServiceEventListener
{
	void onServiceEvent(Service service, Event event);
}