
package com.bruce.travel.desktop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bruce.travel.utils.Methods;
import com.bruce.travel.utils.Variables;

/**
 * Created by qizhenghao 2016-08-01 17:07:30.
 */
public class TabIconView extends ImageView {

    private Paint mPaint;
    private Bitmap mSelectedIcon;
    private Bitmap mNormalIcon;
    private Rect mSelectedRect;
    private Rect mNormalRect;
    private int mSelectedAlpha = 0;

    public TabIconView(Context context) {
        super(context);
    }

    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public final void init(int normal, int selected) {
        this.mNormalIcon = createBitmap(normal);
        this.mSelectedIcon = createBitmap(selected);
        int width = sizeAdapt(this.mNormalIcon.getWidth());
        int height = sizeAdapt(this.mNormalIcon.getHeight());
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = width;
        params.height = height;
        this.mNormalRect = new Rect(0, 0, width, height);
        this.mSelectedRect = new Rect(0, 0, width, height);
        this.mPaint = new Paint(1);
    }

    private Bitmap createBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPaint == null) {
            return;
        }
        this.mPaint.setAlpha(255 - this.mSelectedAlpha);
        canvas.drawBitmap(this.mNormalIcon, null, this.mNormalRect, this.mPaint);
        this.mPaint.setAlpha(this.mSelectedAlpha);
        canvas.drawBitmap(this.mSelectedIcon, null, this.mSelectedRect, this.mPaint);
    }

    public final void changeSelectedAlpha(int alpha) {
        this.mSelectedAlpha = alpha;
        invalidate();
    }

    public final void transformPage(float offset) {
        changeSelectedAlpha((int) (255 * (1 - offset)));
    }

    private int sizeAdapt(int size) {
        return (int) (size/ Variables.density * 2.5);
    }
}
