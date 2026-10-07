package com.android.launcher3.qsb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.android.launcher3.LauncherPrefs;
import com.android.launcher3.Utilities;
import com.android.launcher3.util.HorizontalInsettableView;

/** Optional MaxxOS iOS 27-inspired floating search presentation. */
public class MaxxQsbView extends OseWidgetView implements HorizontalInsettableView {
    private final Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mGooglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mInset;

    public MaxxQsbView(Context context) { super(context); init(); }
    public MaxxQsbView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); init(); }
    public MaxxQsbView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); init(); }

    private void init() {
        setWillNotDraw(false);
        mGooglePaint.setTypeface(android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.BOLD));
    }

    private boolean enabled() {
        return LauncherPrefs.getPrefs(getContext()).getBoolean(Utilities.KEY_IOS27_SEARCH, false);
    }

    @Override protected void onDraw(Canvas canvas) {
        if (enabled()) {
            float d = getResources().getDisplayMetrics().density;
            float left = 12 * d + getWidth() * mInset;
            float right = getWidth() - 12 * d - getWidth() * mInset;
            float top = 4 * d;
            float bottom = getHeight() - 4 * d;
            boolean dark = (getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES;
            mBackgroundPaint.setColor(dark ? 0xCC202124 : 0xCCFFFFFF);
            canvas.drawRoundRect(new RectF(left, top, right, bottom), 24 * d, 24 * d, mBackgroundPaint);
        }
        super.onDraw(canvas);
    }

    @Override protected void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        if (!enabled()) return;
        float d = getResources().getDisplayMetrics().density;
        mGooglePaint.setTextSize(18 * d);
        mGooglePaint.setColor(0xFF4285F4);
        float x = 28 * d + getWidth() * mInset;
        float y = getHeight() / 2f - (mGooglePaint.ascent() + mGooglePaint.descent()) / 2f;
        canvas.drawText("G", x, y, mGooglePaint);
    }

    @Override public void setHorizontalInsets(float insetPercentage) { mInset = insetPercentage; invalidate(); }
    @Override public float getHorizontalInsets() { return mInset; }
}
