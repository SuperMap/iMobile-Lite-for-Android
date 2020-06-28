package com.supermap.imobilelite.maps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

class AnnotationView extends RelativeLayout {
    private static final String LOG_TAG = "com.supermap.android.AnnotationView";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private Context context;
    private OverlayItem parentItem;
    private int markerHeight = 0;
    private Point2D geoPoint;
    private MapView mapView;
    private ScaleAnimation animation;
    private RelativeLayout innerView;
    private MapView.LayoutParams layoutParams;
    private TextView title;
    private TextView snippet;
    private int backgroundColor;
    private float density;
    private Paint bubbleFillPaint;
    private Paint pointerFillPaint;
    private boolean tryToKeepBubbleOnScreen = true;
    private boolean animated = true;
    private int bubbleRadius = 8;
    private int pointerWidth = 0;
    private int pointerHeight = 0;
    private int innerViewDefaultPaddingLeft = 8;
    private int innerViewDefaultPaddingTop = 5;
    private int innerViewDefaultPaddingRight = 8;
    private int innerViewDefaultPaddingBottom = 5;

    private int DEFAULT_POINTER_WIDTH = 22;
    private int DEFAULT_POINTER_HEIGHT = 8;

    public AnnotationView(MapView map) {
        super(map.getContext());
        this.context = map.getContext();
        this.mapView = map;
        init();
    }

    private void init() {
        setVisibility(8);

        this.density = getResources().getDisplayMetrics().density;
        this.bubbleRadius = (int) (this.bubbleRadius * this.density + 0.5F);
        this.pointerWidth = (int) (this.DEFAULT_POINTER_WIDTH * this.density + 0.5F);
        this.pointerHeight = (int) (this.DEFAULT_POINTER_HEIGHT * this.density + 0.5F);

        this.innerViewDefaultPaddingLeft = (int) (this.innerViewDefaultPaddingLeft * this.density + 0.5F);
        this.innerViewDefaultPaddingTop = (int) (this.innerViewDefaultPaddingTop * this.density + 0.5F);
        this.innerViewDefaultPaddingRight = (int) (this.innerViewDefaultPaddingRight * this.density + 0.5F);
        this.innerViewDefaultPaddingBottom = (int) (this.innerViewDefaultPaddingBottom * this.density + 0.5F);

        this.backgroundColor = Color.parseColor("#E6434343");

        this.layoutParams = new MapView.LayoutParams(-2, -2, new Point2D(0, 0), 33);

        if (this.innerView == null) {
            createDefaultInnerView();
            createDefaultBubble();
        }

        this.animated = true;
        this.animation = new ScaleAnimation(this.markerHeight, 1.0F, 0.0F, 1.0F, 1, 0.5F, 1, 1.0F);

        this.animation.setDuration(100L);
        this.animation.setInterpolator(new DecelerateInterpolator());

        LayoutParams ivlp = new LayoutParams(-2, -2);

        this.innerView.setLayoutParams(ivlp);

        setLayoutParams(this.layoutParams);
        this.mapView.addView(this);
    }

    private void createDefaultBubble() {
        int[] colors = { -1, -7829368 };
        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gradient.setGradientType(1);
        gradient.setDither(true);
        gradient.setGradientCenter(-0.1F, -0.1F);

        this.bubbleFillPaint = new Paint();
        this.bubbleFillPaint.setColor(this.backgroundColor);
        this.bubbleFillPaint.setAntiAlias(true);

        this.pointerFillPaint = new Paint();
        this.pointerFillPaint.setColor(this.backgroundColor);
        this.pointerFillPaint.setAntiAlias(true);
    }

    private void createDefaultInnerView() {
        this.innerView = new RelativeLayout(this.context);
        this.innerView.setPadding(this.innerViewDefaultPaddingLeft, this.innerViewDefaultPaddingTop, this.innerViewDefaultPaddingRight,
                this.innerViewDefaultPaddingBottom);

        RelativeLayout textHolderView = new RelativeLayout(this.context);
        textHolderView.setId("AnnotationViewTextHolderView".hashCode());
        LayoutParams thvlp = new LayoutParams(-2, -2);

        this.title = new TextView(this.context);
        this.title.setId("AnnotationViewTitle".hashCode());
        this.title.setTextColor(-1);
        this.title.setTypeface(Typeface.DEFAULT_BOLD);
        this.title.setTextSize((int) (14.0F * this.density + 0.5F));
        this.title.setMaxEms(10);
        this.title.setSingleLine(true);
        this.title.setEllipsize(TruncateAt.END);
        this.title.setIncludeFontPadding(false);
        LayoutParams tvlp = new LayoutParams(-2, -2);

        tvlp.addRule(6);
        tvlp.addRule(5);
        textHolderView.addView(this.title, tvlp);

        this.snippet = new TextView(this.context);
        this.snippet.setId("AnnotationViewSnippet".hashCode());
        this.snippet.setTextColor(-1);
        this.snippet.setTextSize((int) (10.0F * this.density + 0.5F));
        this.snippet.setMaxEms(15);
        this.snippet.setSingleLine(true);
        this.snippet.setEllipsize(TruncateAt.END);
        this.snippet.setIncludeFontPadding(false);
        LayoutParams svlp = new LayoutParams(-2, -2);

        svlp.addRule(3, this.title.getId());
        svlp.addRule(5);
        textHolderView.addView(this.snippet, svlp);

        this.innerView.addView(textHolderView, thvlp);

        setPadding(0, 0, 0, this.pointerHeight);
        addView(this.innerView);
    }

