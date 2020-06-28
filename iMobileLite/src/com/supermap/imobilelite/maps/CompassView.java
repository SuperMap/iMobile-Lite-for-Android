package com.supermap.imobilelite.maps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

class CompassView extends View {
    private BitmapDrawable needle;
    private BitmapDrawable bg;
    private float rotation;
    private Context context;

    public CompassView(Context context) {
        super(context);
        init(context);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        try {
            canvas.save();
            canvas.translate(w >> 1, h >> 1);
            this.bg.draw(canvas);
            canvas.rotate(this.rotation);
            this.needle.draw(canvas);
        } finally {
            canvas.restore();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = Math.max(this.bg.getIntrinsicWidth(), this.needle.getIntrinsicWidth());
        int h = Math.max(this.bg.getIntrinsicHeight(), this.needle.getIntrinsicHeight());
        setMeasuredDimension(w, h);
    }

    private void init(Context context) {
        this.needle = Util.getDrawable(context, "icn_compass_needle");
        this.bg = Util.getDrawable(context, "icn_compass_bg");
        Overlay.setAlignment(this.needle, 3);
        Overlay.setAlignment(this.bg, 3);
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        if (getVisibility() == 0)
            invalidate();
    }
}