package com.arellomobile.anlib.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

/**
 * Dec 11, 2014
 *
 * @author denis.mirochnik
 */
public class DrawableWrapper extends Drawable implements Drawable.Callback
{
	private final Drawable mDelegate;

	public DrawableWrapper(Drawable delegate)
	{
		mDelegate = delegate;
		mDelegate.setCallback(this);
	}

	@Override
	public void invalidateDrawable(Drawable who)
	{
		invalidateSelf();
	}

	@Override
	public void scheduleDrawable(Drawable who, Runnable what, long when)
	{
		scheduleSelf(what, when);
	}

	@Override
	public void unscheduleDrawable(Drawable who, Runnable what)
	{
		unscheduleSelf(what);
	}

	@Override
	public void draw(Canvas canvas)
	{
		mDelegate.draw(canvas);
	}

	@Override
	public void setBounds(int left, int top, int right, int bottom)
	{
		super.setBounds(left, top, right, bottom);
		mDelegate.setBounds(left, top, right, bottom);
	}

	@Override
	public void setBounds(Rect bounds)
	{
		super.setBounds(bounds);
		mDelegate.setBounds(bounds);
	}

	@Override
	public Rect getDirtyBounds()
	{
		return mDelegate.getDirtyBounds();
	}

	@Override
	public void setChangingConfigurations(int configs)
	{
		mDelegate.setChangingConfigurations(configs);
	}

	@Override
	public int getChangingConfigurations()
	{
		return mDelegate.getChangingConfigurations();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDither(boolean dither)
	{
		mDelegate.setDither(dither);
	}

	@Override
	public void setFilterBitmap(boolean filter)
	{
		mDelegate.setFilterBitmap(filter);
	}

	@Override
	public void setAlpha(int alpha)
	{
		mDelegate.setAlpha(alpha);
	}

	@Override
	public int getAlpha()
	{
		return mDelegate.getAlpha();
	}

	@Override
	public void setColorFilter(ColorFilter cf)
	{
		mDelegate.setColorFilter(cf);
	}

	@Override
	public void setColorFilter(int color, Mode mode)
	{
		mDelegate.setColorFilter(color, mode);
	}

	@Override
	public void setTint(int tint)
	{
		mDelegate.setTint(tint);
	}

	@Override
	public void setTintList(ColorStateList tint)
	{
		mDelegate.setTintList(tint);
	}

	@Override
	public void setTintMode(Mode tintMode)
	{
		mDelegate.setTintMode(tintMode);
	}

	@Override
	public ColorFilter getColorFilter()
	{
		return mDelegate.getColorFilter();
	}

	@Override
	public void clearColorFilter()
	{
		mDelegate.clearColorFilter();
	}

	@Override
	public void setHotspot(float x, float y)
	{
		mDelegate.setHotspot(x, y);
	}

	@Override
	public void setHotspotBounds(int left, int top, int right, int bottom)
	{
		mDelegate.setHotspotBounds(left, top, right, bottom);
	}

	@Override
	public boolean isStateful()
	{
		return mDelegate.isStateful();
	}

	@Override
	public boolean setState(int[] stateSet)
	{
		return mDelegate.setState(stateSet);
	}

	@Override
	public int[] getState()
	{
		return mDelegate.getState();
	}

	@Override
	public void jumpToCurrentState()
	{
		mDelegate.jumpToCurrentState();
	}

	@Override
	public Drawable getCurrent()
	{
		return mDelegate.getCurrent();
	}

	@Override
	public boolean setVisible(boolean visible, boolean restart)
	{
		return mDelegate.setVisible(visible, restart);
	}

	@Override
	public void setAutoMirrored(boolean mirrored)
	{
		mDelegate.setAutoMirrored(mirrored);
	}

	@Override
	public boolean isAutoMirrored()
	{
		return mDelegate.isAutoMirrored();
	}

	@Override
	public void applyTheme(Theme t)
	{
		mDelegate.applyTheme(t);
	}

	@Override
	public boolean canApplyTheme()
	{
		return mDelegate.canApplyTheme();
	}

	@Override
	public int getOpacity()
	{
		return mDelegate.getOpacity();
	}

	@Override
	public Region getTransparentRegion()
	{
		return mDelegate.getTransparentRegion();
	}

	@Override
	protected boolean onLevelChange(int level)
	{
		return mDelegate.setLevel(level);
	}

	@Override
	public int getIntrinsicWidth()
	{
		return mDelegate.getIntrinsicWidth();
	}

	@Override
	public int getIntrinsicHeight()
	{
		return mDelegate.getIntrinsicHeight();
	}

	@Override
	public int getMinimumWidth()
	{
		return mDelegate.getMinimumWidth();
	}

	@Override
	public int getMinimumHeight()
	{
		return mDelegate.getMinimumHeight();
	}

	@Override
	public boolean getPadding(Rect padding)
	{
		return mDelegate.getPadding(padding);
	}

	@Override
	public void getOutline(Outline outline)
	{
		mDelegate.getOutline(outline);
	}

	@Override
	public Drawable mutate()
	{
		return new DrawableWrapper(mDelegate.mutate());
	}

	@Override
	public ConstantState getConstantState()
	{
		return mDelegate.getConstantState();
	}
}