    public void showAnnotationView(OverlayItem item) {
        this.parentItem = item;
        this.geoPoint = item.getPoint();
        setLayoutParams(item);
        this.title.setText(item.getTitle());
        this.snippet.setText(item.getSnippet());
        this.markerHeight = this.parentItem.getMarker(this.parentItem.getState()).getIntrinsicHeight();
        setPadding(0, 0, 0, this.pointerHeight + this.markerHeight);

        show();
    }

    private void setLayoutParams(OverlayItem item) {
        this.layoutParams = new MapView.LayoutParams(-2, -2, this.geoPoint, 33);

        setLayoutParams(this.layoutParams);
    }

    public void show() {
        if (getVisibility() != 0) {
            setVisibility(0);
            bringToFront();
            if (this.animated) {
                startAnimation(this.animation);
            }
            Log.d(LOG_TAG, resource.getMessage(MapCommon.ANNOTATIONVIEW_SHOWING));
        }
    }

    public void hide() {
        Log.d(LOG_TAG, resource.getMessage(MapCommon.ANNOTATIONVIEW_HIDING));
        setVisibility(8);
    }

    protected void dispatchDraw(Canvas canvas) {
        drawBubble(canvas);
        super.dispatchDraw(canvas);
    }

    private void drawBubble(Canvas canvas) {
        RectF fillRect = new RectF();

        fillRect.set(0.0F, 0.0F, getMeasuredWidth(), getMeasuredHeight() - this.pointerHeight - this.markerHeight);
        if (this.bubbleFillPaint != null) {
            canvas.drawRoundRect(fillRect, this.bubbleRadius, this.bubbleRadius, this.bubbleFillPaint);
        }

        drawPointer(canvas);
    }

    private void drawPointer(Canvas canvas) {
        RectF position = new RectF();

        int middle = getMeasuredWidth() / 2;
        int left = middle - this.pointerWidth / 2;
        int right = middle + this.pointerWidth / 2;
        int top = getMeasuredHeight() - this.pointerHeight - this.markerHeight;
        int bottom = getMeasuredHeight() - this.markerHeight;

        position.set(left, top, right, bottom);

        Path path = new Path();
        path.moveTo(left, top);
        path.lineTo(middle, bottom);
        path.lineTo(right, top);
        path.close();

        Paint paint = new Paint();
        paint.setColor(this.backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawPath(path, paint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(this.innerView.getMeasuredWidth(), this.innerView.getMeasuredHeight());
    }

    private int getXOffsetToKeepBubbleOnScreen() {
        int x = 0;
        if (this.tryToKeepBubbleOnScreen) {
            int realLeft = getLeft();
            int annotationWidth = getMeasuredWidth() - this.bubbleRadius * 2;

            if (realLeft < 0) {
                if (Math.abs(realLeft) < annotationWidth) {
                    x = getLeft() * -1;
                } else {
                    x = annotationWidth;
                }
            } else {
                x = 0;
            }
        }
        return x;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public boolean isTryToKeepBubbleOnScreen() {
        return this.tryToKeepBubbleOnScreen;
    }

    public void tryToKeepBubbleOnScreen(boolean tryToKeepBubbleOnScreen) {
        this.tryToKeepBubbleOnScreen = tryToKeepBubbleOnScreen;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public RelativeLayout getInnerView() {
        return this.innerView;
    }

    public void setInnerView(RelativeLayout newInnerView) {
        this.innerViewDefaultPaddingLeft = newInnerView.getPaddingLeft();
        this.innerViewDefaultPaddingTop = newInnerView.getPaddingTop();
        this.innerViewDefaultPaddingRight = newInnerView.getPaddingRight();
        this.innerViewDefaultPaddingBottom = newInnerView.getPaddingBottom();

        removeView(this.innerView);

        this.innerView = newInnerView;

        addView(this.innerView);
    }

    public TextView getTitle() {
        return this.title;
    }

    public void setTitle(TextView titleView) {
        this.title = titleView;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public TextView getSnippet() {
        return this.snippet;
    }

    public void setSnippet(TextView snippetView) {
        this.snippet = snippetView;
    }

    public void setSnippet(String snippet) {
        this.title.setText(snippet);
    }

    public void setBubbleRadius(int radius) {
        this.bubbleRadius = radius;
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        this.bubbleFillPaint.setColor(color);
        this.pointerFillPaint.setColor(color);
    }

    public Point2D getGeoPoint() {
        return this.geoPoint;
    }

    public void setGeoPoint(Point2D point) {
        this.geoPoint = point;
    }

    public OverlayItem getOverlayItem() {
        return this.parentItem;
    }
}