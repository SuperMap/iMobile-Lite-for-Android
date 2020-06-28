package com.supermap.imobilelite.maps;

import android.view.MotionEvent;

abstract interface MapTouchEventHandler {
	public abstract boolean handleTouchEvent(MotionEvent paramMotionEvent);
}