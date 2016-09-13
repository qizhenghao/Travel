package com.bruce.travel.universal.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.bruce.travel.universal.utils.Methods;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by sunfusheng on 16/4/6.
 */
public class GlideRoundCornerTransform extends BitmapTransformation {

    private static float mRadius = Methods.computePixelsWithDensity(6);

    public GlideRoundCornerTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundedCorner(pool, toTransform, outWidth, outHeight);
    }

    private static Bitmap roundedCorner(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;

        int sourceWidth = source.getWidth(), sourceHeight = source.getHeight();
        float scale = Math.max(outWidth * 1.0f / sourceWidth, outHeight * 1.0f / sourceHeight);
        Log.d("Bruce", "scale: " + scale + " outWidth : " + outWidth + " outHeight: " + outHeight + " source.getWidth(): " +  source.getWidth() + "source.getHeight(): " + source.getHeight() );

        RectF roundRect = new RectF(0, 0, outWidth, outHeight);

        BitmapShader bitmapShader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (scale > 0) {
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            bitmapShader.setLocalMatrix(matrix);
        }

        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(roundRect, mRadius, mRadius, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(mRadius);
    }
}
